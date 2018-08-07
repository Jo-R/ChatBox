package com.jorigg.android.chatbox.model;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ChatBankTest {

    @Mock
    Context mockContext;

    private ChatBank mSubject;

    @Before
    public void setUp() throws Exception {
        mSubject = ChatBank.get(mockContext);

    }

    @Test
    public void get() {
    }

    @Test
    public void getChatTitles() {
    }

    @Test
    public void addNewConversation() {
    }

    @Test
    public void getConversationLibrary() {
    }

    @Test
    public void getConversation() {
    }

    @Test
    public void deleteConversation() {
    }
}