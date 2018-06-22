package com.bignerdranch.android.chatbox.model;

import java.util.HashMap;
import java.util.List;

public interface Conversation {

    String getTitle();
    void setTitle(String title);
    HashMap<String, List<Sentence>> getConversation();
    void addToConversation(String conversationElement,Sentence sentence);
    List<Sentence> getNextMove(String conversationElementJustUsed);
}
