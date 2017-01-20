package com.mppkvvclbot.dictionary.beans.mongo;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Created by NITISH on 19-01-2017.
 */
public class Word implements Serializable {

    @Id
    private String id;

    private String word;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Word() {
    }

    public Word(String word) {
        this.word = word;
    }

    public Word(String id, String word) {
        this.id = id;
        this.word = word;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id='" + id + '\'' +
                ", word='" + word + '\'' +
                '}';
    }
}
