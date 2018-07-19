package com.jorigg.android.chatbox;

import android.content.Intent;
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
import com.jorigg.android.chatbox.model.Conversation;
import com.jorigg.android.chatbox.model.ConversationElementEnum;
import com.jorigg.android.chatbox.model.Sentence;

import java.util.ArrayList;
import java.util.Arrays;

import static com.jorigg.android.chatbox.ParentHomeActivity.TITLE_TO_CONFIG;

public class ConfigureConversationActivity extends AppCompatActivity {

    ChatBank mChatBank;
    Conversation mCurrentConversation;

    ImageButton mInputSentenceButton;
    EditText mInputSentenceField;

    Spinner mSelectElementSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_conversation);

        mChatBank = ChatBank.get(this);

        String convoTitle = getIntent().getCharSequenceExtra(TITLE_TO_CONFIG).toString();
        mCurrentConversation = mChatBank.getConversation(convoTitle);

        mSelectElementSpinner = findViewById(R.id.config_elements_spinner);
        populateElementSpinner();

        mInputSentenceField = findViewById(R.id.config_input_sentence_field);
        mInputSentenceButton = findViewById(R.id.config_add_input_sentence_button);
        mInputSentenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO remove hard code of AFS
                ConversationElementEnum element = AskForSomething.AskForSomethingElements.valueOf
                        (mSelectElementSpinner.getSelectedItem().toString());

                mCurrentConversation.addToConversation(element, mInputSentenceField.getText().toString());
            }
        });


    }

    private void populateElementSpinner() {
        ArrayList<String> elements = new ArrayList<>();

        if (mCurrentConversation instanceof AskForSomething) {
            AskForSomething.AskForSomethingElements[] values = AskForSomething.AskForSomethingElements
                    .values();
            for (AskForSomething.AskForSomethingElements element : values) {
                elements.add(element.name());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, elements);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSelectElementSpinner.setAdapter(adapter);
    }
}
