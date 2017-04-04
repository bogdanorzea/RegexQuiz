package com.bogdanorzea.regexquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

        String[] mDataset = new String[]{"Question 1", "Question 2", "Question 3", "Question 4", "Question 5", "Question 6", "Question 7", "Question 8", "Question 9", "Question 10"};
        mAdapter = new MyAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);
    }
}


///http://stacktips.com/tutorials/android/android-recyclerview-example#2-recyclerview-example