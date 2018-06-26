package com.jorigg.android.chatbox.model;

import java.util.HashMap;
import java.util.List;

public class AskForSomething implements Conversation {

    public enum AskForSomethingElements implements ConversationElementEnum {

        GREETING("user"),
        ALT_GREETING("user"),
        RTN_GREETING("agent"),
        MAKE_REQUEST("user"),
        ALT_MAKE_REQUEST("user"),
        AGREE_REQUEST("agent"),
        REQ_CLARIFY("agent"),
        PROVIDE_CLARIFY("user"),
        REFUSE_REQ("agent"),
        ACKNOWL_REFUSAL("user"),
        THANK("user"),
        ACKNOWL_THANK("agent");


        private final String mSpeaker;

        private AskForSomethingElements(final String speaker) {
            mSpeaker = speaker;
        }

        @Override
        public String getSpeaker() {
            return mSpeaker;
        }

    }

    private HashMap<ConversationElementEnum, List<Sentence>> mDialogue;
    private String mTitle; //user generated convo name

    public AskForSomething(String title) {
        mDialogue = new HashMap<>();
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public HashMap<ConversationElementEnum, List<Sentence>> getConversation() {
        return mDialogue;
    }

    @Override
    public void addToConversation(ConversationElementEnum conversationElement, Sentence sentence, Sentence
            .SpeechType speechType) {
        //add the sentence to the arrylist associated with the key
    }

    @Override
    public List<Sentence> getNextMove(ConversationElementEnum conversationElementJustUsed) {
        return null;
        //takes most recent move made by user and works out next conversationElements to display
    }
}
