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

import com.jorigg.android.chatbox.model.ChatBank;
import com.jorigg.android.chatbox.model.Conversation;
import com.jorigg.android.chatbox.model.ConversationElementEnum;
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

        mElementAdapter = new ElementAdapter(mCurrentConversation.getDialogue());
        mConversationRecycler.setAdapter(mElementAdapter);

    }


    private class ElementHolder extends RecyclerView.ViewHolder {

        public ElementHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_conversation, parent, false));
        }
    }

    private class ElementAdapter extends RecyclerView.Adapter<ElementHolder> {

        HashMap<ConversationElementEnum, ArrayList<Sentence>> mDialogue;

        public ElementAdapter(HashMap<ConversationElementEnum, ArrayList<Sentence>> dialogue) {
            mDialogue = dialogue;
        }

        @NonNull
        @Override
        public ElementHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = getApplicationContext(); //this might not work? see p 174 in book
            LayoutInflater layoutInflater = LayoutInflater.from(context);

            return new ElementHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ElementHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mDialogue.size();
        }
    }
}
