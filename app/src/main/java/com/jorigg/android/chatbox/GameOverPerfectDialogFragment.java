package com.jorigg.android.chatbox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.jorigg.android.chatbox.model.UserPreferences;

public class GameOverPerfectDialogFragment extends DialogFragment {

    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    NoticeDialogListener mListener;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("CHAT OVER").setMessage(UserPreferences.getPraise(getContext()) + " You " +
                        "now have " + UserPreferences.getUserScore(getContext()) + " perfect conversations")
                .setPositiveButton
                ("Play again", new
                DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onDialogPositiveClick(GameOverPerfectDialogFragment.this);
            }
        })
                .setNegativeButton("Home", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onDialogNegativeClick(GameOverPerfectDialogFragment.this);
                    }
                });

        return builder.create();
    }

    @Override
    //per https://developer.android.com/guide/topics/ui/dialogs#PassingEvents
    public void onAttach(Context context) {
        super.onAttach(context);
        //verify host activity implements callback ifc
        try {
            mListener = (NoticeDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException((context.toString() + " must implement " +
                    "NoticeDialogListener"));
        }
    }
}
