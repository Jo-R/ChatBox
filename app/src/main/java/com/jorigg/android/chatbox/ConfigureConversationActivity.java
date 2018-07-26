package com.jorigg.android.chatbox;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.jorigg.android.chatbox.model.AskForSomething;
import com.jorigg.android.chatbox.model.ChatBank;
import com.jorigg.android.chatbox.model.Conversation;
import com.jorigg.android.chatbox.model.ConversationElementEnum;
import com.jorigg.android.chatbox.model.Sentence;
import com.jorigg.android.chatbox.model.SentenceBank;
import com.jorigg.android.chatbox.model.XmlWriteRead;

import java.util.ArrayList;
import java.util.logging.XMLFormatter;

import static com.jorigg.android.chatbox.ParentHomeActivity.TITLE_TO_CONFIG;

public class ConfigureConversationActivity extends AppCompatActivity {

    ChatBank mChatBank;
    Conversation mCurrentConversation;
    SentenceBank mSentenceBank;

    ImageButton mInputSentenceButton;
    EditText mInputSentenceField;

    Spinner mSelectElementSpinner;

    Spinner mExistingChatSentenceSpinner;
    ImageButton mRemoveChatSentenceButton;

    TextView mDisplayWhoseTurn;
    TextView mDisplayElementDescription;

    Spinner mSentenceBankSpinner;
    ImageButton mAddFromSentenceBankButton;
    ImageButton mRemoveFromSentenceBankButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_conversation);

        mChatBank = ChatBank.get(this);
        mSentenceBank = SentenceBank.get(this);

        String convoTitle = getIntent().getCharSequenceExtra(TITLE_TO_CONFIG).toString();
        mCurrentConversation = mChatBank.getConversation(convoTitle);

        mSelectElementSpinner = findViewById(R.id.config_elements_spinner);
        populateElementSpinner();
        mSelectElementSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                populateExistingChatSentenceSpinner();
                populateSentenceBankSpinner();
                displayElementDetails();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing?
            }
        });


        mDisplayWhoseTurn = findViewById(R.id.config_whose_turn_display);
        mDisplayElementDescription = findViewById(R.id.config_element_description);
        displayElementDetails();

        mInputSentenceField = findViewById(R.id.config_input_sentence_field);
        mInputSentenceButton = findViewById(R.id.config_add_input_sentence_button);
        mInputSentenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConversationElementEnum element = getElementFromString();
                mCurrentConversation.addToConversation(element, mInputSentenceField.getText().toString());
                mSentenceBank.addSentence(new Sentence(mInputSentenceField.getText().toString(),
                        element.getSpeechType()));
                mInputSentenceField.setText("");
                populateExistingChatSentenceSpinner();
                populateSentenceBankSpinner();
            }
        });


        mSentenceBankSpinner = findViewById(R.id.config_sentence_bank_spinner);
        populateSentenceBankSpinner();
        mAddFromSentenceBankButton = findViewById(R.id.config_add_sentence_bank_button);
        mAddFromSentenceBankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConversationElementEnum element = getElementFromString();
                mCurrentConversation.addToConversation(element, mSentenceBankSpinner
                        .getSelectedItem().toString());
                populateExistingChatSentenceSpinner();
            }
        });
        mRemoveFromSentenceBankButton = findViewById(R.id.config_remove_sentence_bank_button);
        mRemoveFromSentenceBankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConversationElementEnum element = getElementFromString();
                mSentenceBank.removeSentence(mSentenceBankSpinner
                        .getSelectedItem().toString(), element.getSpeechType());
                populateSentenceBankSpinner();
            }
        });



        mExistingChatSentenceSpinner = findViewById(R.id.config_existing_spinner);
        populateExistingChatSentenceSpinner();
        mRemoveChatSentenceButton = findViewById(R.id.config_remove_existing_button);
        mRemoveChatSentenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the slected text and the selected element
                ConversationElementEnum element = getElementFromString();
                String contentToRem = mExistingChatSentenceSpinner.getSelectedItem().toString();
                //call method on convo elem
                mCurrentConversation.removeSentenceFromConversation(contentToRem, element);
                populateExistingChatSentenceSpinner();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        Context ctxt = this.getApplicationContext();
        XmlWriteRead.writeChatsToXML(mChatBank.getConversationLibrary(), ctxt);
        XmlWriteRead.writeSentencesToXml(mSentenceBank.getSentenceLibrary(), ctxt);
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

    private void populateSentenceBankSpinner() {
        ConversationElementEnum element = getElementFromString();
        ArrayList<Sentence> sentences = mSentenceBank.getSentences(element.getSpeechType());

        if (sentences != null) {
            ArrayList<String> strings = new ArrayList<>();

            for (Sentence sentence : sentences) {
                strings.add(sentence.getContent());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                    .simple_spinner_item, strings);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSentenceBankSpinner.setAdapter(adapter);
        }
    }

    private void populateExistingChatSentenceSpinner() {
        ConversationElementEnum element = getElementFromString();
        ArrayList<Sentence> elements = mCurrentConversation.getElementOptions(element);
        ArrayList<String> strings = new ArrayList<>();

        if (elements != null) {
            for (Sentence sentence : elements) {
                strings.add(sentence.getContent());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_item, strings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mExistingChatSentenceSpinner.setAdapter(adapter);
    }

    private ConversationElementEnum getElementFromString() {
        //TODO remove hard code AFS
        return AskForSomething.AskForSomethingElements.valueOf(mSelectElementSpinner
                .getSelectedItem().toString());
    }

    private void displayElementDetails() {
        ConversationElementEnum element = getElementFromString();
        mDisplayWhoseTurn.setText(element.getSpeaker().toString());
        mDisplayElementDescription.setText(element.getElementDescription());
    }
}
