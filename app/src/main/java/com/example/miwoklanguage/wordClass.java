package com.example.miwoklanguage;

public class wordClass {
    private String english = "";
    private String miwok = "";
    private int mImageResourceId = 0;
    private int mAudioResourceId = 0;

    public wordClass(){
        this.english = "none";
        this.miwok = "none";
        this.mImageResourceId = 0;
    }

    public wordClass(String english, String miwok, int mAudioResourceId){
        this.english = english;
        this.miwok = miwok;
        this.mAudioResourceId = mAudioResourceId;
    }

    public wordClass(String english, String miwok, int mImageResourceId, int mAudioResourceId){
        this.english = english;
        this.miwok = miwok;
        this.mImageResourceId = mImageResourceId;
        this.mAudioResourceId = mAudioResourceId;
    }

    public String getDefaultTranslation(){
        return english;
    }
    public String getMiwokTranslation(){
        return miwok;
    }
    public int getmImageResourceId(){return mImageResourceId;}
    public int getmAudioResourceId() {
        return mAudioResourceId;
    }
}
