package com.bogdanorzea.regexquiz;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Question> mDataset;

    public MyAdapter(List<Question> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_card_view, parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Question tempQuestion = mDataset.get(position);
        final String text = tempQuestion.getTitle();

        holder.mTitle.setText(tempQuestion.getTitle());
        holder.mDescription.setText(tempQuestion.getDescription());

        // Hide unnecessary elements
        if (tempQuestion.getSingleChoices() != null) {
            holder.mInput.setVisibility(View.GONE);

            holder.mRButton1.setText(tempQuestion.getSingleChoices()[0]);
            holder.mRButton2.setText(tempQuestion.getSingleChoices()[1]);
            holder.mRButton3.setText(tempQuestion.getSingleChoices()[2]);
            holder.mRButton4.setText(tempQuestion.getSingleChoices()[3]);

            holder.mMultipleChoiceLayout.setVisibility(View.GONE);
        } else if(tempQuestion.getMultipleChoices() != null) {
            holder.mInput.setVisibility(View.GONE);

            holder.mSingleChoiceLayout.setVisibility(View.GONE);

            holder.mCBox1.setText(tempQuestion.getMultipleChoices()[0]);
            holder.mCBox2.setText(tempQuestion.getMultipleChoices()[1]);
            holder.mCBox3.setText(tempQuestion.getMultipleChoices()[2]);
            holder.mCBox4.setText(tempQuestion.getMultipleChoices()[3]);
        } else {
            holder.mSingleChoiceLayout.setVisibility(View.GONE);
            holder.mMultipleChoiceLayout.setVisibility(View.GONE);
        }

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
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle, mDescription;
        public EditText mInput;
        public RadioGroup mSingleChoiceLayout;
        public RadioButton mRButton1, mRButton2, mRButton3, mRButton4;
        public LinearLayout mMultipleChoiceLayout;
        public CheckBox mCBox1, mCBox2, mCBox3, mCBox4;

        public Button mButton;

        public ViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.title);
            mDescription = (TextView) v.findViewById(R.id.description);
            mInput = (EditText) v.findViewById(R.id.inputText);

            mSingleChoiceLayout = (RadioGroup) v.findViewById(R.id.radioGroup);
            mRButton1 = (RadioButton) v.findViewById(R.id.radioButton1);
            mRButton2 = (RadioButton) v.findViewById(R.id.radioButton2);
            mRButton3 = (RadioButton) v.findViewById(R.id.radioButton3);
            mRButton4 = (RadioButton) v.findViewById(R.id.radioButton4);

            mMultipleChoiceLayout = (LinearLayout) v.findViewById(R.id.multiple_choice_layout);
            mCBox1 = (CheckBox) v.findViewById(R.id.checkBox1);
            mCBox2 = (CheckBox) v.findViewById(R.id.checkBox2);
            mCBox3 = (CheckBox) v.findViewById(R.id.checkBox3);
            mCBox4 = (CheckBox) v.findViewById(R.id.checkBox4);

            mButton = (Button) v.findViewById(R.id.button);
        }
    }
}
