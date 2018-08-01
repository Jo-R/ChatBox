package com.jorigg.android.chatbox;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jorigg.android.chatbox.model.AskForSomething;
import com.jorigg.android.chatbox.model.ChatBank;
import com.jorigg.android.chatbox.model.Conversation;
import com.jorigg.android.chatbox.model.ConversationElementEnum;
import com.jorigg.android.chatbox.model.Greeting;
import com.jorigg.android.chatbox.model.Sentence;
import com.jorigg.android.chatbox.model.UserPreferences;

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
    private ImageView mLeftSpeechBubble;
    private ImageView mRightSpeechBubble;
    private TextView mLeftSpeechBubbleText;
    private TextView mRightSpeechBubbleText;
    private ImageView mUserAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mUserAvatar = findViewById(R.id.user_avatar);
        if (UserPreferences.getUserAvatar(getApplicationContext()) == R.drawable.boy_figure_test) {
           Drawable avatar = getResources().getDrawable(R.drawable.boy_figure_test);
            mUserAvatar.setImageDrawable(avatar);
        } else if (UserPreferences.getUserAvatar(getApplicationContext()) == R.drawable
                .girl_figure) {
            Drawable avatar = getResources().getDrawable(R.drawable.girl_figure);
            mUserAvatar.setImageDrawable(avatar);
        }


        mLeftSpeechBubble = findViewById(R.id.left_speech_bubble);
        mLeftSpeechBubble.setVisibility(View.INVISIBLE);
        mLeftSpeechBubbleText = findViewById(R.id.left_speech_bubble_text);
        mRightSpeechBubble = findViewById(R.id.right_speech_bubble);
        mRightSpeechBubble.setVisibility(View.INVISIBLE);
        mRightSpeechBubbleText = findViewById(R.id.right_speech_bubble_text);

        mResponseSpinner = findViewById(R.id.user_response_spinner);
        mUserResponseButton = findViewById(R.id.user_response_button);
        mUserResponseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String response = String.valueOf(mResponseSpinner.getSelectedItem());

                mLeftSpeechBubble.setVisibility(View.INVISIBLE);
                mLeftSpeechBubbleText.setText("");

                mRightSpeechBubble.setVisibility(View.VISIBLE);
                mRightSpeechBubbleText.setText(response);


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

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showNextAgentMove();
                        getNextItemsForUserResponseSpinner();
                        mRightSpeechBubble.setVisibility(View.INVISIBLE);
                        mRightSpeechBubbleText.setText("");
                    }
                }, 1500);


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
        } else if (mCurrentConversation instanceof Greeting) {
            agentElement = (Greeting.GreetingElements) mCurrentAgentElement;
        }
        outState.putSerializable(CURR_AGENT, agentElement);
        outState.putSerializable(CURR_USER, mCurrentChildMoves);
        outState.putCharSequence(CURR_CONVO, mCurrentConversation.getTitle());
        super.onSaveInstanceState(outState);
    }

    private void initialiseUI() {
        if (mCurrentConversation.getInitiator() == UserPreferences.UserType.CHILD && mChildMoveElement ==
         null) {
            getNextItemsForUserResponseSpinner();
            mCurrentAgentElement = mCurrentConversation.getInitialAgentElement();
        } else if (mCurrentConversation.getInitiator() == UserPreferences.UserType.AGENT ||
                mCurrentAgentElement == null) {
            showNextAgentMove();
            getNextItemsForUserResponseSpinner();
        }
    }

    private void showNextAgentMove() {
        String nextMove = "";
        if (mCurrentAgentElement == null) {
            nextMove = mCurrentConversation.getInitialAgentResponse().toString();
            mCurrentAgentElement = mCurrentConversation.getInitialAgentElement();
        } else if (mCurrentConversation.isInProgress()) {
            nextMove = mCurrentConversation.getNextAgentMove(mChildMoveElement).second.toString();
            mCurrentAgentElement = (ConversationElementEnum) mCurrentConversation
                    .getNextAgentMove(mChildMoveElement).first;
        } else {
            Toast.makeText(ChatActivity.this, "GAME OVER", Toast.LENGTH_LONG).show();
            //TODO make a better ending
        }
        mLeftSpeechBubble.setVisibility(View.VISIBLE);
        mLeftSpeechBubbleText.setText(nextMove);

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
