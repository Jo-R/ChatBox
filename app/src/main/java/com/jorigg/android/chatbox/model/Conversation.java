package com.jorigg.android.chatbox.model;

import java.util.HashMap;
import java.util.List;

public interface Conversation {

    String getTitle();
    void setTitle(String title);
    HashMap<ConversationElementEnum, List<Sentence>> getConversation();
    void addToConversation(ConversationElementEnum conversationElement, Sentence sentence, Sentence.SpeechType
            speechType);
    List<Sentence> getNextMove(ConversationElementEnum conversationElementJustUsed);
}
