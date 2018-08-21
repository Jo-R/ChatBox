package com.jorigg.android.chatbox;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.jorigg.android.chatbox.model.AskForSomething;
import com.jorigg.android.chatbox.model.ChatBank;
import com.jorigg.android.chatbox.model.Conversation;
import com.jorigg.android.chatbox.model.ConversationElementEnum;
import com.jorigg.android.chatbox.model.Greeting;
import com.jorigg.android.chatbox.model.Sentence;

import java.util.ArrayList;
import java.util.HashMap;

import static com.jorigg.android.chatbox.ParentHomeActivity.TITLE_TO_CONFIG;


public class ConversationListActivity extends AppCompatActivity {

    private RecyclerView mConversationRecycler;

    private ChatBank mChatBank;
    private Conversation mCurrentConversation;
    private ElementAdapter mElementAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);

        mConversationRecycler = findViewById(R.id.convo_recycler_view);
        mConversationRecycler.setLayoutManager(new LinearLayoutManager(this));

        updateUI();

    }

    private void updateUI() {
        mChatBank = ChatBank.get(this);
        String convoTitle = getIntent().getCharSequenceExtra(TITLE_TO_CONFIG).toString();
        mCurrentConversation = mChatBank.getConversation(convoTitle);

        mElementAdapter = new ElementAdapter(mCurrentConversation.getDialogue(),
                mCurrentConversation.getClass().getSimpleName().toString());
        mConversationRecycler.setAdapter(mElementAdapter);

    }


    private class ElementHolder extends RecyclerView.ViewHolder {

        private TextView mElementTextView;
        private TextView mTurnTextView;
        private TextView mDescriptionTextView;
        private Spinner mExistingSentenceSpinner;

        public ElementHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_conversation, parent, false));

            mElementTextView = itemView.findViewById(R.id.list_item_element);
            mTurnTextView = itemView.findViewById(R.id.list_item_whose_turn);
            mDescriptionTextView = itemView.findViewById(R.id.list_item_description);
            mExistingSentenceSpinner = itemView.findViewById(R.id.list_existing_items);
        }

        public void bind(ArrayList<Sentence> elemContents, ConversationElementEnum element) {

            ArrayList<String> elemContentsStrings = new ArrayList<>();

            for (Sentence sent : elemContents) {
                elemContentsStrings.add(sent.getContent());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout
                    .simple_spinner_item, elemContentsStrings);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mExistingSentenceSpinner.setAdapter(adapter);

            mElementTextView.setText(element.name());
            mTurnTextView.setText(element.getSpeaker().toString());
            mDescriptionTextView.setText(element.getElementDescription());
        }
    }

    private class ElementAdapter extends RecyclerView.Adapter<ElementHolder> {

        HashMap<ConversationElementEnum, ArrayList<Sentence>> mDialogue;
        ConversationElementEnum[] mOrderedElements;
        ArrayList<ArrayList<Sentence>> mOrderedSentences;

        //todo this is not ideal cf having to cast
        public ElementAdapter(HashMap<ConversationElementEnum, ArrayList<Sentence>> dialogue,
                              String convoType) {
            mDialogue = dialogue;

            //hash map not ordered so get each element enum in order then get each Sentence ALIst
            // belonging to that key in order in another list
            if (convoType.equals("AskForSomething")) {
                mOrderedElements = AskForSomething.AskForSomethingElements.values();
            } else if (convoType.equals("Greeting")) {
                mOrderedElements = Greeting.GreetingElements.values();
            }

            mOrderedSentences = new ArrayList<>();
            for (ConversationElementEnum elem : mOrderedElements) {
                mOrderedSentences.add(mDialogue.get(elem));
            }

        }

        @NonNull
        @Override
        public ElementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());

            return new ElementHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ElementHolder holder, int position) {
            //get the sentences belonging to element
            ArrayList<Sentence> elemContents = mOrderedSentences.get(position);
            ConversationElementEnum elem = mOrderedElements[position];

            holder.bind(elemContents, elem);
        }

        @Override
        public int getItemCount() {
            return mDialogue.size();
        }
    }
}
