package com.mppkvvclbot.dictionary.beans.facebook;

import java.io.Serializable;

/**
 * Created by NITISH on 19-01-2017.
 */
public class Message implements Serializable {

    private String mid;

    private long seq;

    private String text;

    private QuickReply quick_reply;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public QuickReply getQuick_reply() {
        return quick_reply;
    }

    public void setQuick_reply(QuickReply quick_reply) {
        this.quick_reply = quick_reply;
    }

    public Message() {
    }

    public Message(String mid, long seq, String text, QuickReply quick_reply) {
        this.mid = mid;
        this.seq = seq;
        this.text = text;
        this.quick_reply = quick_reply;
    }

    @Override
    public String toString() {
        return "Message{" +
                "mid='" + mid + '\'' +
                ", seq=" + seq +
                ", text='" + text + '\'' +
                ", quick_reply=" + quick_reply +
                '}';
    }
}

class QuickReply{
    private String payload;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public QuickReply() {
    }

    public QuickReply(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "QuickReply{" +
                "payload='" + payload + '\'' +
                '}';
    }
}
