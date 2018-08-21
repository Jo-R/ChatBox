package com.jorigg.android.chatbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jorigg.android.chatbox.model.ChatBank;
import com.jorigg.android.chatbox.model.Conversation;

import static com.jorigg.android.chatbox.ParentHomeActivity.TITLE_TO_CONFIG;


public class ConversationListActivity extends AppCompatActivity {

    private RecyclerView mConversationList;

    private ChatBank mChatBank;
    private Conversation mCurrentConversation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);

        mConversationList = findViewById(R.id.convo_recycler_view);
        mConversationList.setLayoutManager(new LinearLayoutManager(this));

        mChatBank = ChatBank.get(this);
        String convoTitle = getIntent().getCharSequenceExtra(TITLE_TO_CONFIG).toString();
        mCurrentConversation = mChatBank.getConversation(convoTitle);

    }
}
