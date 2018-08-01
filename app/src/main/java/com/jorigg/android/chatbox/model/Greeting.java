package com.jorigg.android.chatbox.model;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class Greeting implements Conversation {

    public enum GreetingElements implements ConversationElementEnum {

        GREETING(UserPreferences.UserType.AGENT, Sentence.SpeechType.GREETING, "The computer " +
                "greets the child", false),
        RTN_GREETING(UserPreferences.UserType.CHILD, Sentence.SpeechType.GREETING, "The child " +
                "returns the greeting appropriately", false),
        OPEN_QUESTION(UserPreferences.UserType.AGENT, Sentence.SpeechType.OPEN_QUESTION, "The " +
                "computer asks an open question", false),
        APT_RESPONSE(UserPreferences.UserType.CHILD, Sentence.SpeechType.STATEMENT, "Appropriate " +
                "responses to the question", false),
        INAPT_RESPONSE(UserPreferences.UserType.CHILD, Sentence.SpeechType.STATEMENT,
                "Inappropriate reponses to the question", false),
        HINT1(UserPreferences.UserType.AGENT, null, "A hint about the appropriate response to the" +
                " open question will be presented as a thought bubble from the computer avatar", true),
        ACKNOWL_APT_REPSONSE(UserPreferences.UserType.AGENT, Sentence.SpeechType.ACKNOWLEDGEMENT,
                "The computer acknowledges the answer", false),
        FOLLOW_UP_QUESTION(UserPreferences.UserType.CHILD, Sentence.SpeechType.CLOSED_QUESTION,
                "The child asks a closed question as a follow up", false),
        AGENT_APT_RESPONSE(UserPreferences.UserType.AGENT, Sentence.SpeechType.STATEMENT, "The " +
                "computer provides an appropriate response to the follow up question", false),
        APT_ACKNOWL(UserPreferences.UserType.CHILD, Sentence.SpeechType.ACKNOWLEDGEMENT,
                "Appropriate responses for the child to make to acknowledge the response", false),
        INAPT_ACKNOWL(UserPreferences.UserType.CHILD, Sentence.SpeechType.ACKNOWLEDGEMENT,
                "Inappropriate repsonses for the child to make to acknowledge the response", false),
        HINT2(UserPreferences.UserType.AGENT, Sentence.SpeechType.STATEMENT, "A hint about " +
                "appropriate ways to acknowledge the response. Is presented as a tough bubble",
                true),
        GOODBYE(UserPreferences.UserType.AGENT, Sentence.SpeechType.CLOSING, "Computer terminates" +
                " the conversation politely", false),
        RTN_GOODBYE(UserPreferences.UserType.CHILD, Sentence.SpeechType.CLOSING, "Child returns " +
                "the goodbye", false);

        private final UserPreferences.UserType mSpeaker;
        private final Sentence.SpeechType mSpeechType;
        private final String mElementDescription;
        private final boolean mIsThought;

        GreetingElements (final UserPreferences.UserType speaker, final Sentence.SpeechType
                speechType, String elementDescription, boolean isThought) {
            mSpeaker = speaker;
            mSpeechType = speechType;
            mElementDescription = elementDescription;
            mIsThought = isThought;
        }

        @Override
        public UserPreferences.UserType getSpeaker() {
            return null;
        }

        @Override
        public Sentence.SpeechType getSpeechType() {
            return null;
        }

        @Override
        public String getElementDescription() {
            return null;
        }

        @Override
        public boolean isThought() {
            return false;
        }
    }

    private HashMap<GreetingElements, ArrayList<Sentence>> mDialogue;
    private String mTitle; //user generated convo name
    private UserPreferences.UserType mInitiator;
    private boolean mInProgress;

    public Greeting(String title) {
        mDialogue = new HashMap<>();
        mTitle = title;
        mInitiator = UserPreferences.UserType.AGENT;
        mInProgress = true;
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
    public boolean addToConversation(ConversationElementEnum conversationElement, String content) {
        Sentence.SpeechType speechType = conversationElement.getSpeechType();

        if (mDialogue.containsKey(conversationElement)) {
            ArrayList<Sentence> thisElement = mDialogue.get(conversationElement);

            for (Sentence sentence : thisElement) {
                if (sentence.getContent().equals(content)) {
                    return false;
                }
            }

            thisElement.add(new Sentence(content, speechType));
            mDialogue.put((GreetingElements) conversationElement, thisElement);

        } else {
            ArrayList<Sentence> newElement = new ArrayList<>();
            newElement.add(new Sentence(content, speechType));
            mDialogue.put((GreetingElements) conversationElement, newElement);
        }
        return true;
    }

    @Override
    public Pair getNextAgentMove(ConversationElementEnum lastUserMove) {
        return null; //todo
    }

    @Override
    public HashMap getNextUserMoves(ConversationElementEnum lastAgentMove) {
        return null; //todo
    }

    @Override
    public UserPreferences.UserType getInitiator() {
        return mInitiator;
    }

    @Override
    public Sentence getInitialAgentResponse() {
        ArrayList<Sentence> options = mDialogue.get(GreetingElements.GREETING);
        Sentence move = options.get(new Random().nextInt(options.size()));
        return move;
    }

    @Override
    public ConversationElementEnum getInitialAgentElement() {
        return GreetingElements.GREETING;
    }

    @Override
    public HashMap getInitialUserMoves() {
        return null; //not needed in this implementation
    }

    @Override
    public ArrayList<Sentence> getElementOptions(ConversationElementEnum element) {
        return mDialogue.get(element);
    }

    @Override
    public void removeSentenceFromConversation(String sentence, ConversationElementEnum element) {
        ArrayList<Sentence> options = mDialogue.get(element);
        Iterator<Sentence> iterator = options.iterator();

        while (iterator.hasNext()) {
            Sentence sent = iterator.next();
            if(sent.getContent().equals(sentence)) {
                iterator.remove();
            }
        }

        //if no entries left then remove the key form hashMap so that check complete will
        // evaluate correctly
        if (options.isEmpty()) {
            mDialogue.remove(element);
        } else {
            mDialogue.put((GreetingElements) element, options);

        }
    }

    @Override
    public HashMap getDialogue() {
        return mDialogue;
    }

    @Override
    public boolean isInProgress() {
        return mInProgress;
    }

    @Override
    public boolean hasAnEntryPerElement() {
        return GreetingElements.values().length == mDialogue.size();
    }
}
