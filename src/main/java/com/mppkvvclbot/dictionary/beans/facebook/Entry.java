package com.mppkvvclbot.dictionary.beans.facebook;

import java.io.Serializable;
import java.util.List;

/**
 * Created by NITISH on 19-01-2017.
 */
public class Entry implements Serializable{

    private String id;

    private String time;

    private List<RecievedMessage> messaging;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<RecievedMessage> getMessaging() {
        return messaging;
    }

    public void setMessaging(List<RecievedMessage> messaging) {
        this.messaging = messaging;
    }

    public Entry() {
    }

    public Entry(String id, String time, List<RecievedMessage> messaging) {
        this.id = id;
        this.time = time;
        this.messaging = messaging;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", messaging=" + messaging +
                '}';
    }
}
