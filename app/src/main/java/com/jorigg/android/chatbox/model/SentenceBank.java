package com.jorigg.android.chatbox.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SentenceBank {

    private ArrayList<Sentence> mOpenQuestionSentenceLibrary;
    private ArrayList<Sentence> mClosedQuestionSentenceLibrary;
    private ArrayList<Sentence> mAcknowledgementSentenceLibrary;
    private ArrayList<Sentence> mGreetingSentenceLibrary;
    private ArrayList<Sentence> mClosingSentenceLibrary;
    private ArrayList<Sentence> mRequestSentenceLibrary;
    private ArrayList<Sentence> mStatementSentenceLibrary;
    private ArrayList<Sentence> mAgreementSentenceLibrary;
    private ArrayList<Sentence> mRefusalSentenceLibrary;
    private ArrayList<Sentence> mThanksSentenceLibrary;
    public static SentenceBank sSentenceBank;
    private Context mContext;

    public static SentenceBank get(Context context) {
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
                if (!mOpenQuestionSentenceLibrary.contains(sentence)) {
                    mOpenQuestionSentenceLibrary.add(sentence);
                }
                break;
            case CLOSED_QUESTION:
                if (!mClosedQuestionSentenceLibrary.contains(sentence)) {
                    mClosedQuestionSentenceLibrary.add(sentence);
                }
                break;
            case ACKNOWLEDGEMENT:
                if (!mAcknowledgementSentenceLibrary.contains(sentence)) {
                    mAcknowledgementSentenceLibrary.add(sentence);
                }
                break;
            case GREETING:
                if (!mGreetingSentenceLibrary.contains(sentence)) {
                    mGreetingSentenceLibrary.add(sentence);
                }
                break;
            case CLOSING:
                if (!mClosingSentenceLibrary.contains(sentence)) {
                    mClosingSentenceLibrary.add(sentence);
                }
                break;
            case REQUEST:
                if (!mRequestSentenceLibrary.contains(sentence)) {
                    mRequestSentenceLibrary.add(sentence);
                }
                break;
            case STATEMENT:
                if (!mStatementSentenceLibrary.contains(sentence)) {
                    mStatementSentenceLibrary.add(sentence);
                }
                break;
            case AGREEMENT:
                if (!mAgreementSentenceLibrary.contains(sentence)) {
                    mAgreementSentenceLibrary.add(sentence);
                }
                break;
            case REFUSAL:
                if (!mRefusalSentenceLibrary.contains(sentence)) {
                    mRefusalSentenceLibrary.add(sentence);
                }
                break;
            case THANKS:
                if (!mThanksSentenceLibrary.contains(sentence)) {
                    mThanksSentenceLibrary.add(sentence);
                }
                break;
        }
    }

    public ArrayList<Sentence> getSentences(Sentence.SpeechType type) {
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

    public void removeSentence(String content, Sentence.SpeechType speechType) {
        ArrayList<Sentence> list = null;
        switch (speechType) {
            case OPEN_QUESTION:
                list = mOpenQuestionSentenceLibrary;
                break;
            case CLOSED_QUESTION:
                list = mClosedQuestionSentenceLibrary;
                break;
            case ACKNOWLEDGEMENT:
                list = mAcknowledgementSentenceLibrary;
                break;
            case GREETING:
                list = mGreetingSentenceLibrary;
                break;
            case CLOSING:
               list = mClosingSentenceLibrary;
                break;
            case REQUEST:
               list = mRequestSentenceLibrary;
                break;
            case STATEMENT:
                list = mStatementSentenceLibrary;
                break;
            case AGREEMENT:
                list = mAgreementSentenceLibrary;
                break;
            case REFUSAL:
                list = mRefusalSentenceLibrary;
                break;
            case THANKS:
                list = mThanksSentenceLibrary;
                break;
        }

        Iterator<Sentence> iterator = list.iterator();

        while (iterator.hasNext()) {
            Sentence sen = iterator.next();

            if (sen.getContent().equals(content)) {
                iterator.remove();
            }
        }
    }
}
