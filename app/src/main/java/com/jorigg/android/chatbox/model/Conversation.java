package com.jorigg.android.chatbox.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Conversation<E extends Enum<E>> {

    String getTitle();
    void setTitle(String title);
    Map<E, ArrayList<Sentence>> getConversationDetails();
    void addToConversation(E conversationElement, Sentence sentence);
    List<Sentence> getNextElements(E conversationElementJustUsed);
    User.UserType getInitiator();
    List<E> getInitialElements();
    List<Sentence> getElementContent(E conversationElement);
}
