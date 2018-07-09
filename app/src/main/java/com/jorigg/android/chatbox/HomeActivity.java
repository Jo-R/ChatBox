package com.jorigg.android.chatbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.jorigg.android.chatbox.model.ChatBank;
import com.jorigg.android.chatbox.model.Sentence;

public class HomeActivity extends AppCompatActivity {

    public static final String SELECTED_CONVO = "selectedConvo";

    private Spinner mChooseConvoSpinner;
    private Button mStartChattingBtn;

    private ChatBank mChatBank;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
        populateSpinner();
    }

    private void populateSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, mChatBank.getChatTitles());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mChooseConvoSpinner.setAdapter(adapter);
    }
}
