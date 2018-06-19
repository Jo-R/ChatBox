package com.bignerdranch.android.chatbox.model;

import java.util.List;

public class Conversation {

    private List<Sentence> mDialogue;
    private String mTitle; //user generated convo name
    private String mType; //overall type per model, might be an enum
    private List<Integer> mStartItems; //ids of first item(s) to be displayed in conversation

    public Conversation(List<Sentence> dialogue, String title, String type, List<Integer> startItems) {
        mDialogue = dialogue;
        mTitle = title;
        mType = type;
        mStartItems = startItems;
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

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public List<Integer> getStartItems() {
        return mStartItems;
    }

    public void setStartItems(List<Integer> startItems) {
        mStartItems = startItems;
    }
}
