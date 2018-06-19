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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAgentText = findViewById(R.id.agent_text);
        mUserText = findViewById(R.id.user_text);
        mNextBtn = findViewById(R.id.next_button);

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
        mChatManager = ChatManager.get(this); //current activity context but ctor then sets it to
        // getApplicationContext()
        Conversation currConvo = mChatManager.getConversation("test request");
        List<Sentence> currDialogue = currConvo.getDialogue();
        mAgentText.setText(currDialogue.get(0).getContent());
        mUserText.setText(currDialogue.get(1).getContent());
    }
}
