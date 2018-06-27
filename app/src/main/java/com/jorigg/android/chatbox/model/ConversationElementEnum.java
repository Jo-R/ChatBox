package com.jorigg.android.chatbox.model;

public interface ConversationElementEnum {

    String name();
    User.UserType getSpeaker();
    Sentence.SpeechType getSpeechType(String elementName);
    //https://stackoverflow.com/questions/3054247/how-to-define-properties-for-enum-items
}
