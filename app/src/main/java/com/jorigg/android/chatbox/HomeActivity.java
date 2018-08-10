package com.jorigg.android.chatbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private TextView mUserScore;

    private ChatBank mChatBank;
    private SentenceBank mSentenceBank;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mWelcomeText = findViewById(R.id.welcome_text);
        mWelcomeText.setText("Hi " + UserPreferences.getUserName(getApplicationContext()));

        mUserScore = findViewById(R.id.displayScore);
        mUserScore.setText(Integer.toString(UserPreferences.getUserScore(getApplicationContext())));


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
        mSentenceBank = SentenceBank.get(this);
        XmlWriteRead.parseChatsFromXml(getApplicationContext());
        XmlWriteRead.parseSentencesFromXml(getApplicationContext());
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
}
