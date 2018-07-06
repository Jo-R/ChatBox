package com.jorigg.android.chatbox.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class AskForSomething implements Conversation {

    public enum AskForSomethingElements implements ConversationElementEnum {

        GREETING(User.UserType.CHILD, Sentence.SpeechType.GREETING),
        ALT_GREETING(User.UserType.CHILD, Sentence.SpeechType.GREETING),
        RTN_GREETING(User.UserType.AGENT, Sentence.SpeechType.GREETING),
        MAKE_REQUEST(User.UserType.CHILD, Sentence.SpeechType.REQUEST),
        ALT_MAKE_REQUEST(User.UserType.CHILD, Sentence.SpeechType.REQUEST),
        AGREE_REQUEST(User.UserType.AGENT, Sentence.SpeechType.AGREEMENT),
        REQ_CLARIFY(User.UserType.AGENT, Sentence.SpeechType.REQUEST),
        PROVIDE_CLARIFY(User.UserType.CHILD, Sentence.SpeechType.STATEMENT), //or REQUEST??
        REFUSE_REQ(User.UserType.AGENT, Sentence.SpeechType.REFUSAL),
        ACKNOWL_REFUSAL(User.UserType.CHILD, Sentence.SpeechType.ACKNOWLEDGEMENT),
        THANK(User.UserType.CHILD, Sentence.SpeechType.THANKS),
        ACKNOWL_THANK(User.UserType.AGENT, Sentence.SpeechType.ACKNOWLEDGEMENT);


        private final User.UserType mSpeaker;
        private final Sentence.SpeechType mSpeechType;

        AskForSomethingElements(final User.UserType speaker, final Sentence.SpeechType speechType) {
            mSpeaker = speaker;
            mSpeechType = speechType;
        }

        @Override
        public User.UserType getSpeaker() {
            return mSpeaker;
        }

        @Override
        public Sentence.SpeechType getSpeechType(String elementName) {
            return mSpeechType;
        }


    }

    private EnumMap<AskForSomethingElements, ArrayList<Sentence>> mDialogue;
    private String mTitle; //user generated convo name
    private User.UserType mInitiator; //TODO is this needed cf get that as part of the element returned in mInitialElements?
    private ArrayList<AskForSomethingElements> mInitialElements;

    public AskForSomething(String title) {
        mDialogue = new EnumMap<>(AskForSomethingElements.class);
        mTitle = title;
        mInitiator = User.UserType.CHILD;
        mInitialElements = new ArrayList<>();
        mInitialElements.add(AskForSomethingElements.GREETING);
        mInitialElements.add(AskForSomethingElements.ALT_GREETING);
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public User.UserType getInitiator() {
        return mInitiator;
    }


    @Override
    public void addToConversation(ConversationElementEnum conversationElement, Sentence sentence) {
        //add the sentence to the arrylist associated with the key
        //get the list to a temp var and add and reput so don't remove existing
        if (mDialogue.containsKey(conversationElement)) {
            ArrayList<Sentence> thisElement = mDialogue.get(conversationElement);
            thisElement.add(sentence);
            mDialogue.put((AskForSomethingElements)conversationElement, thisElement);
        } else {
            ArrayList<Sentence> newElement = new ArrayList<>();
            newElement.add(sentence);
            mDialogue.put((AskForSomethingElements) conversationElement, newElement);
        }
    }


    @Override
    //this works for this conversation as user = initiator
    public ArrayList<Sentence> getInitialUserResponses() {
        ArrayList<Sentence> initialUserResponses = new ArrayList<>();
        for (AskForSomethingElements elem : mInitialElements) {
            for (Sentence sen : mDialogue.get(elem)) {
                initialUserResponses.add(sen);
            }
        }
        return initialUserResponses;
    }

    @Override
    public ArrayList<AskForSomethingElements> getInitialElements() {
        return mInitialElements;
    }

    @Override
    public ArrayList<Sentence> getNextMove(ConversationElementEnum conversationElementJustUsed) {
        return null;
        //takes most recent move made by user and works out next conversationElements to display
        //this might need to return both agent and user next responses????
    }


}
