package com.niagarakayak.niagarakayakapp.model;

/**
 * This class represents a Tweet object.
 */

public class Tweet {

    String userHandle;
    String text;

    public Tweet() {};

    /**
     * Constructor for Tweet
     * @param userHandle    The twitter handle, e.g @fzxt
     * @param text  The text contents of the tweet.
     */

    public Tweet(String userHandle, String text) {
        this.userHandle = userHandle;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getUserHandle() {
        return userHandle;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }
}
