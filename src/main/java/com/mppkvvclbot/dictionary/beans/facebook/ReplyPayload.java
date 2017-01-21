package com.mppkvvclbot.dictionary.beans.facebook;

import java.io.Serializable;

/**
 * Created by NITISH on 20-01-2017.
 */
public class ReplyPayload implements Serializable {

    /*
     * Object of Sender to which this payload will get delivered
     */
    private Sender recipient;

    private ReplyMessage message;

    public Sender getRecipient() {
        return recipient;
    }

    public void setRecipient(Sender recipient) {
        this.recipient = recipient;
    }

    public ReplyMessage getMessage() {
        return message;
    }

    public void setMessage(ReplyMessage message) {
        this.message = message;
    }

    public ReplyPayload() {
    }

    public ReplyPayload(Sender recipient, ReplyMessage message) {
        this.recipient = recipient;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ReplyPayload{" +
                "recipient=" + recipient +
                ", message=" + message +
                '}';
    }
}
