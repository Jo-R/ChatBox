package com.jorigg.android.chatbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.jorigg.android.chatbox.model.ChatBank;
import com.jorigg.android.chatbox.model.Conversation;
import com.jorigg.android.chatbox.model.ConversationElementEnum;
import com.jorigg.android.chatbox.model.Sentence;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ChatBank mChatBank;

    private Spinner mResponseSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.jorigg.android.chatbox.R.layout.activity_chat);

        mResponseSpinner = findViewById(R.id.user_response_spinner);

        updateUI();
    }

    private void updateUI() {
        mChatBank = ChatBank.get(this);
        addItemsToUserResponseSpinner();

    }

    private void addItemsToUserResponseSpinner() {
        ArrayAdapter<Sentence> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, mChatBank.getInitialUserResponses());
        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        mResponseSpinner.setAdapter(adapter);
    }

}
