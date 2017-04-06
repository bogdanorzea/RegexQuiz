package com.bogdanorzea.regexquiz;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;


class MyAdapter extends RecyclerView.Adapter<ViewHolder> {
    public static final String REGEXQUIZ = "REGEXQUIZ";
    private List<Question> mDataset;

    MyAdapter(List<Question> myDataset) {
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Question tempQuestion = mDataset.get(position);

        holder.mTitle.setText(tempQuestion.getTitle());
        holder.mDescription.setText(tempQuestion.getDescription());

        // Hide unnecessary elements
        if (tempQuestion.getAvailableChoices() == null) {
            holder.mSingleChoiceLayout.setVisibility(View.GONE);
            holder.mMultipleChoiceLayout.setVisibility(View.GONE);
        } else {
            String[] tc = tempQuestion.getAvailableChoices();

            if (tempQuestion.getAnswers().size() == 1) {
                holder.mInput.setVisibility(View.GONE);

                holder.mRButton1.setText(tc[0]);
                holder.mRButton2.setText(tc[1]);
                holder.mRButton3.setText(tc[2]);
                holder.mRButton4.setText(tc[3]);
                holder.mRButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRadioButtonClick(v, holder);
                    }
                });
                holder.mRButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRadioButtonClick(v, holder);
                    }
                });
                holder.mRButton3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRadioButtonClick(v, holder);
                    }
                });
                holder.mRButton4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRadioButtonClick(v, holder);
                    }
                });

                holder.mMultipleChoiceLayout.setVisibility(View.GONE);
            } else {
                holder.mInput.setVisibility(View.GONE);

                holder.mSingleChoiceLayout.setVisibility(View.GONE);

                holder.mCBox1.setText(tc[0]);
                holder.mCBox2.setText(tc[1]);
                holder.mCBox3.setText(tc[2]);
                holder.mCBox4.setText(tc[3]);
                holder.mCBox1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCheckBoxClick(v, holder);
                    }
                });
                holder.mCBox2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCheckBoxClick(v, holder);
                    }
                });
                holder.mCBox3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCheckBoxClick(v, holder);
                    }
                });
                holder.mCBox4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCheckBoxClick(v, holder);
                    }
                });
            }
        }

        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tempQuestion.getAvailableChoices() == null) {
                    holder.mUserChoice.clear();
                    holder.mUserChoice.add(holder.mInput.getText().toString());
                }

                Log.i(REGEXQUIZ, "Final answer for " + holder.mTitle.getText().toString() + " is \"" + holder.mUserChoice + "\"");

                if (tempQuestion.getAnswers().size() != holder.mUserChoice.size()) {
                    Toast.makeText(view.getContext(), "Wrong answer!", Toast.LENGTH_SHORT).show();
                } else {
                    List<String> a = tempQuestion.getAnswers();
                    List<String> b = holder.mUserChoice;
                    Collections.sort(a);
                    Collections.sort(b);
                    if (a.equals(b)) {
                        Toast.makeText(view.getContext(), "Correct answer!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(view.getContext(), "Wrong answer!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    void onRadioButtonClick(View v, ViewHolder holder) {
        boolean checked = ((RadioButton) v).isChecked();
        if (checked) {
            holder.mUserChoice.clear();
            holder.mUserChoice.add(((RadioButton) v).getText().toString());
            Log.i(REGEXQUIZ, "User clicked on: " + holder.mUserChoice);
        }
    }

    void onCheckBoxClick(View v, ViewHolder holder) {
        boolean checked = ((CheckBox) v).isChecked();
        if (checked) {
            holder.mUserChoice.add(((CheckBox) v).getText().toString());
        } else {
            holder.mUserChoice.remove(((CheckBox) v).getText().toString());
        }

        Log.i(REGEXQUIZ, "User clicked on: " + holder.mUserChoice);
    }
}
