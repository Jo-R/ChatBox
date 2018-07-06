package com.jorigg.android.chatbox.model;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.Map;

public interface Conversation<E extends ConversationElementEnum> {

    String getTitle();
    void setTitle(String title);
    void addToConversation(E conversationElement, Sentence sentence);
    Pair<E, Sentence> getNextAgentMove(E lastUserMove);
    Map<E, ArrayList<Sentence>> getNextUserMoves(E lastAgentMove);
    User.UserType getInitiator();
    ArrayList<Sentence> getInitialUserResponses();
    ArrayList<E> getInitialElements();
}
