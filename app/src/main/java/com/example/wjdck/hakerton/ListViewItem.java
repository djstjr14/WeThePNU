package com.example.wjdck.hakerton;

import java.io.Serializable;

public class ListViewItem implements Serializable {

    private String key;
    private String title;
    private String text;
    private String recommend;
    private String date;

    /*
    public ListViewItem(String titleStr, String recommendStr, String dateStr){
        this.titleStr = titleStr;
        this.recommendStr = recommendStr;
        this.dateStr = dateStr;
    }
    */

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

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
