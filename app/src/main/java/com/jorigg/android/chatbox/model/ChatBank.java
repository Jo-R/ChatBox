package com.jorigg.android.chatbox.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ChatBank {

    private List<Conversation> mConversationLibrary;
    private Context mContext;
    private static ChatBank sChatBank;

    public static ChatBank get(Context context) {
        if (sChatBank == null) {
            sChatBank = new ChatBank(context);
        }
        return sChatBank;
    }

    private ChatBank(Context context) {
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
