package com.bogdanorzea.regexquiz;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;


class MyAdapter extends RecyclerView.Adapter<ViewHolder> implements Serializable {
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
            // Add logic to update user choice as he types the answer
            holder.mInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    tempQuestion.addExclusiveUserChoice(holder.mInput.getText().toString());
                }
            });

            if (!tempQuestion.getUserChoices().isEmpty()) {
                holder.mInput.setText(tempQuestion.getUserChoices().get(0));
            }

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

                // Check the radio buttons if needed
                if (tempQuestion.hasAsAnswer(tc[0])) {
                    holder.mRButton1.setChecked(true);
                }
                if (tempQuestion.hasAsAnswer(tc[1])) {
                    holder.mRButton2.setChecked(true);
                }
                if (tempQuestion.hasAsAnswer(tc[2])) {
                    holder.mRButton3.setChecked(true);
                }
                if (tempQuestion.hasAsAnswer(tc[3])) {
                    holder.mRButton4.setChecked(true);
                }

                holder.mRButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRadioButtonClick(v, tempQuestion);
                    }
                });
                holder.mRButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRadioButtonClick(v, tempQuestion);
                    }
                });
                holder.mRButton3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRadioButtonClick(v, tempQuestion);
                    }
                });
                holder.mRButton4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRadioButtonClick(v, tempQuestion);
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

                // Check the check buttons if needed
                if (tempQuestion.hasAsAnswer(tc[0])) {
                    holder.mCBox1.setChecked(true);
                }
                if (tempQuestion.hasAsAnswer(tc[1])) {
                    holder.mCBox2.setChecked(true);
                }
                if (tempQuestion.hasAsAnswer(tc[2])) {
                    holder.mCBox3.setChecked(true);
                }
                if (tempQuestion.hasAsAnswer(tc[3])) {
                    holder.mCBox4.setChecked(true);
                }

                holder.mCBox1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCheckBoxClick(v, tempQuestion);
                    }
                });
                holder.mCBox2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCheckBoxClick(v, tempQuestion);
                    }
                });
                holder.mCBox3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCheckBoxClick(v, tempQuestion);
                    }
                });
                holder.mCBox4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCheckBoxClick(v, tempQuestion);
                    }
                });
            }
        }

        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitButtonClick(view, tempQuestion, holder);
            }
        });

        if (tempQuestion.wasAnswered()) {
            setEnableHolder(holder, false);
        }
    }

    private void setEnableHolder(ViewHolder holder, boolean mode) {
        holder.mTitle.setEnabled(mode);
        holder.mDescription.setEnabled(mode);
        holder.mInput.setEnabled(mode);
        holder.mRButton1.setEnabled(mode);
        holder.mRButton2.setEnabled(mode);
        holder.mRButton3.setEnabled(mode);
        holder.mRButton4.setEnabled(mode);
        holder.mCBox1.setEnabled(mode);
        holder.mCBox2.setEnabled(mode);
        holder.mCBox3.setEnabled(mode);
        holder.mCBox4.setEnabled(mode);
        holder.mButton.setEnabled(mode);
    }

    private void onSubmitButtonClick(View view, Question tempQuestion, ViewHolder holder) {
        Log.d(REGEXQUIZ, "Final answer for " + tempQuestion.getTitle() + " is: " + tempQuestion.getUserChoices());

        if (!tempQuestion.hasSufficientChoices()) {
            Toast.makeText(view.getContext(), "Wrong answer!", Toast.LENGTH_SHORT).show();
        } else {
            if (tempQuestion.isCorrectlyAnswered()) {
                Toast.makeText(view.getContext(), "Correct answer!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(view.getContext(), "Wrong answer!", Toast.LENGTH_SHORT).show();
            }
        }

        // Disables question card from being clicked again
        tempQuestion.markAnswered();
        setEnableHolder(holder, false);

        // TODO Report score
    }

    private void onRadioButtonClick(View v, Question tempQuestion) {
        boolean checked = ((RadioButton) v).isChecked();
        if (checked) {
            tempQuestion.addExclusiveUserChoice(((RadioButton) v).getText().toString());
            Log.d(REGEXQUIZ, "User clicked on: " + tempQuestion.getUserChoices());
        }
    }

    private void onCheckBoxClick(View v, Question tempQuestion) {
        boolean checked = ((CheckBox) v).isChecked();
        if (checked) {
            tempQuestion.addUserChoice(((CheckBox) v).getText().toString());
        } else {
            tempQuestion.removeUserChoice(((CheckBox) v).getText().toString());
        }
        Log.d(REGEXQUIZ, "User clicked on: " + tempQuestion.getUserChoices());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
