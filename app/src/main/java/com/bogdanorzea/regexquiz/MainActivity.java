package com.bogdanorzea.regexquiz;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.bogdanorzea.regexquiz.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {
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
        } else {
            reinitializeProgress();
        }
        setAdapter();
    }

    /**
     * Sets the initial values for the quiz
     */
    private void reinitializeProgress() {
        progressStatus = 0;
        correctAnswers = 0;
        mAdapter = new QuestionAdapter(generateQuestions());
    }

    /**
     * Sets the Adapter for the RecyclerView
     */
    private void setAdapter() {
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                        builder.setTitle(R.string.congratulations);
                        builder.setMessage(String.format(getString(R.string.progress_status), correctAnswers, progressStatus));

                        builder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
            });
        }
    }

    /**
     * This function generates the Question objects by parsing an JSON file
     *
     * @return List of Question object
     */
    private List<Question> generateQuestions() {
        // JSON Node names
        String TAG_QUESTIONS = "questions";
        String TAG_TITLE = "title";
        String TAG_DESCRIPTION = "description";
        String TAG_CHOICES = "choices";
        String TAG_ANSWERS = "answers";

        // Read JSON file
        StringBuilder jsonString = new StringBuilder();
        BufferedReader in = null;
        try {
            InputStream json = getAssets().open("questions.json");
            in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                jsonString.append(str);
            }
        } catch (IOException e) {
            Log.e("JSON", "Error opening asset questions.json");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e("JSON", "Error closing asset questions.json");
                }
            }
        }

        List<Question> qList = new ArrayList<>();

        try {
            JSONObject jsonObj = new JSONObject(jsonString.toString());

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
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle(R.string.confirm);
            builder.setMessage(R.string.reset_progress_prompt);

            builder.setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    reinitializeProgress();
                    setAdapter();
                    mAdapter.notifyDataSetChanged();

                    dialog.dismiss();
                }
            });

            builder.setNegativeButton(R.string.no_button, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();

            return true;
        }
        if (id == R.id.action_check) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle(R.string.progress);
            builder.setMessage(String.format(getString(R.string.progress_status), correctAnswers, mAdapter.getItemCount()));

            builder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
