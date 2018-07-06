package com.jorigg.android.chatbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.jorigg.android.chatbox.model.ChatBank;
import com.jorigg.android.chatbox.model.Conversation;
import com.jorigg.android.chatbox.model.ConversationElementEnum;
import com.jorigg.android.chatbox.model.Sentence;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ChatBank mChatBank;
    private Conversation mCurrentConversation; //TODO passed in from home activity
    private ConversationElementEnum mCurrentAgentElement;
    private List<ConversationElementEnum> mCurrentChildElement;

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
                //TODO
                //display it on the user icon
                Toast.makeText(ChatActivity.this, response, Toast.LENGTH_LONG).show();
                //display the agent reponse
                //and put the next set of userResponses in spinner??
                addItemsToUserResponseSpinner();
            }
        });

        //TODO temp hard coding of current conversaiton to test one
        mChatBank = ChatBank.get(this);
        mCurrentConversation = mChatBank.getConversation("test");

        updateUI();
    }

    private void updateUI() {
        addItemsToUserResponseSpinner();
        //if mCurrentConversation.initiator = child do response spinner
        //if mCurrentConversation.initiator = agent do agent first move and get child possible
        // response to response spinner

    }

    private void addItemsToUserResponseSpinner() {
        ArrayList<Sentence> nextMoves = new ArrayList<>();
        if (mCurrentChildElement == null) {
            nextMoves = mCurrentConversation.getInitialUserResponses();
            mCurrentChildElement = mCurrentConversation.getInitialElements();
        } else {
//            nextMoves = mCurrentConversation.getNextMove(mCurrentChildElement);
            //and would need to get the element(s) as well
        }
        ArrayAdapter<Sentence> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, nextMoves);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mResponseSpinner.setAdapter(adapter);
    }

}
