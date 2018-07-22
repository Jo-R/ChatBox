package com.jorigg.android.chatbox.model;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatBank {

    private ArrayList<Conversation> mConversationLibrary;
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

    public ArrayList<String> getChatTitles() {
        ArrayList<String> titles = new ArrayList<>();
        for (Conversation convo : mConversationLibrary) {
            titles.add(convo.getTitle());
        }
        return titles;
    }

    public void addNewConversation(String title, String type) {
        Conversation newConvo = null;
        if (type.equals("Ask for Something")) {
            newConvo = new AskForSomething(title);
        }
        mConversationLibrary.add(newConvo);
    }

    public ArrayList<Conversation> getConversationLibrary() {
        return mConversationLibrary;
    }

    public void addConversation() { //TODO HARD CODED FOR NOW
       //add a new conversation - create it and add to the library
        Conversation testConvo = new AskForSomething("test");
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.GREETING,"Hi");
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.ALT_GREETING,"Hi there dude");
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.RTN_GREETING, "Yo");
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.RTN_GREETING, "Big up");
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.MAKE_REQUEST, "Please " +
                "can I have a thing?");
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.ALT_MAKE_REQUEST,
                "Thing");
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.AGREE_REQUEST, "Of course");
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.AGREE_REQUEST, "Yes," +
                " here you are");
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.REQ_CLARIFY, "Sorry, " +
                "can you just say that again?");
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.PROVIDE_CLARIFY, "Of course, " +
                "please can I have a thing?");
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.ALT_PROVIDE_CLARIFY,
                "Thing");
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.REFUSE_REQ, "Sorry, no");
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.REFUSE_REQ, "No, not now");
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.ACKNOWL_REFUSAL, "OK, no problem");
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.ACKNOWL_REFUSAL, "Alright then");
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.THANK, "Thanks very " +
                "much");
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.THANK, "Ta");
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.ACKNOWL_THANK, "No problem");

        mConversationLibrary.add(testConvo);

        Conversation dummy = new AskForSomething("empty convo");
        mConversationLibrary.add(dummy);

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
