package com.example.wjdck.hakerton;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
@IgnoreExtraProperties
public class discussItem implements Serializable {

    private String key;
    private String title;
    private String text;
    private long recommend;
    private long unrecommend;
    private String date;
    private long hits;
    private long comments;
    public Map<String, Boolean> recommended = new HashMap<>();
    public Map<String, Boolean> clicked = new HashMap<>();

    public Map<String, Boolean> getRecommended() {
        return recommended;
    }

    public void setRecommended(Map<String, Boolean> recommended) {
        this.recommended = recommended;
    }

    public long getHits() {
        return hits;
    }

    public void setHits(long hits) {
        this.hits = hits;
    }

    public long getComments() {
        return comments;
    }

    public void setComments(long comments) {
        this.comments = comments;
    }

    public Map<String, Boolean> getClicked() {
        return clicked;
    }

    public void setClicked(Map<String, Boolean> clicked) {
        this.clicked = clicked;
    }

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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("key", key);
        result.put("title", title);
        result.put("text", text);
        result.put("recommend", recommend);
        result.put("unrecommend", unrecommend);
        result.put("date", date);
        result.put("hits", hits);
        result.put("comments", comments);
        result.put("recommended", recommended);
        result.put("clicked", clicked);
        return result;
    }

    public discussItem() {}

    public discussItem(String key, String title, String text, long recommend, long unrecommend, String date, long hits, long comments){
        this.key = key;
        this.title = title;
        this.text = text;
        this.recommend = recommend;
        this.unrecommend = unrecommend;
        this.date = date;
        this.hits = hits;
        this.comments = comments;
    }
}
