package com.jorigg.android.chatbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.jorigg.android.chatbox.model.ChatBank;

public class CreateAskForSomethingActivity extends AppCompatActivity {

    ChatBank mChatBank;

    ImageButton mAddTitleButton;
    EditText mAddTitleField;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ask_for_something);

        mChatBank = ChatBank.get(this);

        mAddTitleField.findViewById(R.id.editTitle);
        mAddTitleButton.findViewById(R.id.imageButtonTitle);
        mAddTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChatBank.addNewConversation(mAddTitleField.getText().toString(), "Ask for Something");
            }
        });
    }
}
