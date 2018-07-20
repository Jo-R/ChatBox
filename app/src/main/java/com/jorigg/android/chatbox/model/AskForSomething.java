package com.jorigg.android.chatbox.model;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Random;

public class AskForSomething implements Conversation {

    public enum AskForSomethingElements implements ConversationElementEnum {

        GREETING(User.UserType.CHILD, Sentence.SpeechType.GREETING, "An appropriate greeting"),
        ALT_GREETING(User.UserType.CHILD, Sentence.SpeechType.GREETING, "A less appropriate " +
                "greeting"),
        RTN_GREETING(User.UserType.AGENT, Sentence.SpeechType.GREETING, "An appropriate greeting"),
        MAKE_REQUEST(User.UserType.CHILD, Sentence.SpeechType.REQUEST, "An appropriately phrased " +
                "request"),
        ALT_MAKE_REQUEST(User.UserType.CHILD, Sentence.SpeechType.REQUEST, "An inappropriately " +
                "phrased request"),
        AGREE_REQUEST(User.UserType.AGENT, Sentence.SpeechType.AGREEMENT, "Agree to fulfill the " +
                "request"),
        REQ_CLARIFY(User.UserType.AGENT, Sentence.SpeechType.REQUEST, "Request clarification of " +
                "the request"),
        PROVIDE_CLARIFY(User.UserType.CHILD, Sentence.SpeechType.REQUEST, "An appropriately " +
                "phrased clarification"),
        ALT_PROVIDE_CLARIFY(User.UserType.CHILD, Sentence.SpeechType.REQUEST, "An inapproriately " +
                "phrased clarification"),
        REFUSE_REQ(User.UserType.AGENT, Sentence.SpeechType.REFUSAL, "Refuse to fulfil the " +
                "request"),
        ACKNOWL_REFUSAL(User.UserType.CHILD, Sentence.SpeechType.ACKNOWLEDGEMENT, "Acknwoledge " +
                "that the request has been refused"),
        THANK(User.UserType.CHILD, Sentence.SpeechType.THANKS, "Thank you for fulfilling the " +
                "request"),
        ACKNOWL_THANK(User.UserType.AGENT, Sentence.SpeechType.ACKNOWLEDGEMENT, "Acknowledge the " +
                "thanks");


        private final User.UserType mSpeaker;
        private final Sentence.SpeechType mSpeechType;
        private final String mElementDescription;

        AskForSomethingElements(final User.UserType speaker, final Sentence.SpeechType
                speechType, String elementDescription) {
            mSpeaker = speaker;
            mSpeechType = speechType;
            mElementDescription = elementDescription;
        }

        @Override
        public User.UserType getSpeaker() {
            return mSpeaker;
        }

        @Override
        public Sentence.SpeechType getSpeechType() {
            return mSpeechType;
        }

        @Override
        public String getElementDescription() {
            return mElementDescription;
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
    public ArrayList<Sentence> getElementOptions(ConversationElementEnum element) {
        return mDialogue.get(element);
    }

    @Override
    public void removeSentenceFromConversation(String sentence, ConversationElementEnum element) {
        ArrayList<Sentence> options = mDialogue.get(element);
        for (Sentence sent : options) {
            if (sent.getContent().equals(sentence)) {
                options.remove(sent);
            }
        }
        mDialogue.put((AskForSomethingElements) element, options);
    }

    @Override
    // Sentence?
    public void addToConversation(ConversationElementEnum conversationElement, String content) {
        //add the sentence to the arrylist associated with the key
        //get the list to a temp var and add and reput so don't remove existing
        Sentence.SpeechType speechType = conversationElement.getSpeechType();
        if (mDialogue.containsKey(conversationElement)) {
            ArrayList<Sentence> thisElement = mDialogue.get(conversationElement);
            thisElement.add(new Sentence(content, speechType));
            mDialogue.put((AskForSomethingElements)conversationElement, thisElement);
        } else {
            ArrayList<Sentence> newElement = new ArrayList<>();
            newElement.add(new Sentence(content, speechType));
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

        //TODO if nextElement == null move was ACKNOWL_REFUSAL, options also empty so app is
        // crashing in thie scenario
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
