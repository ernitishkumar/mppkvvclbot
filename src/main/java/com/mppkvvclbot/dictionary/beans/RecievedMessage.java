package com.mppkvvclbot.dictionary.beans;

import com.sun.corba.se.impl.protocol.giopmsgheaders.*;
import com.sun.org.apache.xml.internal.serializer.utils.SerializerMessages_zh_CN;

import java.io.Serializable;

/**
 * Created by NITISH on 19-01-2017.
 */
public class RecievedMessage implements Serializable{

    private Sender sender;

    private Recipient recipient;

    private String timestamp;

    private Message message;

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public RecievedMessage() {
    }

    public RecievedMessage(Sender sender, Recipient recipient, String timestamp, Message message) {
        this.sender = sender;
        this.recipient = recipient;
        this.timestamp = timestamp;
        this.message = message;
    }

    @Override
    public String toString() {
        return "RecievedMessage{" +
                "sender=" + sender +
                ", recipient=" + recipient +
                ", timestamp='" + timestamp + '\'' +
                ", message=" + message +
                '}';
    }
}
