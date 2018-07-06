package com.jorigg.android.chatbox.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Conversation<E extends ConversationElementEnum> {

    String getTitle();
    void setTitle(String title);
    void addToConversation(E conversationElement, Sentence sentence);
    ArrayList<Sentence> getNextMove(E conversationElementJustUsed);
    User.UserType getInitiator();
    ArrayList<Sentence> getInitialUserResponses();
    ArrayList<E> getInitialElements();
}
