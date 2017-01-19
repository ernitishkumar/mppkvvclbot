package com.mppkvvclbot.dictionary.beans;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

/**
 * Created by NITISH on 19-01-2017.
 */
public class Payload {

    private String object;

    private List<Entry> entry;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<Entry> getEntry() {
        return entry;
    }

    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }

    public Payload() {
    }

    public Payload(String object, List<Entry> entry) {
        this.object = object;
        this.entry = entry;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "object='" + object + '\'' +
                ", entry=" + entry +
                '}';
    }
}
