package com.jorigg.android.chatbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.jorigg.android.chatbox.model.AskForSomething;
import com.jorigg.android.chatbox.model.ChatBank;
import com.jorigg.android.chatbox.model.ConversationElementEnum;

import java.util.ArrayList;
import java.util.Arrays;

public class ConfigureConversationActivity extends AppCompatActivity {

    ChatBank mChatBank;

    ImageButton mAddTitleButton;
    EditText mAddTitleField;

    Spinner mSelectElementSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_conversation);

        mChatBank = ChatBank.get(this);

        //TODO shuld this show existing if eists and how would that be editable???
        mAddTitleField = findViewById(R.id.config_edit_title);
        mAddTitleButton= findViewById(R.id.config_edit_title_button);
        mAddTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChatBank.addNewConversation(mAddTitleField.getText().toString(), "Ask for Something");
            }
        });

        mSelectElementSpinner = findViewById(R.id.config_elements_spinner);
        populateElementSpinner();
    }

    private void populateElementSpinner() {
        ArrayList<String> elements = new ArrayList<>();

        //TODO is hard coded for ask for something
        AskForSomething.AskForSomethingElements[] values = AskForSomething.AskForSomethingElements
                .values();
        for (AskForSomething.AskForSomethingElements element : values) {
            elements.add(element.name());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, elements);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSelectElementSpinner.setAdapter(adapter);
    }
}
