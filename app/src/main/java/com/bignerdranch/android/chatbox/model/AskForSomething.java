package com.bignerdranch.android.chatbox.model;

import java.util.HashMap;
import java.util.List;

public class AskForSomething implements Conversation {

    private HashMap<String, List<Sentence>> mDialogue;
    private String mTitle; //user generated convo name

    public AskForSomething(String title) {
        mDialogue = new HashMap<>();
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public HashMap<String, List<Sentence>> getConversation() {
        return mDialogue;
    }

    @Override
    public void addToConversation(String conversationElement, Sentence sentence) {
        //add the sentence to the arrylist associated with the key
    }

    @Override
    public List<Sentence> getNextMove(String conversationElementJustUsed) {
        return null;
        //takes most recent move made by user and works out next conversationElements to display
    }
}
