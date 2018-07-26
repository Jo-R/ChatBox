package com.jorigg.android.chatbox.model;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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

    public boolean addNewConversation(String title, String type) {
        Conversation newConvo = null;

        //check not a duplicate title
        for (Conversation convo : mConversationLibrary) {
            if (convo.getTitle().equals(title)) {
                return false;
            }
        }

        if (type.equals("Ask for Something")) {
            newConvo = new AskForSomething(title);
        }
        mConversationLibrary.add(newConvo);
        return true;
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

    public void deleteConversation(String title) {
        Iterator<Conversation> iterator = mConversationLibrary.iterator();

        while (iterator.hasNext()) {
            Conversation convo = iterator.next();

            if (convo.getTitle().equals(title)) {
                iterator.remove();
            }
        }
    }



}
