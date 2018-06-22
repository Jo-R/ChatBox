package com.bignerdranch.android.chatbox.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ChatManager {

    private List<Conversation> mConversationLibrary;
    private Context mContext;
    private static ChatManager sChatManager;

    public static ChatManager get(Context context) {
        if (sChatManager == null) {
            sChatManager = new ChatManager(context);
        }
        return sChatManager;
    }

    private ChatManager(Context context) {
        mContext = context.getApplicationContext();
        mConversationLibrary = new ArrayList<>();
        addConversation();
    }


    public void addConversation() {
       //add a new conversation - create it and add to the library
    }

    public Conversation getConversation(String title) {
        for ( Conversation conversation : mConversationLibrary) {
            if (conversation.getTitle().equals(title)) {
                return conversation;
            }
        }
        return null;
    }

    //does this need a getNextMove method as well so controller calls this which calls the
    // conversation or is that daft??

}
