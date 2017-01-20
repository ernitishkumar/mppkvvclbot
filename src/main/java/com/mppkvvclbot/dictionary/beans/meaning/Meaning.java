package com.mppkvvclbot.dictionary.beans.meaning;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Created by NITISH on 19-01-2017.
 */
public class Meaning implements Serializable {

    @Id
    private String id;

    private String language;

    private String text;

    private String wordId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public Meaning() {
    }

    public Meaning(String language, String text) {
        this.language = language;
        this.text = text;
    }

    public Meaning(String language, String text, String wordId) {
        this.language = language;
        this.text = text;
        this.wordId = wordId;
    }

    public Meaning(String id, String language, String text, String wordId) {
        this.id = id;
        this.language = language;
        this.text = text;
        this.wordId = wordId;
    }

    @Override
    public String toString() {
        return "Meaning{" +
                "id='" + id + '\'' +
                ", language='" + language + '\'' +
                ", text='" + text + '\'' +
                ", wordId='" + wordId + '\'' +
                '}';
    }
}
