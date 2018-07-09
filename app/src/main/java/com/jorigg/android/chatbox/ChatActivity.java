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
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private ChatBank mChatBank;
    private Conversation mCurrentConversation;
    private ConversationElementEnum mCurrentAgentElement;
    private HashMap<ConversationElementEnum, ArrayList<Sentence>> mCurrentChildMoves = new
            HashMap<>();
    private ConversationElementEnum mChildMoveElement;

    private Spinner mResponseSpinner;
    private ImageButton mUserResponseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        mResponseSpinner = findViewById(R.id.user_response_spinner);
        mUserResponseButton = findViewById(R.id.user_response_button);

        //todo if onSavedInstanceState != null


        mUserResponseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String response = String.valueOf(mResponseSpinner.getSelectedItem());

                //TODO display it on the user icon
                Toast.makeText(ChatActivity.this, response, Toast.LENGTH_LONG).show();

                //establish which element the child response belongs to and update
                // mCurrentChildElement
                for (Map.Entry<ConversationElementEnum, ArrayList<Sentence>> map : mCurrentChildMoves
                        .entrySet()) {
                    ArrayList<Sentence> thisMove = map.getValue();
                    for (Sentence sentence : thisMove) {
                        if (sentence.getContent().equals(response)) {
                            mChildMoveElement = map.getKey();
                        }
                    }
                }

                //display the agent reponse
                showNextAgentMove();

                //and put the next set of userResponses in spinner
                addNextItemsToUserResponseSpinner();
            }
        });

        //TODO temp hard coding of current conversation to test one
        mChatBank = ChatBank.get(this);
        mCurrentConversation = mChatBank.getConversation("test");

        initialiseUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //TODO need to preserve on rotation
        //need private statis final string keys
        //put relevant vars
        super.onSaveInstanceState(outState);
    }

    private void initialiseUI() {
        if (mCurrentConversation.getInitiator() == User.UserType.CHILD && mChildMoveElement ==
         null) {
            addNextItemsToUserResponseSpinner();
            mCurrentAgentElement = mCurrentConversation.getInitialAgentElement();
        } else if (mCurrentConversation.getInitiator() == User.UserType.AGENT ||
                mCurrentAgentElement == null) {
            showNextAgentMove();
            addNextItemsToUserResponseSpinner();
        }
    }

    private void showNextAgentMove() {
        String nextMove = "";
        if (mCurrentAgentElement == null) {
            nextMove = mCurrentConversation.getInitialAgentResponse().toString();
            mCurrentAgentElement = (ConversationElementEnum) mCurrentConversation
                    .getInitialUserMoves();
        } else {
            nextMove = mCurrentConversation.getNextAgentMove(mChildMoveElement).second.toString();
            mCurrentAgentElement = (ConversationElementEnum) mCurrentConversation
                    .getNextAgentMove(mChildMoveElement).first;
        }
        //TODO use toast on temp basis
        Toast.makeText(ChatActivity.this, nextMove, Toast.LENGTH_LONG).show();

    }

    private void addNextItemsToUserResponseSpinner() {
        ArrayList<Sentence> nextMoves = new ArrayList<>();

        if (mChildMoveElement == null) {
            mCurrentChildMoves = mCurrentConversation.getInitialUserMoves();
        } else {
            mCurrentChildMoves = mCurrentConversation.getNextUserMoves(mCurrentAgentElement);
        }

        for (Map.Entry<ConversationElementEnum, ArrayList<Sentence>> map : mCurrentChildMoves
                .entrySet()) {
            ArrayList<Sentence> thisMove = map.getValue();
            nextMoves.addAll(thisMove);
        }

        ArrayAdapter<Sentence> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, nextMoves);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mResponseSpinner.setAdapter(adapter);
    }

}
