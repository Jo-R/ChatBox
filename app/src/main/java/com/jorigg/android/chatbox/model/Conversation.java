package com.jorigg.android.chatbox.model;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface Conversation<E extends ConversationElementEnum> {

    String getTitle();
    void setTitle(String title);
    void addToConversation(E conversationElement, Sentence sentence);
    Pair<E, Sentence> getNextAgentMove(E lastUserMove);
    HashMap<E, ArrayList<Sentence>> getNextUserMoves(E lastAgentMove);
    User.UserType getInitiator();
    Sentence getInitialAgentResponse();
    ConversationElementEnum getInitialAgentElement();
    HashMap<E, ArrayList<Sentence>> getInitialUserMoves();
}
