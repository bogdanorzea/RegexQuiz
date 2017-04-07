package com.bogdanorzea.regexquiz;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.bogdanorzea.regexquiz.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {
    private static Bundle mBundleRecyclerViewState;
    private final String RECYCLERVIEWSTATE = "RECYCLERVIEWSTATE";
    private final String ADAPTER = "ADAPTER";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // TODO Add menu to reset the answers
        // TODO Add menu to share the results :)

        if (savedInstanceState != null) {
            mAdapter = (RecyclerView.Adapter) savedInstanceState.getSerializable(ADAPTER);
        } else {
            mAdapter = new MyAdapter(generateQuestions());
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    // TODO Add more questions
    private List<Question> generateQuestions() {
        List<Question> qList = new ArrayList<>();

        Question temp = new Question("Question 1", "What is the character to match any single character?", ".");
        qList.add(temp);
        temp = new Question("Question 2", "What does \"regex\" shorthand stand for?", new String[]{"Regional Expression", "Regular Expression", "Regal Experience", "Regular Exercise"}, "Regular Expression");
        qList.add(temp);
        temp = new Question("Question 3", "Which of the following will match a single digit number?", new String[]{"\\D", "[0-9]", "\\n", "\\d"}, new String[]{"[0-9]", "\\d"});
        qList.add(temp);
        temp = new Question("Question 4", "What is the syntax to capture groups?", new String[]{"(?: ...  )", "(?= ...  )", "[?: ...  ]", "(?== ...  )"}, "(?: ...  )");
        qList.add(temp);
        return qList;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(ADAPTER, (Serializable) mAdapter);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(RECYCLERVIEWSTATE, listState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(RECYCLERVIEWSTATE);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }
}
///http://stacktips.com/tutorials/android/android-recyclerview-example#2-recyclerview-example

