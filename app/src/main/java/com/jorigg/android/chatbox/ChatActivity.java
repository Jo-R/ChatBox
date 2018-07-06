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
import com.jorigg.android.chatbox.model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {

    private ChatBank mChatBank;
    private Conversation mCurrentConversation; //TODO passed in from home activity
    private ConversationElementEnum mCurrentAgentElement;
    private ArrayList<ConversationElementEnum> mCurrentChildElement = new ArrayList<>();

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

                //TODO display it on the user icon
                Toast.makeText(ChatActivity.this, response, Toast.LENGTH_LONG).show();

                //TODO establish which element the child response belongs to and update
                // mCurrentChild
                // Element so its only that one

                //display the agent reponse (pass mCurrentChildElement)
                showNextAgentMove(mCurrentChildElement);

                //and put the next set of userResponses in spinner
                addItemsToUserResponseSpinner();
            }
        });

        //TODO temp hard coding of current conversaiton to test one
        mChatBank = ChatBank.get(this);
        mCurrentConversation = mChatBank.getConversation("test");

        initialiseUI();
    }

    private void initialiseUI() {
        if (mCurrentConversation.getInitiator() == User.UserType.CHILD && mCurrentChildElement
                .size() == 0) {
            addItemsToUserResponseSpinner();
            mCurrentAgentElement = mCurrentConversation.getInitialAgentElement();
        } else if (mCurrentConversation.getInitiator() == User.UserType.AGENT ||
                mCurrentAgentElement == null) {
            showNextAgentMove(null);
            addItemsToUserResponseSpinner();
        }
    }

    private void showNextAgentMove(ArrayList<ConversationElementEnum> childLastMove) {
        String nextMove = "";
        if (mCurrentAgentElement == null) {
            nextMove = mCurrentConversation.getInitialAgentResponse().toString();
            mCurrentAgentElement = (ConversationElementEnum) mCurrentConversation
                    .getInitialUserElements().get(0);
        } else {
            ConversationElementEnum childMove = childLastMove.get(0);
            nextMove = mCurrentConversation.getNextAgentMove(childMove).second.toString();
            mCurrentAgentElement = (ConversationElementEnum) mCurrentConversation
                    .getNextAgentMove(childMove).first;
        }
        //TODO use toast on temp basis
        Toast.makeText(ChatActivity.this, nextMove, Toast.LENGTH_LONG).show();

    }

    private void addItemsToUserResponseSpinner() {
        ArrayList<Sentence> nextMoves = new ArrayList<>();
        if (mCurrentChildElement.size() == 0) {
            nextMoves = mCurrentConversation.getInitialUserResponses();
            mCurrentChildElement = mCurrentConversation.getInitialUserElements();
        } else {
            HashMap<ConversationElementEnum, ArrayList<Sentence>> moves = new HashMap<>();
            moves = mCurrentConversation.getNextUserMoves(mCurrentAgentElement);
            //TODO add each key to the currentChildMoves and each Sentence to the nextMoves
            // arraylist
        }
        ArrayAdapter<Sentence> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, nextMoves);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mResponseSpinner.setAdapter(adapter);
    }

}
