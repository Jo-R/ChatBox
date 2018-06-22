package com.bignerdranch.android.chatbox.model;


public class Sentence {

    private String mSender; //?TODO ?is this needed in here - is it not defined by conversation
    private String mContent;
    private boolean mIsThought; //TODO also maybe part of convo as a element??


    //idea being that construct sentences in conversation but without the content which is added
    // later
    public Sentence(String sender, boolean isThought) {
        mSender = sender;
        mIsThought = isThought;
    }

    //ctor all fields
    public Sentence(String sender, String content, boolean isThought) {
        mSender = sender;
        mContent = content;
        mIsThought = isThought;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getSender() {
        return mSender;
    }


    public String getContent() {
        return mContent;
    }

    public boolean isThought() {
        return mIsThought;
    }
}
