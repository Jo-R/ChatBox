package com.jorigg.android.chatbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jorigg.android.chatbox.model.ChatBank;
import com.jorigg.android.chatbox.model.Sentence;
import com.jorigg.android.chatbox.model.SentenceBank;
import com.jorigg.android.chatbox.model.UserPreferences;
import com.jorigg.android.chatbox.model.XmlWriteRead;

import java.util.ArrayList;
import java.util.Iterator;

public class HomeActivity extends AppCompatActivity {

    public static final String SELECTED_CONVO = "selectedConvo";

    private Spinner mChooseConvoSpinner;
    private Button mStartChattingBtn;
    private TextView mWelcomeText;
    private TextView mBadgeOne;
    private TextView mBadgeTwo;
    private TextView mBadgeThree;
    private TextView mBadgeFour;
    private TextView mBadgeFive;
    private TextView mRewardLabel;

    private ChatBank mChatBank;
    private SentenceBank mSentenceBank;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mWelcomeText = findViewById(R.id.welcome_text);
        mWelcomeText.setText("Hi " + UserPreferences.getUserName(getApplicationContext()));


        mBadgeOne = findViewById(R.id.badge1);
        mBadgeTwo = findViewById(R.id.badge2);
        mBadgeThree = findViewById(R.id.badge3);
        mBadgeFour = findViewById(R.id.badge4);
        mBadgeFive = findViewById(R.id.badge5);

       showBadges();

       mRewardLabel = findViewById(R.id.rewards_label);
       setRewardMessage();


        mChooseConvoSpinner = findViewById(R.id.home_choose_conversation_spinner);
        mStartChattingBtn = findViewById(R.id.home_start_chatting_btn);
        mStartChattingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start chat activity for selected convo
                Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
                intent.putExtra(SELECTED_CONVO, String.valueOf(mChooseConvoSpinner
                        .getSelectedItem()));
                startActivity(intent);
            }
        });

        mChatBank = ChatBank.get(this);

//

        mSentenceBank = SentenceBank.get(this);
        XmlWriteRead.parseChatsFromXml(getApplicationContext());
        XmlWriteRead.parseSentencesFromXml(getApplicationContext());

        if (mChatBank.getConversationLibrary().isEmpty()) {
            mStartChattingBtn.setEnabled(false);
        }
        populateSpinner();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_parent_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.parent_mode:
                Intent intent = new Intent(HomeActivity.this, ParentHomeActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        populateSpinner();
        if (!mChatBank.getConversationLibrary().isEmpty()) {
            mStartChattingBtn.setEnabled(true);
        }
        mWelcomeText.setText("Hi " + UserPreferences.getUserName(getApplicationContext()));
        showBadges();
        setRewardMessage();
    }

    private void populateSpinner() {
        //only want to display the fully complete ones
        ArrayList<String> chatTitles = mChatBank.getChatTitles();
        Iterator<String> iterator = chatTitles.iterator();

        while (iterator.hasNext()) {
            String title = iterator.next();
            if (!mChatBank.getConversation(title).hasAnEntryPerElement()) {
                iterator.remove();
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, chatTitles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mChooseConvoSpinner.setAdapter(adapter);
    }

    private void showBadges() {
        int userScore = UserPreferences.getUserScore(getApplicationContext());

        if (userScore >= 1) {
            mBadgeOne.setBackground(getResources().getDrawable(R.drawable.badge2b));
        }
        if (userScore >= 10) {
            mBadgeTwo.setBackground(getResources().getDrawable(R.drawable.badge2b));
        }
        if (userScore >= 50) {
            mBadgeThree.setBackground(getResources().getDrawable(R.drawable.badge2b));
        }
        if (userScore >= 100) {
            mBadgeFour.setBackground(getResources().getDrawable(R.drawable.badge2b));
        }
        if (userScore >= 150) {
            mBadgeFive.setBackground(getResources().getDrawable(R.drawable.badge2b));
        }
    }

    //todo 75 should be configurable - also what happens here when goal is reached?
    private void setRewardMessage() {
        int needThisManyMore = 75 - UserPreferences.getUserScore(getApplicationContext());
        String message = "";

        if (needThisManyMore > 0) {
            message = "You need " + needThisManyMore + " more perfect conversations to get " +
                    "your reward: " + UserPreferences.getReward(getApplicationContext());
        } else if (needThisManyMore == 0) {
            message = "CONGRATULATIONS you have won your reward: " + UserPreferences
                    .getReward(getApplicationContext());
        }
        mRewardLabel.setText(message);
    }
}
