package com.bignerdranch.android.chatbox.model;

import java.util.HashMap;
import java.util.List;

public interface Conversation {

    String getTitle();
    void setTitle(String title);
    HashMap<String, List<Sentence>> getConversation(); //TODO Enum but diff values in each class?
    void addToConversation(String conversationElement,Sentence sentence);
    List<Sentence> getNextMove(String conversationElementJustUsed);
}
