package com.jorigg.android.chatbox.model;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public interface Conversation<E extends ConversationElementEnum> {

    String getTitle();
    void setTitle(String title);
    void setInProgress();

    /**
     * Adds the content to the dialog for the specified element
     * @param conversationElement
     * @param content
     * @return
     */
    boolean addToConversation(E conversationElement, String content);

    /**
     * Processes move made by the user to generate the next agent move
     * For agent responses need to choose one sentence from the ArrayList for the element chosen
     * at random.
     * Update isInPrgress for all temrminating moves
     * @param lastUserMove
     * @return details of the next agent move: a pair consisting of the sentence to be displayed
     * and the element it belongs to
     */
    Pair<E, Sentence> getNextAgentMove(E lastUserMove);

    /**
     * Processes move made by the agent to generate the next user move
     * Update isInProgress for all terminating moves
     * @param lastAgentMove
     * @return each element and associated options that the user could go to next
     */
    HashMap<E, ArrayList<Sentence>> getNextUserMoves(E lastAgentMove);

    /**
     * To check who initiates the conversation
     * @return the initiator of the conversation
     */
    UserPreferences.UserType getInitiator();

    /**
     * Implement this if the agent makes the initial move.
     * For agent responses need to choose one sentence from the ArrayList for the element chosen
     * at random.
     * @return the sentence associated with the first agent element
     */
    Sentence getInitialAgentResponse();

    /**
     *
     * @return the first element the agent owns
     */
    ConversationElementEnum getInitialAgentElement();

    /**
     * This is always needed
     * @return each of the initial user elements and their associated options
     */
    HashMap<E, ArrayList<Sentence>> getInitialUserMoves();

    /**
     *
     * @param element
     * @return the sentences associated with a particular element
     */
    ArrayList<Sentence> getElementOptions(E element);

    /**
     * Removes the sentence that has the specified String content from the dialogue. Needs to
     * remove the key as well if there are no sentences left for that element cf implementing a
     * check in hasEntryPerElement
     * @param sentence
     * @param element
     */
    void removeSentenceFromConversation(String sentence, E element);

    /**
     *
     * @return the whole dialogue
     */
    HashMap<E, ArrayList<Sentence>> getDialogue();

    /**
     *
     * @return true if in progress or false if conversation has tereminated
     */
    boolean isInProgress();

    /**
     *
     * @return true if dialogue has an entry for each of the associated conversation elements
     */
    boolean hasAnEntryPerElement();

    /**
     * checks whether the element is to be regarded as a negative (i.e. not good) move
     * @param element
     * @return true if is a negative move
     */
    boolean isNegativeMove(E element);
}
