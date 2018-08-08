package com.jorigg.android.chatbox;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
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

public class ChatActivity extends AppCompatActivity implements GameOverPerfectDialogFragment
        .NoticeDialogListener {

    public static final String CURR_AGENT = "currAgent";
    public static final String CURR_USER = "currUser";
    public static final String CURR_CONVO = "currConvo";
    public static final String CURR_IS_PERFECT = "isPerfect";
    public static final String CURR_FEEDBACK = "currFeedback";
    public static final String FINAL_FEEDBACK = "finalFeedback";

    private ChatBank mChatBank;
    private Conversation mCurrentConversation;
    private ConversationElementEnum mCurrentAgentElement;
    private HashMap<ConversationElementEnum, ArrayList<Sentence>> mCurrentChildMoves = new
            HashMap<>();
    private ConversationElementEnum mChildMoveElement;
    private boolean mIsPerfectConversation;
    private ArrayList<String> mFeedback;

    private Spinner mResponseSpinner;
    private ImageButton mUserResponseButton;
    private ImageView mLeftSpeechBubble;
    private ImageView mRightSpeechBubble;
    private TextView mLeftSpeechBubbleText;
    private TextView mRightSpeechBubbleText;
    private ImageView mThoughtBubble;
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
        mThoughtBubble = findViewById(R.id.thought_bubble);
        mThoughtBubble.setVisibility(View.INVISIBLE);

        mResponseSpinner = findViewById(R.id.user_response_spinner);
        mUserResponseButton = findViewById(R.id.user_response_button);
        mUserResponseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String response = String.valueOf(mResponseSpinner.getSelectedItem());

                mLeftSpeechBubble.setVisibility(View.INVISIBLE);
                mThoughtBubble.setVisibility(View.INVISIBLE);
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

                //check if last move was a bad one and change status if is
                if (mCurrentConversation.isNegativeMove(mChildMoveElement)) {
                    mIsPerfectConversation = false;
                    mFeedback.add(mCurrentConversation.getFeedback(mChildMoveElement));
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
            mIsPerfectConversation = savedInstanceState.getBoolean(CURR_IS_PERFECT);
            mFeedback = savedInstanceState.getStringArrayList(CURR_FEEDBACK);
            mCurrentChildMoves = (HashMap) savedInstanceState.getSerializable(CURR_USER);
            addNextItemsToUserResponseSpinner();
            mCurrentAgentElement = (ConversationElementEnum) savedInstanceState.getSerializable
                    (CURR_AGENT);
        } else {
            mCurrentConversation = mChatBank.getConversation(getIntent().getCharSequenceExtra
                    (SELECTED_CONVO).toString());
            mCurrentConversation.setInProgress();
            mIsPerfectConversation = true;
            mFeedback = new ArrayList<>();
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
        outState.putBoolean(CURR_IS_PERFECT, mIsPerfectConversation);
        outState.putStringArrayList(CURR_FEEDBACK, mFeedback);
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
            endGame();
        }
        if (mCurrentAgentElement.isThought()) {
            mThoughtBubble.setVisibility(View.VISIBLE);
        } else {
            mLeftSpeechBubble.setVisibility(View.VISIBLE);
        }
        mLeftSpeechBubbleText.setText(nextMove);

    }

    private void getNextItemsForUserResponseSpinner() {
        if (mChildMoveElement == null) {
            mCurrentChildMoves = mCurrentConversation.getInitialUserMoves();
        } else if (mCurrentConversation.isInProgress()) {
            mCurrentChildMoves = mCurrentConversation.getNextUserMoves(mCurrentAgentElement);
        } else {
            endGame();
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

    private void endGame() {
        if (mIsPerfectConversation) {
            int existingScore = UserPreferences.getUserScore(getApplicationContext());
            existingScore++;
            UserPreferences.setUserScore(getApplicationContext(), existingScore);
            DialogFragment gameOverPerfectDialog = new GameOverPerfectDialogFragment();
            gameOverPerfectDialog.show(getSupportFragmentManager(), "gameOverPerf");
        } else {
            GameOverFeedbackDialogFragment gameOverFeedbackDialog = GameOverFeedbackDialogFragment.newInstance
                    (mFeedback.get(0)); //just provide one piece of feedback per dialog
            gameOverFeedbackDialog.show(getSupportFragmentManager(), "gameOverFeedback");
        }

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        //Play again
        finish();
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(SELECTED_CONVO, mCurrentConversation.getTitle());
        startActivity(intent);
    }


    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        //go home - which is on backstack so don't need ot start it again
        finish();
//        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//        startActivity(intent);
    }
}
