package com.jorigg.android.chatbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.jorigg.android.chatbox.model.AskForSomething;
import com.jorigg.android.chatbox.model.ChatBank;
import com.jorigg.android.chatbox.model.Conversation;
import com.jorigg.android.chatbox.model.ConversationElementEnum;
import com.jorigg.android.chatbox.model.Sentence;
import com.jorigg.android.chatbox.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.jorigg.android.chatbox.HomeActivity.SELECTED_CONVO;

public class ChatActivity extends AppCompatActivity {

    public static final String CURR_AGENT = "currAgent";
    public static final String CURR_USER = "currUser";
    public static final String CURR_CONVO = "currConvo";

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
                getNextItemsForUserResponseSpinner();
            }
        });

        mChatBank = ChatBank.get(this);

        if (savedInstanceState != null) {
            mCurrentConversation = mChatBank.getConversation(savedInstanceState.getCharSequence
                    (CURR_CONVO).toString());
            mCurrentChildMoves = (HashMap) savedInstanceState.getSerializable(CURR_USER);
            addNextItemsToUserResponseSpinner();
            mCurrentAgentElement = (ConversationElementEnum) savedInstanceState.getSerializable
                    (CURR_AGENT);
            //todo will need something here to display agent bit on rotate
        } else {
            mCurrentConversation = mChatBank.getConversation(getIntent().getCharSequenceExtra
                    (SELECTED_CONVO).toString());
            initialiseUI();
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //agent element needs casting to Enum so is serializable
        //todo this may not be ideal since need to add cast for each convo type - could make the
        // interface Parcelable or something
        Enum agentElement = null;
        if (mCurrentConversation instanceof AskForSomething) {
            agentElement = (AskForSomething.AskForSomethingElements) mCurrentAgentElement;
        }
        outState.putSerializable(CURR_AGENT, agentElement);
        outState.putSerializable(CURR_USER, mCurrentChildMoves);
        outState.putCharSequence(CURR_CONVO, mCurrentConversation.getTitle());
        super.onSaveInstanceState(outState);
    }

    private void initialiseUI() {
        if (mCurrentConversation.getInitiator() == User.UserType.CHILD && mChildMoveElement ==
         null) {
            getNextItemsForUserResponseSpinner();
            mCurrentAgentElement = mCurrentConversation.getInitialAgentElement();
        } else if (mCurrentConversation.getInitiator() == User.UserType.AGENT ||
                mCurrentAgentElement == null) {
            showNextAgentMove();
            getNextItemsForUserResponseSpinner();
        }
    }

    private void showNextAgentMove() {
        String nextMove = "";
        if (mCurrentAgentElement == null) {
            nextMove = mCurrentConversation.getInitialAgentResponse().toString();
            mCurrentAgentElement = (ConversationElementEnum) mCurrentConversation
                    .getInitialUserMoves();
        } else if (mCurrentConversation.isInProgress()) {
            nextMove = mCurrentConversation.getNextAgentMove(mChildMoveElement).second.toString();
            mCurrentAgentElement = (ConversationElementEnum) mCurrentConversation
                    .getNextAgentMove(mChildMoveElement).first;
        } else {
            nextMove = "GAME OVER"; //TODO make a better ending
        }
        //TODO use toast on temp basis
        Toast.makeText(ChatActivity.this, nextMove, Toast.LENGTH_LONG).show();

    }

    private void getNextItemsForUserResponseSpinner() {
        if (mChildMoveElement == null) {
            mCurrentChildMoves = mCurrentConversation.getInitialUserMoves();
        } else if (mCurrentConversation.isInProgress()) {
            mCurrentChildMoves = mCurrentConversation.getNextUserMoves(mCurrentAgentElement);
        } else { //TODO MAKE A better ending
            Toast.makeText(ChatActivity.this, "GAME OVER", Toast.LENGTH_LONG).show();
        }
        addNextItemsToUserResponseSpinner();
    }

    private void addNextItemsToUserResponseSpinner() {
        ArrayList<Sentence> nextMoves = new ArrayList<>();

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
