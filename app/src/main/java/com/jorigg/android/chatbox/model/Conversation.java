package com.jorigg.android.chatbox.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Conversation<E extends Enum<E>> {

    String getTitle();
    void setTitle(String title);
    EnumMap<E, ArrayList<Sentence>> getConversationDetails();
    void addToConversation(E conversationElement, Sentence sentence);
    ArrayList<Sentence> getNextElements(E conversationElementJustUsed);
    User.UserType getInitiator();
    ArrayList<Sentence> getInitialUserResponses();
    ArrayList<Sentence> getElementContent(E conversationElement);
}
