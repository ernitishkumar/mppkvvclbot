package com.mppkvvclbot.dictionary.beans.facebook;

/**
 * Created by NITISH on 20-01-2017.
 */
public class ReplyMessage {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ReplyMessage() {
    }

    public ReplyMessage(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ReplyMessage{" +
                "text='" + text + '\'' +
                '}';
    }
}
