package com.jorigg.android.chatbox.model;

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


    public UserType getType() {
        return mType;
    }

}
