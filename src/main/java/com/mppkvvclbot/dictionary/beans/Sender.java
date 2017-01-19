package com.mppkvvclbot.dictionary.beans;

import java.io.Serializable;

/**
 * Created by NITISH on 19-01-2017.
 */
public class Sender implements Serializable {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Sender() {
    }

    public Sender(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Sender{" +
                "id='" + id + '\'' +
                '}';
    }
}
