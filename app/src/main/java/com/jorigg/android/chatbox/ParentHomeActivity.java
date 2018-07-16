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

import java.util.ArrayList;

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
        populateSpinners();

        mCreateButton = findViewById(R.id.create_new_button);
        mEditButton = findViewById(R.id.edit_button);

        //TODO edit button listener

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selected = String.valueOf(mCreateSpinner.getSelectedItem());
                Intent intent = null;
                if (selected.equals("Ask for Something")) {
                    intent = new Intent(ParentHomeActivity.this, CreateAskForSomethingActivity.class);
                }
                startActivity(intent);
            }
        });


    }

    private void populateSpinners() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, mChatBank.getChatTitles());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEditSpinner.setAdapter(adapter);

        ArrayList<String> templates = new ArrayList<>();
        templates.add("Ask for Something");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, templates);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCreateSpinner.setAdapter(adapter2);
    }
}
