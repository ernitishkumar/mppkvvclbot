package com.mppkvvclbot.dictionary.beans.meaning;

import java.io.Serializable;
import java.util.List;

/**
 * Created by NITISH on 19-01-2017.
 */
public class DictionaryResponse implements Serializable {

    private String result;

    private List<DictionaryTuc> tuc;

    private String phrase;

    private String from;

    private String dest;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<DictionaryTuc> getTuc() {
        return tuc;
    }

    public void setTuc(List<DictionaryTuc> tuc) {
        this.tuc = tuc;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public DictionaryResponse() {
    }

    public DictionaryResponse(String result, List<DictionaryTuc> tuc, String phrase, String from, String dest) {
        this.result = result;
        this.tuc = tuc;
        this.phrase = phrase;
        this.from = from;
        this.dest = dest;
    }

    @Override
    public String toString() {
        return "DictionaryResponse{" +
                "result='" + result + '\'' +
                ", tuc=" + tuc +
                ", phrase='" + phrase + '\'' +
                ", from='" + from + '\'' +
                ", dest='" + dest + '\'' +
                '}';
    }
}
