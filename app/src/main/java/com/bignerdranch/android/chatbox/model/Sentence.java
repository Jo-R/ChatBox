package com.bignerdranch.android.chatbox.model;

import java.util.List;

public class Sentence {

    private String mSender;
    private String mReceiver;
    private String mContent;
    private String mSpeechType; //might be better as an enum??
    private int mId;
    private List<Integer> mNextItems;
    private boolean mIsThought; //can this be a speech type??


    //idea being that construct sentences in conversation but without the content which is added
    // later
    public Sentence(String sender, String receiver, String speechType, int id, List<Integer> nextItems,
                    boolean isThought) {
        mSender = sender;
        mReceiver = receiver;
        mSpeechType = speechType;
        mId = id;
        mNextItems = nextItems;
        mIsThought = isThought;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getSender() {
        return mSender;
    }

    public String getReceiver() {
        return mReceiver;
    }

    public String getContent() {
        return mContent;
    }

    public String getSpeechType() {
        return mSpeechType;
    }

    public int getId() {
        return mId;
    }

    public List<Integer> getNextItems() {
        return mNextItems;
    }

    public boolean isThought() {
        return mIsThought;
    }
}
