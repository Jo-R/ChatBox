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
        HINT1(UserPreferences.UserType.AGENT, Sentence.SpeechType.STATEMENT, "A hint about the appropriate response to the" +
                " open question will be presented as a thought bubble from the computer avatar", true),
        INAPT_RESPONSE2(UserPreferences.UserType.CHILD, Sentence.SpeechType.STATEMENT,
                "Inappropriate reponses to the question to be displayed after hint", false),
        TERM_CONVO1(UserPreferences.UserType.AGENT, Sentence.SpeechType.CLOSING, "Computer " +
                "terminates conversation after a second inapt response or acknowledgement", false),
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
        INAPT_ACKNOWL2(UserPreferences.UserType.CHILD, Sentence.SpeechType.ACKNOWLEDGEMENT,
                "Inappropriate repsonses for the child to make to acknowledge the response, to be" +
                        " displayed after hint", false),
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

        @Override
        public boolean isThought() {
            return mIsThought;
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
    public void setInProgress() {
        mInProgress = true;
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
        ArrayList<Sentence> options = new ArrayList<>();
        GreetingElements nextElement = null;

        if (lastUserMove == GreetingElements.RTN_GREETING) {
            options = mDialogue.get(GreetingElements.OPEN_QUESTION);
            nextElement = GreetingElements.OPEN_QUESTION;
        } else if (lastUserMove == GreetingElements.APT_RESPONSE) {
            options = mDialogue.get(GreetingElements.ACKNOWL_APT_REPSONSE);
            nextElement = GreetingElements.ACKNOWL_APT_REPSONSE;
        } else if (lastUserMove == GreetingElements.INAPT_RESPONSE) {
            options = mDialogue.get(GreetingElements.HINT1);
            nextElement = GreetingElements.HINT1;
        } else if (lastUserMove == GreetingElements.INAPT_RESPONSE2) {
            options = mDialogue.get(GreetingElements.TERM_CONVO1);
            nextElement = GreetingElements.TERM_CONVO1;
            mInProgress = false;
        } else if (lastUserMove == GreetingElements.FOLLOW_UP_QUESTION) {
            options = mDialogue.get(GreetingElements.AGENT_APT_RESPONSE);
            nextElement = GreetingElements.AGENT_APT_RESPONSE;
        } else if (lastUserMove == GreetingElements.APT_ACKNOWL) {
            options = mDialogue.get(GreetingElements.GOODBYE);
            nextElement = GreetingElements.GOODBYE;
            mInProgress = false;
        } else if (lastUserMove == GreetingElements.INAPT_ACKNOWL) {
            options = mDialogue.get(GreetingElements.HINT2);
            nextElement = GreetingElements.HINT2;
        } else if (lastUserMove == GreetingElements.INAPT_ACKNOWL2) {
            options = mDialogue.get(GreetingElements.TERM_CONVO1);
            nextElement = GreetingElements.TERM_CONVO1;
            mInProgress = false;
        }

        //choose one from the AList at random - so only ever gives one reponse but there is a choice
        Sentence move = options.get(new Random().nextInt(options.size()));
        return new Pair(nextElement, move);
    }

    @Override
    public HashMap getNextUserMoves(ConversationElementEnum lastAgentMove) {
        //map because there could be >1 entry for next moves
        HashMap<ConversationElementEnum, ArrayList<Sentence>> nextMoves = new HashMap<>();

        if (lastAgentMove == GreetingElements.GREETING) {
            nextMoves.put(GreetingElements.RTN_GREETING, mDialogue.get
                    (GreetingElements.RTN_GREETING));
        } else if (lastAgentMove == GreetingElements.OPEN_QUESTION) {
            nextMoves.put(GreetingElements.APT_RESPONSE, mDialogue.get(GreetingElements
                    .APT_RESPONSE));
            nextMoves.put(GreetingElements.INAPT_RESPONSE, mDialogue.get(GreetingElements
                    .INAPT_RESPONSE));
        } else if (lastAgentMove == GreetingElements.HINT1) {
            nextMoves.put(GreetingElements.APT_RESPONSE, mDialogue.get(GreetingElements
                    .APT_RESPONSE));
            nextMoves.put(GreetingElements.INAPT_RESPONSE2, mDialogue.get(GreetingElements
                    .INAPT_RESPONSE2));
        } else if (lastAgentMove == GreetingElements.ACKNOWL_APT_REPSONSE) {
            nextMoves.put(GreetingElements.FOLLOW_UP_QUESTION, mDialogue.get(GreetingElements
                    .FOLLOW_UP_QUESTION));
        } else if (lastAgentMove == GreetingElements.AGENT_APT_RESPONSE) {
            nextMoves.put(GreetingElements.APT_ACKNOWL, mDialogue.get(GreetingElements
                    .APT_ACKNOWL));
            nextMoves.put(GreetingElements.INAPT_ACKNOWL, mDialogue.get(GreetingElements
                    .INAPT_ACKNOWL));
        } else if (lastAgentMove == GreetingElements.HINT2) {
            nextMoves.put(GreetingElements.APT_ACKNOWL, mDialogue.get(GreetingElements
                    .APT_ACKNOWL));
            nextMoves.put(GreetingElements.INAPT_ACKNOWL2, mDialogue.get(GreetingElements
                    .INAPT_ACKNOWL2));
        } else if (lastAgentMove == GreetingElements.GOODBYE) {
            nextMoves.put(GreetingElements.RTN_GOODBYE, mDialogue.get(GreetingElements
                    .RTN_GOODBYE));
            mInProgress = false;
        }

        return nextMoves;
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
        HashMap<GreetingElements, ArrayList<Sentence>> moves = new HashMap<>();
        moves.put(GreetingElements.RTN_GREETING, mDialogue.get(GreetingElements.RTN_GREETING));
        return moves;
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

    @Override
    public boolean isNegativeMove(ConversationElementEnum element) {
        if (element == GreetingElements.INAPT_ACKNOWL || element == GreetingElements
                .INAPT_ACKNOWL2 || element == GreetingElements.INAPT_RESPONSE || element ==
                GreetingElements.INAPT_RESPONSE2) {
            return true;
        }
        return false;
    }
}
