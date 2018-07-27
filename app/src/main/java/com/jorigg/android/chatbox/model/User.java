package com.jorigg.android.chatbox.model;

import android.graphics.drawable.Drawable;

public class User {

    public enum UserType {
        CHILD, AGENT, ADULT
    }

    private String mUserName;
    private UserType mType;
    private Drawable mUserChatAvatar;
    //todo also be able to upload a homepage image

    public User(String userName, UserType type) {
        mUserName = userName;
        mType = type;
    }

    public String getUserName() {
        return mUserName;
    }


    public UserType getType() {
        return mType;
    }

    public Drawable getUserChatAvatar() {
        return mUserChatAvatar;
    }

    public void setUserChatAvatar(Drawable userChatAvatar) {
        mUserChatAvatar = userChatAvatar;
    }
}
