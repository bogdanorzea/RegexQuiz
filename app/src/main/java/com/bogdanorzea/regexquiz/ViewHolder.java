package com.bogdanorzea.regexquiz;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

class ViewHolder extends RecyclerView.ViewHolder {
    TextView mTitle, mDescription;
    EditText mInput;
    RadioGroup mSingleChoiceLayout;
    RadioButton mRButton1, mRButton2, mRButton3, mRButton4;
    LinearLayout mMultipleChoiceLayout;
    CheckBox mCBox1, mCBox2, mCBox3, mCBox4;
    Button mButton;

    ViewHolder(View v) {
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
