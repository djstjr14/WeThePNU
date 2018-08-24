package com.example.wjdck.hakerton;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class discussItem implements Serializable {

    private String key;
    private String title;
    private String text;
    private long recommend;
    private long unrecommend;
    private String date;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getRecommend() {
        return recommend;
    }

    public void setRecommend(long recommend) {
        this.recommend = recommend;
    }

    public long getUnrecommend() {
        return unrecommend;
    }

    public void setUnrecommend(long unrecommend) {
        this.unrecommend = unrecommend;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public discussItem() {}

    public discussItem(String key, String title, String text, long recommend, long unrecommend, String date){
        this.key = key;
        this.title = title;
        this.text = text;
        this.recommend = recommend;
        this.unrecommend = unrecommend;
        this.date = date;
    }

}
