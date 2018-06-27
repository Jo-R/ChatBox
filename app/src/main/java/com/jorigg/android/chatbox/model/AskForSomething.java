package com.jorigg.android.chatbox.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

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

        private AskForSomethingElements(final User.UserType speaker, final Sentence.SpeechType speechType) {
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

    private Map<AskForSomethingElements, ArrayList<Sentence>> mDialogue;
    private String mTitle; //user generated convo name

    public AskForSomething(String title) {
        mDialogue = new EnumMap<>(AskForSomethingElements.class);
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public Map<AskForSomethingElements, ArrayList<Sentence>> getConversationDetails() {
        return mDialogue;
    }

    @Override
    public void addToConversation(Enum conversationElement, Sentence sentence, Sentence
            .SpeechType speechType) {
        //add the sentence to the arrylist associated with the key
    }

    @Override
    public List<Sentence> getNextMove(Enum conversationElementJustUsed) {
        return null;
        //takes most recent move made by user and works out next conversationElements to display
    }

    @Override
    public User.UserType getInitiator() {
        return null;
    }
}
