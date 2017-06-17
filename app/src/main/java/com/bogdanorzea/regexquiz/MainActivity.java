package com.bogdanorzea.regexquiz;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.bogdanorzea.regexquiz.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getName();
    private static final String PROGRESS = "PROGRESS";
    private static final String CORRECT_ANSWERS = "CORRECT_ANSWERS";
    private static Bundle mBundleRecyclerViewState;
    private final String RECYCLER_VIEW_STATE = "RECYCLER_VIEW_STATE";
    private final String ADAPTER = "ADAPTER";
    private int progressStatus;
    private int correctAnswers;
    private ProgressBar progressBar;
    private QuestionAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(10);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (savedInstanceState != null) {
            progressStatus = savedInstanceState.getInt(PROGRESS);
            correctAnswers = savedInstanceState.getInt(CORRECT_ANSWERS);
            mAdapter = (QuestionAdapter) savedInstanceState.getSerializable(ADAPTER);
            setAdapter();
        } else {
            reinitializeProgress();
        }
    }

    /**
     * Sets the initial values for the quiz
     */
    void reinitializeProgress() {
        // TODO sharedPreferences
        new GetQuestionsTask().execute("192.168.43.86", "123");
    }

    /**
     * Sets the Adapter for the RecyclerView
     */
    void setAdapter() {
        if (mAdapter != null) {
            progressBar.setMax(mAdapter.getItemCount());
            progressBar.setProgress(progressStatus);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setOnCheckClickListener(new OnCheckClickListener() {
                @Override
                public void onCheckClick(Question q) {
                    progressStatus += 1;
                    if (q.isCorrectlyAnswered()) {
                        correctAnswers += 1;
                    }
                    progressBar.setProgress(progressBar.getProgress() + 1);

                    if (progressStatus == mAdapter.getItemCount()) {

                        ProgressDialogFragment progressFragment = new ProgressDialogFragment();
                        progressFragment.title = getString(R.string.congratulations);
                        progressFragment.message = String.format(getString(R.string.progress_status), correctAnswers, progressStatus);
                        progressFragment.show(getFragmentManager(), "Congratulation Fragment");
                        new PutResultsTask().execute("192.168.43.86", "123", String.format("%1$d/%2$d", correctAnswers, progressStatus));
                    }
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ADAPTER, mAdapter);
        outState.putInt(PROGRESS, progressStatus);
        outState.putInt(CORRECT_ANSWERS, correctAnswers);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(RECYCLER_VIEW_STATE, listState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(RECYCLER_VIEW_STATE);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_reset) {
            ConfirmDialogFragment confirmFragment = new ConfirmDialogFragment();
            confirmFragment.title = getString(R.string.confirm);
            confirmFragment.message = getString(R.string.reset_progress_prompt);
            confirmFragment.show(getFragmentManager(), "Confirm Fragment");

            return true;
        }
        if (id == R.id.action_check) {
            ProgressDialogFragment progressFragment = new ProgressDialogFragment();
            progressFragment.title = getString(R.string.progress);
            progressFragment.message = String.format(getString(R.string.progress_status), correctAnswers, mAdapter.getItemCount());
            progressFragment.show(getFragmentManager(), "Progress Fragment");

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    class GetQuestionsTask extends AsyncTask<String, Void, String> {
        public final String LOG_TAG = GetQuestionsTask.class.getName();

        @Override
        protected void onPostExecute(String s) {
            // Reset game status
            progressStatus = 0;
            correctAnswers = 0;
            if (!s.isEmpty()) {
                mAdapter = new QuestionAdapter(generateQuestions(s));
                setAdapter();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            try {
                if (InetAddress.getByName(strings[0]).isReachable(1000)) {
                    Socket clientSocket = null;
                    DataInputStream dataInputStream = null;

                    clientSocket = new Socket(strings[0], Integer.parseInt(strings[1]));
                    dataInputStream = new DataInputStream(clientSocket.getInputStream());

                    if (dataInputStream != null) {
                        response = dataInputStream.readUTF();
                    }

                } else {
                    Log.i(LOG_TAG, "Server not reachable!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        /**
         * This function generates the Question objects by parsing an JSON file
         *
         * @return List of Question object
         */
        private List<Question> generateQuestions(String s) {
            // JSON Node names
            String TAG_QUESTIONS = "questions";
            String TAG_TITLE = "title";
            String TAG_DESCRIPTION = "description";
            String TAG_CHOICES = "choices";
            String TAG_ANSWERS = "answers";

            List<Question> qList = new ArrayList<>();

            try {
                JSONObject jsonObj = new JSONObject(s);

                JSONArray jsonQuestions = jsonObj.getJSONArray(TAG_QUESTIONS);
                for (int i = 0; i < jsonQuestions.length(); i++) {
                    String title;
                    String description;
                    String[] choices;
                    String[] answers;

                    JSONObject q = jsonQuestions.getJSONObject(i);

                    title = q.getString(TAG_TITLE);
                    description = q.getString(TAG_DESCRIPTION);
                    JSONArray jsonChoices = q.getJSONArray(TAG_CHOICES);
                    choices = new String[jsonChoices.length()];
                    for (int j = 0; j < jsonChoices.length(); j++) {
                        choices[j] = jsonChoices.getString(j);
                    }
                    JSONArray jsonAnswers = q.getJSONArray(TAG_ANSWERS);
                    answers = new String[jsonAnswers.length()];
                    for (int j = 0; j < jsonAnswers.length(); j++) {
                        answers[j] = jsonAnswers.getString(j);
                    }

                    Question temp = new Question(title, description, choices, answers);
                    qList.add(temp);

                }
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            return qList;
        }
    }

    class PutResultsTask extends AsyncTask<String, Void, String> {
        public final String LOG_TAG = PutResultsTask.class.getName();

        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            try {
                if (InetAddress.getByName(strings[0]).isReachable(1000)) {
                    Socket clientSocket = null;
                    DataOutputStream dataOutputStream = null;

                    clientSocket = new Socket(strings[0], Integer.parseInt(strings[1]));
                    dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

                    dataOutputStream.writeUTF(strings[2]);

                } else {
                    Log.i(LOG_TAG, "Server not reachable!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }
    }
}
