package com.example.gadsleaderboard.models;

public class LearnerOne {
    private int mScore;
    private String mName, mCountry, mBadgeImageUrl;

    public LearnerOne(String name, String country, int score, String badgeImageUrl) {
        mName = name;
        mCountry = country;
        mScore = score;
        mBadgeImageUrl = badgeImageUrl;
    }

    public int getScore() {
        return mScore;
    }

    public String getName() {
        return mName;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getBadgeImageUrl() {
        return mBadgeImageUrl;
    }
}
