package com.jorigg.android.chatbox.model;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public interface Conversation<E extends ConversationElementEnum> {

    String getTitle();
    void setTitle(String title);
    boolean addToConversation(E conversationElement, String content);
    Pair<E, Sentence> getNextAgentMove(E lastUserMove);
    HashMap<E, ArrayList<Sentence>> getNextUserMoves(E lastAgentMove);
    User.UserType getInitiator();
    Sentence getInitialAgentResponse();
    ConversationElementEnum getInitialAgentElement();
    HashMap<E, ArrayList<Sentence>> getInitialUserMoves();
    ArrayList<Sentence> getElementOptions(E element);
    void removeSentenceFromConversation(String sentence, E element);
    HashMap<E, ArrayList<Sentence>> getDialogue();
    boolean isInProgress();
    boolean hasAnEntryPerElement();
}
