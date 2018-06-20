package com.bignerdranch.android.chatbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.chatbox.model.ChatManager;
import com.bignerdranch.android.chatbox.model.Conversation;
import com.bignerdranch.android.chatbox.model.Sentence;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private TextView mAgentText;
    private TextView mUserText;
    private Button mNextBtn;

    private ChatManager mChatManager;
    private int mCurrAgentMoveId;
    private int mCurrUserMoveId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAgentText = findViewById(R.id.agent_text);
        mUserText = findViewById(R.id.user_text);
        mNextBtn = findViewById(R.id.next_button);

        //used in updateUI - wouldn't want to change these if had a savedInstanceState
        if (savedInstanceState == null) {
            mCurrAgentMoveId = -1;
            mCurrUserMoveId = -1;
        }

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //something!!
                Toast.makeText(getApplicationContext(), "Button clicked!", Toast.LENGTH_LONG).show();
            }
        });

        updateUI();
    }

    private void updateUI() {
        mChatManager = ChatManager.get(this);
        Conversation currConvo = mChatManager.getConversation("test request");
        List<Sentence> currDialogue = currConvo.getDialogue();

        //find agent and user IDs for next move
        //initial move
        if (mCurrAgentMoveId == -1 || mCurrUserMoveId == -1) {
            for (Sentence sentence : currDialogue) {
                if (sentence.getSender().equals("Agent") && mCurrAgentMoveId == -1) {
                    mAgentText.setText(sentence.getContent());
                    mCurrAgentMoveId = sentence.getId();
                } else if (sentence.getSender().equals("User") && mCurrUserMoveId == -1) {
                    mUserText.setText(sentence.getContent());
                    mCurrUserMoveId = sentence.getId();
                }
            }
        }

    }

}
