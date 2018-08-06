package com.jorigg.android.chatbox.model;


public class Sentence {


    public enum SpeechType {
        OPEN_QUESTION, CLOSED_QUESTION, ACKNOWLEDGEMENT, GREETING, CLOSING, REQUEST, STATEMENT,
        AGREEMENT, REFUSAL, THANKS;
    }

    private String mContent;
    private SpeechType mSpeechType;


    public Sentence(String content, SpeechType speechType) {
        mContent = content;
        mSpeechType = speechType;
    }

    public String getContent() {
        return mContent;
    }

    public SpeechType getSpeechType() {
        return mSpeechType;
    }

    @Override
    public String toString() {
        return this.mContent;
    }
}
