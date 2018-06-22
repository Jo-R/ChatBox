package com.bignerdranch.android.chatbox.model;

import java.util.List;

public class AskForSomething implements Conversation {

    private List<Sentence> mDialogue;
    private String mTitle; //user generated convo name

    public AskForSomething(List<Sentence> dialogue, String title) {
        mDialogue = dialogue;
        mTitle = title;
    }

    public List<Sentence> getDialogue() {
        return mDialogue;
    }

    public void addToDialogue(Sentence sentence) {
        mDialogue.add(sentence);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }


}
