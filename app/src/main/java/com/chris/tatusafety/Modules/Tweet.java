package com.chris.tatusafety.Modules;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Josephine Menge on 06/07/2017.
 */

@Parcel
public class Tweet {

    String mTweetText;
    ArrayList<String> mHashTags;
    String mExternalLink;
    String mUser;
    String mDate;

    public Tweet(){}

    public Tweet(String tweetText, ArrayList<String> hashTags,String externalLink,String user,String date){
        this.mTweetText = tweetText;
        this.mHashTags = hashTags;
        this.mExternalLink = externalLink;
        this.mUser = user;
        this.mDate = date;
    }

    public String getTweetText(){
        return mTweetText;
    }

    public ArrayList<String> getHashTags(){
        return mHashTags;
    }

    public String getExternalLink(){
        return mExternalLink;
    }

    public String getUser(){
        return mUser;
    }

    public String getDate() {
        return mDate;
    }
}

