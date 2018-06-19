package com.bignerdranch.android.chatbox.model;

import java.util.ArrayList;
import java.util.List;

public class ChatManager {

    private List<Conversation> mConversationLibrary;

    public ChatManager() {
        mConversationLibrary = new ArrayList<>();
        addConversation();
    }

    //TEMP HARD CODED
    public void addConversation() {
        List<Sentence> dialogue = new ArrayList<>();
        dialogue.add(new Sentence("User", "Agent", "Hi there","Greeting", 1,
                new int[] {2}, false ));
        dialogue.add(new Sentence("Agent", "User", "Hi","Greeting", 2,
                new int[] {3}, false ));
        dialogue.add(new Sentence("User", "Agent", "Can I have some beans",
                "Request", 3, new int[] {4}, false ));
        dialogue.add(new Sentence("Agent", "User", "Yes","ConfirmRequest", 4,
                new int[] {5}, false ));
        mConversationLibrary.add(new Conversation(dialogue,"test request", "RequestingHelp",
                new int[] {1}));
    }

    public Conversation getConversation(String title) {
        for (Conversation conversation : mConversationLibrary) {
            if (conversation.getTitle().equals(title)) {
                return conversation;
            }
        }
        return null;
    }

}
