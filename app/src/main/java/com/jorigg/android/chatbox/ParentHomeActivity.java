package com.jorigg.android.chatbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.jorigg.android.chatbox.model.ChatBank;

public class ParentHomeActivity extends AppCompatActivity{

    private Spinner mEditSpinner;
    private Spinner mCreateSpinner;
    private ChatBank mChatBank;
    private Button mCreateButton;
    private Button mEditButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home);

        mChatBank = ChatBank.get(this);
        mEditSpinner = findViewById(R.id.edit_spinner);
        mCreateSpinner = findViewById(R.id.create_new_spinner);
        populateSpinner();

        mCreateButton = findViewById(R.id.create_new_button);
        mEditButton = findViewById(R.id.edit_button);

        //TODO button listeners


    }

    private void populateSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, mChatBank.getChatTitles());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEditSpinner.setAdapter(adapter);
        //TODO create spinner
    }
}
