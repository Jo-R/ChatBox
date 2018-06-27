package com.jorigg.android.chatbox;

import android.content.Context;

import com.jorigg.android.chatbox.model.Sentence;

import java.util.ArrayList;
import java.util.List;

public class SentenceBank {

    private List<Sentence> mOpenQuestionSentenceLibrary;
    private List<Sentence> mClosedQuestionSentenceLibrary;
    private List<Sentence> mAcknowledgementSentenceLibrary;
    private List<Sentence> mGreetingSentenceLibrary;
    private List<Sentence> mClosingSentenceLibrary;
    private List<Sentence> mRequestSentenceLibrary;
    private List<Sentence> mStatementSentenceLibrary;
    private List<Sentence> mAgreementSentenceLibrary;
    private List<Sentence> mRefusalSentenceLibrary;
    private List<Sentence> mThanksSentenceLibrary;
    public static SentenceBank sSentenceBank;
    private Context mContext;

    public SentenceBank get(Context context) {
        if (sSentenceBank == null) {
            sSentenceBank = new SentenceBank(context);
        }
        return sSentenceBank;
    }

    private SentenceBank(Context context) {
        mContext = context.getApplicationContext();
        mOpenQuestionSentenceLibrary = new ArrayList<>();
        mClosedQuestionSentenceLibrary = new ArrayList<>();
        mAcknowledgementSentenceLibrary = new ArrayList<>();
        mGreetingSentenceLibrary = new ArrayList<>();
        mRequestSentenceLibrary = new ArrayList<>();
        mClosingSentenceLibrary = new ArrayList<>();
        mStatementSentenceLibrary = new ArrayList<>();
        mAgreementSentenceLibrary = new ArrayList<>();
        mRefusalSentenceLibrary = new ArrayList<>();
        mThanksSentenceLibrary = new ArrayList<>();
    }

    public void addSentence(Sentence sentence) {
        switch (sentence.getSpeechType()) {
            case OPEN_QUESTION:
                mOpenQuestionSentenceLibrary.add(sentence);
                break;
            case CLOSED_QUESTION:
                mClosedQuestionSentenceLibrary.add(sentence);
                break;
            case ACKNOWLEDGEMENT:
                mAcknowledgementSentenceLibrary.add(sentence);
                break;
            case GREETING:
                mGreetingSentenceLibrary.add(sentence);
                break;
            case CLOSING:
                mClosingSentenceLibrary.add(sentence);
                break;
            case REQUEST:
                mRequestSentenceLibrary.add(sentence);
                break;
            case STATEMENT:
                mStatementSentenceLibrary.add(sentence);
                break;
            case AGREEMENT:
                mAgreementSentenceLibrary.add(sentence);
                break;
            case REFUSAL:
                mRefusalSentenceLibrary.add(sentence);
                break;
            case THANKS:
                mThanksSentenceLibrary.add(sentence);
        }
    }

    public List<Sentence> getSentences(Sentence.SpeechType type) {
        switch (type) {
            case OPEN_QUESTION:
                return mOpenQuestionSentenceLibrary;
            case CLOSED_QUESTION:
                return mClosedQuestionSentenceLibrary;
            case ACKNOWLEDGEMENT:
                return mAcknowledgementSentenceLibrary;
            case GREETING:
                return mGreetingSentenceLibrary;
            case CLOSING:
                return mClosingSentenceLibrary;
            case REQUEST:
                return mRequestSentenceLibrary;
            case STATEMENT:
                return mStatementSentenceLibrary;
            case AGREEMENT:
                return mAgreementSentenceLibrary;
            case REFUSAL:
                return mRefusalSentenceLibrary;
            case THANKS:
                return mThanksSentenceLibrary;
            default:
                return null;
        }
    }
}
