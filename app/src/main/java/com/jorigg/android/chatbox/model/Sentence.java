package com.jorigg.android.chatbox.model;


public class Sentence {

    /**
     * Each speech type has an associated arrayList in SentenceBank so need to add that as well if
     * add any more
     */
    public enum SpeechType {
        OPEN_QUESTION, CLOSED_QUESTION, ACKNOWLEDGEMENT, GREETING, CLOSING, REQUEST, STATEMENT,
        AGREEMENT, REFUSAL, THANKS;
    }

    private String mContent;
    private SpeechType mSpeechType;



    //ctor all fields
    public Sentence(String content, SpeechType speechType) {
        mContent = content;
        mSpeechType = speechType;
    }

    public void setContent(String content) {
        mContent = content;
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
