package com.jorigg.android.chatbox.model;

public interface ConversationElementEnum {

    String name();
    User.UserType getSpeaker();
    Sentence.SpeechType getSpeechType();
    String getElementDescription();
}
