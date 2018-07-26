package com.jorigg.android.chatbox.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SentenceBank {

    private ArrayList<Sentence> mSentenceLibrary;
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
        mSentenceLibrary = new ArrayList<>();
    }

    public ArrayList<Sentence> getSentenceLibrary() {
        return mSentenceLibrary;
    }

    public void addSentence(Sentence sentence) {
        mSentenceLibrary.add(sentence);
    }

    public ArrayList<Sentence> getSentences(Sentence.SpeechType type) {
       ArrayList<Sentence> sentenceThisType = new ArrayList<>();

       for (Sentence sentence : mSentenceLibrary) {
           if (sentence.getSpeechType().equals(type)) {
               sentenceThisType.add(sentence);
           }
       }

       return sentenceThisType;
    }

    public void removeSentence(String content, Sentence.SpeechType speechType) {
        Iterator<Sentence> iterator = mSentenceLibrary.iterator();

        while (iterator.hasNext()) {
            Sentence sen = iterator.next();

            if (sen.getContent().equals(content)) {
                iterator.remove();
            }
        }
    }
}
