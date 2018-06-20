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
                updateUI();
            }
        });

        updateUI();
    }

    private void updateUI() {
        mChatManager = ChatManager.get(this);
        Conversation currConvo = mChatManager.getConversation("test request");
        List<Sentence> currDialogue = currConvo.getDialogue();

        //find agent and user IDs for next move
        boolean agentMoveSet = false;
        boolean userMoveSet = false;
        //initial move
        if (mCurrAgentMoveId == -1 || mCurrUserMoveId == -1) {
            for (Sentence sentence : currDialogue) {
                if (sentence.getSender().equals("Agent") && !agentMoveSet) {
                    mAgentText.setText(sentence.getContent());
                    mCurrAgentMoveId = sentence.getId();
                    agentMoveSet = true;
                } else if (sentence.getSender().equals("User") && !userMoveSet) {
                    mUserText.setText(sentence.getContent());
                    mCurrUserMoveId = sentence.getId();
                    userMoveSet = true;
                }
            }
        } else {
            for (Sentence sentence : currDialogue) {
                //this will get the first move for that player that comes after the one they are on
                //TODO this doesn't really work cf multiple options for users
                if (sentence.getSender().equals("Agent") && mCurrAgentMoveId != sentence.getId()
                        && !agentMoveSet) {
                    mAgentText.setText(sentence.getContent());
                    mCurrAgentMoveId = sentence.getId();
                    agentMoveSet = true;
                } else if (sentence.getSender().equals("User") && mCurrUserMoveId != sentence
                        .getId() && !userMoveSet) {
                    mUserText.setText(sentence.getContent());
                    mCurrUserMoveId = sentence.getId();
                    userMoveSet = true;
                }
            }
        }

    }

}
