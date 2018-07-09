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
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.MAKE_REQUEST, new
                Sentence("Please can I have a thing?", Sentence.SpeechType.REQUEST));
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.ALT_MAKE_REQUEST, new
                Sentence("Thing", Sentence.SpeechType.REQUEST));
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.AGREE_REQUEST, new
                Sentence("Of course", Sentence.SpeechType.AGREEMENT));
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.AGREE_REQUEST, new
                Sentence("Yes, here you are", Sentence.SpeechType.AGREEMENT));
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.REQ_CLARIFY, new
                Sentence("Sorry, can you just say that again?", Sentence.SpeechType.REQUEST));
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.PROVIDE_CLARIFY, new
                Sentence("Of course, please can I have a thing?", Sentence.SpeechType.REQUEST));
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.ALT_PROVIDE_CLARIFY, new
                Sentence("Thing", Sentence.SpeechType.REQUEST));
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.REFUSE_REQ, new
                Sentence("Sorry, no", Sentence.SpeechType.REFUSAL));
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.REFUSE_REQ, new
                Sentence("No, not now", Sentence.SpeechType.REFUSAL));
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.ACKNOWL_REFUSAL, new
                Sentence("OK, no problem", Sentence.SpeechType.ACKNOWLEDGEMENT));
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.ACKNOWL_REFUSAL, new
                Sentence("Alright then", Sentence.SpeechType.ACKNOWLEDGEMENT));
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.THANK, new
                Sentence("Thanks very much", Sentence.SpeechType.THANKS));
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.THANK, new
                Sentence("Ta", Sentence.SpeechType.THANKS));
        testConvo.addToConversation(AskForSomething.AskForSomethingElements.ACKNOWL_THANK, new
                Sentence("No problem", Sentence.SpeechType.ACKNOWLEDGEMENT));

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
