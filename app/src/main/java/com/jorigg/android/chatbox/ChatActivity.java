package com.jorigg.android.chatbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private ImageButton mUserResponseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.jorigg.android.chatbox.R.layout.activity_chat);

        mResponseSpinner = findViewById(R.id.user_response_spinner);
        mUserResponseButton = findViewById(R.id.user_response_button);
        mUserResponseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String response = String.valueOf(mResponseSpinner.getSelectedItem());
                Toast.makeText(ChatActivity.this, response, Toast.LENGTH_LONG).show();
                //TODO
                //need to somehow get this string linked to the move it belongs to
                //display the reponse on the main section
                //get the agent response
                //and put the next set of userResponses in spinner??
            }
        });

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
