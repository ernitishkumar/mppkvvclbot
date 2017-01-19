package com.mppkvvclbot.dictionary.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by NITISH on 19-01-2017.
 */
public class DictionaryTuc implements Serializable {

    private String meaningId;

    private List<Meaning> meanings;

    public String getMeaningId() {
        return meaningId;
    }

    public void setMeaningId(String meaningId) {
        this.meaningId = meaningId;
    }

    public List<Meaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<Meaning> meanings) {
        this.meanings = meanings;
    }

    public DictionaryTuc() {
    }

    public DictionaryTuc(String meaningId, List<Meaning> meanings) {
        this.meaningId = meaningId;
        this.meanings = meanings;
    }

    @Override
    public String toString() {
        return "DictionaryTuc{" +
                "meaningId='" + meaningId + '\'' +
                ", meanings=" + meanings +
                '}';
    }
}
