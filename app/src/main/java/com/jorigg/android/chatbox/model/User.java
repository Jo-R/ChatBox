package com.jorigg.android.chatbox.model;


public class User {

    public enum UserType {
        CHILD, AGENT, ADULT
    }

    private String mUserName;
    private UserType mType;
    private int mUserChatAvatar; //will hold the id of the drawable
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

    public int getUserChatAvatar() {
        return mUserChatAvatar;
    }

    public void setUserChatAvatar(int userChatAvatar) {
        mUserChatAvatar = userChatAvatar;
    }
}
