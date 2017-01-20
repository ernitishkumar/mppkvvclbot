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

    private Message message;

    public Sender getRecipient() {
        return recipient;
    }

    public void setRecipient(Sender recipient) {
        this.recipient = recipient;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public ReplyPayload() {
    }

    public ReplyPayload(Sender recipient, Message message) {
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
