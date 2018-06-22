package com.bignerdranch.android.chatbox.model;

import java.util.List;

public interface Conversation {

    String getTitle();
    void setTitle(String title);
    List<Sentence> getDialogue();
    void addToDialogue(Sentence sentence);
}
