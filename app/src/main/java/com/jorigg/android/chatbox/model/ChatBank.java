package com.jorigg.android.chatbox.model;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatBank {

    private ArrayList<Conversation> mConversationLibrary;
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
    }

    public ArrayList<String> getChatTitles() {
        ArrayList<String> titles = new ArrayList<>();
        for (Conversation convo : mConversationLibrary) {
            titles.add(convo.getTitle());
        }
        return titles;
    }

    public void addNewConversation(String title, String type) {
        Conversation newConvo = null;
        if (type.equals("Ask for Something")) {
            newConvo = new AskForSomething(title);
        }
        mConversationLibrary.add(newConvo);
    }

    public ArrayList<Conversation> getConversationLibrary() {
        return mConversationLibrary;
    }


    public Conversation getConversation(String title) {
        for ( Conversation conversation : mConversationLibrary) {
            if (conversation.getTitle().equals(title)) {
                return conversation;
            }
        }
        return null;
    }



}
