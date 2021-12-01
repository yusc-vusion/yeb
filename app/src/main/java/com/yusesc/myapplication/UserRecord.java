package com.yusesc.myapplication;

public class UserRecord {
    private String profile;
    private String idolname;
    private float rating;
    private float confidence;
    private int count;

    public UserRecord(String profile, String idolname, float rating, float confidence, int count) {
        this.profile = profile;
        this.idolname = idolname;
        this.rating = rating;
        this.confidence = confidence;
        this.count = count;
    }

    public  UserRecord(){}

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getIdolname() {
        return idolname;
    }

    public void setIdolname(String idolname) {
        this.idolname = idolname;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
