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

    //TEMP HARD CODED - is next the next item for that user rather than the next item in the
    // dialgoue since 2x moves shown on screen at same time??
    public void addConversation() {
        List<Sentence> dialogue = new ArrayList<>();
        dialogue.add(new Sentence("User", "Agent", "Hi there","Greeting", 1,
                new int[] {3}, false ));
        dialogue.add(new Sentence("Agent", "User", "Hi","Greeting", 2,
                new int[] {4}, false ));
        dialogue.add(new Sentence("User", "Agent", "Can I have some beans",
                "Request", 3, new int[] {4}, false ));
        dialogue.add(new Sentence("Agent", "User", "Yes","ConfirmRequest", 4,
                new int[] {}, false )); //TODO how to handle last move in convo??
        mConversationLibrary.add(new AskForSomething(dialogue,"test request"));
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
