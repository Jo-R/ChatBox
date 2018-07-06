package com.jorigg.android.chatbox.model;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

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
        ALT_PROVIDE_CLARIFY(User.UserType.CHILD, Sentence.SpeechType.STATEMENT),
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
    public Pair<ConversationElementEnum, Sentence> getNextAgentMove(ConversationElementEnum
                                                           lastUserMove) {
        ArrayList<Sentence> options = new ArrayList<>();
        AskForSomethingElements nextElement = null;

        if (lastUserMove == AskForSomethingElements.GREETING ||
                lastUserMove == AskForSomethingElements.ALT_GREETING) {
            options = mDialogue.get(AskForSomethingElements.RTN_GREETING);
            nextElement = AskForSomethingElements.RTN_GREETING;
        } else if (lastUserMove == AskForSomethingElements.MAKE_REQUEST) {
            options = mDialogue.get(AskForSomethingElements.AGREE_REQUEST);
            nextElement = AskForSomethingElements.AGREE_REQUEST;
        } else if (lastUserMove == AskForSomethingElements.ALT_MAKE_REQUEST) {
            options = mDialogue.get(AskForSomethingElements.REQ_CLARIFY);
            nextElement = AskForSomethingElements.REQ_CLARIFY;
        } else if (lastUserMove == AskForSomethingElements.PROVIDE_CLARIFY) {
            options = mDialogue.get(AskForSomethingElements.AGREE_REQUEST);
            nextElement = AskForSomethingElements.AGREE_REQUEST;
        } else if (lastUserMove == AskForSomethingElements.ALT_PROVIDE_CLARIFY) {
            options = mDialogue.get(AskForSomethingElements.REFUSE_REQ);
            nextElement = AskForSomethingElements.REFUSE_REQ;
        } else if (lastUserMove == AskForSomethingElements.THANK) {
            options = mDialogue.get(AskForSomethingElements.ACKNOWL_THANK);
            nextElement = AskForSomethingElements.ACKNOWL_THANK;
        }

        //TODO if nextElement == null throw exception?

        //choose one from the AList at random - so only ever gives one reponse but there is a choice
        Sentence move = options.get(new Random().nextInt(options.size()));
        return new Pair(nextElement, move);


    }

    @Override
    public Map<ConversationElementEnum, Sentence> getNextUserMoves(ConversationElementEnum
                                                                         lastAgentMove) {
        return null;
        //there could be more than one for some but that's fine - hard code this
    }
}
