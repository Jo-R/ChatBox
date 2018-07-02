package com.jorigg.android.chatbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.jorigg.android.chatbox.R.layout.activity_chat);


        updateUI();
    }

    private void updateUI() {
        mChatBank = ChatBank.get(this);
        Conversation currConvo = mChatBank.getConversation("test request");

    }

}
