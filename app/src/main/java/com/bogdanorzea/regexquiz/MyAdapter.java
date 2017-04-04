package com.bogdanorzea.regexquiz;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new TextView
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);

        // Set layout_margins for View
        //ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        //mlp.setMargins(20, 20, 20, 20);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String text = mDataset[position];
        holder.mTextView.setText(text);

        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("LOCATION", "Created from: " + text);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public Button mButton;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.title);
            mButton = (Button) v.findViewById(R.id.button);
        }
    }
}
