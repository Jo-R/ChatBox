package com.bignerdranch.android.chatbox.model;

public class User {

    public enum UserType {
        CHILD, AGENT, ADULT
    }

    private String mUserName;
    private UserType mType;
    //plus an image they choose


    public User(String userName, UserType type) {
        mUserName = userName;
        mType = type;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public UserType getType() {
        return mType;
    }

    public void setType(UserType type) {
        mType = type;
    }
}
