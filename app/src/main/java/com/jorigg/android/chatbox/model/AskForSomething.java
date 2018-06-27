package com.jorigg.android.chatbox.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class AskForSomething implements Conversation {

    public enum AskForSomethingElements implements ConversationElementEnum {

        GREETING(User.UserType.CHILD),
        ALT_GREETING(User.UserType.CHILD),
        RTN_GREETING(User.UserType.AGENT),
        MAKE_REQUEST(User.UserType.CHILD),
        ALT_MAKE_REQUEST(User.UserType.CHILD),
        AGREE_REQUEST(User.UserType.AGENT),
        REQ_CLARIFY(User.UserType.AGENT),
        PROVIDE_CLARIFY(User.UserType.CHILD),
        REFUSE_REQ(User.UserType.AGENT),
        ACKNOWL_REFUSAL(User.UserType.CHILD),
        THANK(User.UserType.CHILD),
        ACKNOWL_THANK(User.UserType.AGENT);


        private final User.UserType mSpeaker;

        private AskForSomethingElements(final User.UserType speaker) {
            mSpeaker = speaker;
        }

        @Override
        public User.UserType getSpeaker() {
            return mSpeaker;
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
