package com.mppkvvclbot.dictionary.beans;

import java.io.Serializable;

/**
 * Created by NITISH on 19-01-2017.
 */
public class Meaning implements Serializable {

    private String language;

    private String text;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Meaning() {
    }

    public Meaning(String language, String text) {
        this.language = language;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Meaning{" +
                "language='" + language + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
