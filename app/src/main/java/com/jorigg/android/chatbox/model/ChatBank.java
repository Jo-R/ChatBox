package com.jorigg.android.chatbox.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatBank {

    private List<Conversation> mConversationLibrary;
    private Context mContext;
    private static ChatBank sChatBank;

    public static ChatBank get(Context context) {
        if (sChatBank == null) {
            sChatBank = new ChatBank(context);
        }
        return sChatBank;
    }

    private ChatBank(Context context) {
        mContext = context.getApplicationContext();
        mConversationLibrary = new ArrayList<>();
        addConversation();
    }


    public void addConversation() { //TODO HARD CODED FOR NOW
       //add a new conversation - create it and add to the library
        Conversation testConvo = new AskForSomething("test");
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.GREETING,
                new Sentence("Hi", Sentence.SpeechType.GREETING));
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.ALT_GREETING,
               new Sentence("Hi there dude", Sentence.SpeechType.GREETING));
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.RTN_GREETING, new
                Sentence("Yo", Sentence.SpeechType.GREETING));
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.RTN_GREETING, new
                Sentence("Big up", Sentence.SpeechType.GREETING));

        mConversationLibrary.add(testConvo);

    }

    public Conversation getConversation(String title) {
        for ( Conversation conversation : mConversationLibrary) {
            if (conversation.getTitle().equals(title)) {
                return conversation;
            }
        }
        return null;
    }



}
