package com.bogdanorzea.regexquiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class ConfirmDialogFragment extends DialogFragment {
    public static final String TITLE = "TITLE";
    public static final String MESSAGE = "MESSAGE";

    String message = null;
    String title = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        if (savedInstanceState != null) {
            title = savedInstanceState.getString(TITLE);
            message = savedInstanceState.getString(MESSAGE);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainActivity) getActivity()).reinitializeProgress();
                ((MainActivity) getActivity()).setAdapter();
                ((MainActivity) getActivity()).notifyDataSetChanged();

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

        return alert;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(TITLE, title);
        outState.putString(MESSAGE, message);

        super.onSaveInstanceState(outState);
    }
}
