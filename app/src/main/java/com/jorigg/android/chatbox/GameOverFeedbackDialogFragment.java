package com.jorigg.android.chatbox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;


import static com.jorigg.android.chatbox.ChatActivity.FINAL_FEEDBACK;

public class GameOverFeedbackDialogFragment extends DialogFragment {

    GameOverPerfectDialogFragment.NoticeDialogListener mListener;

    public static GameOverFeedbackDialogFragment newInstance(String feedback) {
        Bundle data = new Bundle();
        data.putCharSequence(FINAL_FEEDBACK, feedback);
        GameOverFeedbackDialogFragment gameOverFeedbackDialog = new GameOverFeedbackDialogFragment();
        gameOverFeedbackDialog.setArguments(data);
        return gameOverFeedbackDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Bundle bundle = getArguments();

        String feedback = bundle.getString(FINAL_FEEDBACK);


        builder.setTitle("CHAT OVER").setMessage(feedback)
                .setPositiveButton
                        ("Play again", new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        mListener.onDialogPositiveClick(GameOverFeedbackDialogFragment.this);
                                    }
                                })
                .setNegativeButton("Home", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onDialogNegativeClick(GameOverFeedbackDialogFragment.this);
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
            mListener = (GameOverPerfectDialogFragment.NoticeDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException((context.toString() + " must implement " +
                    "NoticeDialogListener"));
        }
    }
}
