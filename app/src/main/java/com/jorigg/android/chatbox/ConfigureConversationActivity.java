package com.jorigg.android.chatbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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

    Spinner mSelectExistingSentenceSpinner;
    ImageButton mRemoveSentenceButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_conversation);

        mChatBank = ChatBank.get(this);

        String convoTitle = getIntent().getCharSequenceExtra(TITLE_TO_CONFIG).toString();
        mCurrentConversation = mChatBank.getConversation(convoTitle);

        mSelectElementSpinner = findViewById(R.id.config_elements_spinner);
        populateElementSpinner();
        mSelectElementSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                populateExistingSentenceSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing?
            }
        });

        mInputSentenceField = findViewById(R.id.config_input_sentence_field);
        mInputSentenceButton = findViewById(R.id.config_add_input_sentence_button);
        mInputSentenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConversationElementEnum element = getElementFromString(mSelectElementSpinner.getSelectedItem().toString());
                mCurrentConversation.addToConversation(element, mInputSentenceField.getText().toString());
                mInputSentenceField.setText("");
            }
        });

        mSelectExistingSentenceSpinner = findViewById(R.id.config_existing_spinner);
        populateExistingSentenceSpinner();
        mRemoveSentenceButton = findViewById(R.id.config_remove_existing_button);
        mRemoveSentenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the slected text and the selected element
                ConversationElementEnum element = getElementFromString(mSelectElementSpinner.getSelectedItem().toString());
                String contentToRem = mSelectExistingSentenceSpinner.getSelectedItem().toString();
                //call method on convo elem
                mCurrentConversation.removeSentenceFromConversation(contentToRem, element);
                populateExistingSentenceSpinner();
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

    private void populateExistingSentenceSpinner() {
        ConversationElementEnum element = getElementFromString(mSelectElementSpinner
                .getSelectedItem().toString());
        ArrayList<Sentence> elements = mCurrentConversation.getElementOptions(element);

        if (elements != null) {
            ArrayList<String> strings = new ArrayList<>();

            for (Sentence sentence : elements) {
                strings.add(sentence.getContent());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                    .simple_spinner_item, strings);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSelectExistingSentenceSpinner.setAdapter(adapter);
        }
    }

    private ConversationElementEnum getElementFromString(String elemName) {
        //TODO remove hard code AFS
        return AskForSomething.AskForSomethingElements.valueOf(elemName);
    }
}
