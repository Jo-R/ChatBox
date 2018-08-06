package com.jorigg.android.chatbox.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SentenceTest {

    private Sentence mSubject;

    @Before
    public void setUp() throws Exception {
        mSubject = new Sentence("test sentence", Sentence.SpeechType.STATEMENT);
    }


    @Test
    public void getContent() {
        assertThat(mSubject.getContent(), is("test sentence"));
    }

    @Test
    public void getSpeechType() {
        assertThat(mSubject.getSpeechType(), is(Sentence.SpeechType.STATEMENT));
    }

    @Test
    public void testToString() {
        assertThat(mSubject.toString(), is("test sentence"));
    }
}