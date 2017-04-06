package com.bogdanorzea.regexquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.bogdanorzea.regexquiz.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {
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

        List<Question> qList = new ArrayList<>();

        Question temp = new Question("Question 1", "Input type question", "A");
        qList.add(temp);
        temp = new Question("Question 2", "What does \"regex\" shorthand stand for?", new String[]{"Regional Expression", "Regular Expression", "Regal Experience", "Regular Exercise"}, "Regular Expression");
        qList.add(temp);
        temp = new Question("Question 3", "Multiple type question", new String[]{"A", "B", "C", "D"}, new String[]{"A", "B"});
        qList.add(temp);

        mAdapter = new MyAdapter(qList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
///http://stacktips.com/tutorials/android/android-recyclerview-example#2-recyclerview-example