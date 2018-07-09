package com.jorigg.android.chatbox.model;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Random;

public class AskForSomething implements Conversation {

    public enum AskForSomethingElements implements ConversationElementEnum {

        GREETING(User.UserType.CHILD),
        ALT_GREETING(User.UserType.CHILD),
        RTN_GREETING(User.UserType.AGENT),
        MAKE_REQUEST(User.UserType.CHILD),
        ALT_MAKE_REQUEST(User.UserType.CHILD),
        AGREE_REQUEST(User.UserType.AGENT),
        REQ_CLARIFY(User.UserType.AGENT),
        PROVIDE_CLARIFY(User.UserType.CHILD), //or REQUEST??
        ALT_PROVIDE_CLARIFY(User.UserType.CHILD),
        REFUSE_REQ(User.UserType.AGENT),
        ACKNOWL_REFUSAL(User.UserType.CHILD),
        THANK(User.UserType.CHILD),
        ACKNOWL_THANK(User.UserType.AGENT);


        private final User.UserType mSpeaker;

        AskForSomethingElements(final User.UserType speaker) {
            mSpeaker = speaker;
        }

        @Override
        public User.UserType getSpeaker() {
            return mSpeaker;
        }



    }

    private EnumMap<AskForSomethingElements, ArrayList<Sentence>> mDialogue;
    private String mTitle; //user generated convo name
    private User.UserType mInitiator;
    private ArrayList<AskForSomethingElements> mInitialUserElements;

    public AskForSomething(String title) {
        mDialogue = new EnumMap<>(AskForSomethingElements.class);
        mTitle = title;
        mInitiator = User.UserType.CHILD;
        mInitialUserElements = new ArrayList<>();
        mInitialUserElements.add(AskForSomethingElements.GREETING);
        mInitialUserElements.add(AskForSomethingElements.ALT_GREETING);
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
    public HashMap<AskForSomethingElements, ArrayList<Sentence>> getInitialUserMoves() {
        HashMap<AskForSomethingElements, ArrayList<Sentence>> initMoves = new HashMap<>();
        initMoves.put(AskForSomethingElements.GREETING, mDialogue.get(AskForSomethingElements
                .GREETING));
        initMoves.put(AskForSomethingElements.ALT_GREETING, mDialogue.get(AskForSomethingElements
                .ALT_GREETING));

        return initMoves;
    }

    @Override
    public Sentence getInitialAgentResponse() {
        return null; //not needed for this implementation
    }

    @Override
    public ConversationElementEnum getInitialAgentElement() {
        return AskForSomethingElements.RTN_GREETING;
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

        //TODO if nextElement == null conversation over ie ACKNOWL_REFUSAL
        //will currently return null for nextElement so this is how we know convo has ended

        //choose one from the AList at random - so only ever gives one reponse but there is a choice
        Sentence move = options.get(new Random().nextInt(options.size()));
        return new Pair(nextElement, move);


    }

    @Override
    public HashMap<ConversationElementEnum, ArrayList<Sentence>> getNextUserMoves
            (ConversationElementEnum lastAgentMove) {
        //map because there could be >1 entry for next moves
        HashMap<ConversationElementEnum, ArrayList<Sentence>> nextMoves = new HashMap<>();

        if (lastAgentMove == AskForSomethingElements.RTN_GREETING) {
            nextMoves.put(AskForSomethingElements.MAKE_REQUEST, mDialogue.get
                    (AskForSomethingElements.MAKE_REQUEST));
            nextMoves.put(AskForSomethingElements.ALT_MAKE_REQUEST, mDialogue.get
                    (AskForSomethingElements.ALT_MAKE_REQUEST));
        } else if (lastAgentMove == AskForSomethingElements.AGREE_REQUEST) {
            nextMoves.put(AskForSomethingElements.THANK, mDialogue.get(AskForSomethingElements
                    .THANK));
        } else if (lastAgentMove == AskForSomethingElements.REQ_CLARIFY) {
            nextMoves.put(AskForSomethingElements.PROVIDE_CLARIFY, mDialogue.get
                    (AskForSomethingElements.PROVIDE_CLARIFY));
            nextMoves.put(AskForSomethingElements.ALT_PROVIDE_CLARIFY, mDialogue.get
                    (AskForSomethingElements.ALT_PROVIDE_CLARIFY));
        } else if (lastAgentMove == AskForSomethingElements.REFUSE_REQ) {
            nextMoves.put(AskForSomethingElements.ACKNOWL_REFUSAL, mDialogue.get
                    (AskForSomethingElements.ACKNOWL_REFUSAL));
        }

        //TODO if hashmap is empty = end of conversation

        return nextMoves;
    }
}
