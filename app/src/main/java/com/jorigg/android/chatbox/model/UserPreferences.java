package com.jorigg.android.chatbox.model;


import android.content.Context;
import android.preference.PreferenceManager;

import com.jorigg.android.chatbox.R;

public class UserPreferences {

    public enum UserType {
        CHILD, AGENT, ADULT
    }

    private static final String PREF_USER_NAME = "userName";
    private static final String PREF_USER_AVATAR = "userAvatar";
    private static final String PREF_USER_SCORE = "userScore";

    public static String getUserName(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_USER_NAME,
                "");
    }

    public static int getUserAvatar(Context context) {
        //defaults boy figure if user pref isn't set
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(PREF_USER_AVATAR,
                R.drawable.boy_figure_test);
    }

    public static int getUserScore(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(PREF_USER_SCORE, 0);
    }

    public static void setUserName(Context context, String userName) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_USER_NAME,
                userName).apply();
    }

    public static void setUserAvatar(Context context, int avatarId) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(PREF_USER_AVATAR,
                avatarId).apply();
    }

    public static void setUserScore(Context context, int newScore) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(PREF_USER_SCORE,
                newScore).apply();
    }
}
