package com.jorigg.android.chatbox.model;

public interface ConversationElementEnum {

    String name();
    UserPreferences.UserType getSpeaker();
    Sentence.SpeechType getSpeechType();
    String getElementDescription();
    boolean isThought();
}
