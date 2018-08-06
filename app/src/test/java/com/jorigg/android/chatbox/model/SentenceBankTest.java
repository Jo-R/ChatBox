package com.jorigg.android.chatbox.model;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SentenceBankTest {

    private Sentence mSentence;
    private SentenceBank mSubject;

    @Mock
    Context mMockContext;


    @Before
    public void setUp() throws Exception {
        mSentence = mock(Sentence.class);
        when(mSentence.getContent()).thenReturn("test");
        when(mSentence.getSpeechType()).thenReturn(Sentence.SpeechType.STATEMENT);
        mSubject = SentenceBank.get(mMockContext);
        mSubject.addSentence(mSentence);
    }

    @Test
    public void getSentenceLibrary() {
        ArrayList<Sentence> returnedList = mSubject.getSentenceLibrary();
        assertThat(returnedList.size(), is(1));
    }

    @Test
    public void addSentence() {
        Sentence newSentence = mock(Sentence.class);
        when(newSentence.getContent()).thenReturn("anotherTest");
        when(newSentence.getSpeechType()).thenReturn(Sentence.SpeechType.STATEMENT);
        mSubject.addSentence(newSentence);
        assertTrue(mSubject.getSentenceLibrary().contains(newSentence));
    }

    @Test
    public void addSentenceDuplicate() {
        Sentence newSentence = mock(Sentence.class);
        when(newSentence.getContent()).thenReturn("test");
        mSubject.addSentence(newSentence);
        assertFalse(mSubject.getSentenceLibrary().contains(newSentence));
    }

    @Test
    public void getSentencesForSpeechType() {
        Sentence newSentence = mock(Sentence.class);
        when(newSentence.getContent()).thenReturn("this test");
        when(newSentence.getSpeechType()).thenReturn(Sentence.SpeechType.ACKNOWLEDGEMENT);
        mSubject.addSentence(newSentence);
        ArrayList<Sentence> returnedList = mSubject.getSentencesForSpeechType(Sentence.SpeechType.ACKNOWLEDGEMENT);
        assertThat(returnedList.size(), is(1));
    }

    @Test
    public void removeSentence() {
        int sizeBefore = mSubject.getSentenceLibrary().size();
        mSubject.removeSentence("test", Sentence.SpeechType.STATEMENT);
        assertThat(mSubject.getSentenceLibrary().size(), is(sizeBefore-1));
    }
}