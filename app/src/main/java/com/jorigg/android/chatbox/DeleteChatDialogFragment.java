package com.jorigg.android.chatbox;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.jorigg.android.chatbox.model.ChatBank;

public class DeleteChatDialogFragment extends DialogFragment {

    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    NoticeDialogListener mListener;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.dialog_delete_chat).setPositiveButton(R.string
                .dialog_positive_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onDialogPositiveClick(DeleteChatDialogFragment.this);
            }
        })
                .setNegativeButton(R.string.dialog_negative_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onDialogNegativeClick(DeleteChatDialogFragment.this);
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
