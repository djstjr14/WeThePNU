package com.example.wjdck.hakerton;

public class ListViewItem {

    private String key;
    private String titleStr;
    private String recommendStr;
    private String dateStr;

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

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public String getRecommendStr() {
        return recommendStr;
    }

    public void setRecommendStr(String recommendStr) {
        this.recommendStr = recommendStr;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public void setTitle(String title){ titleStr = title; }

   public void setRecommend(String recommend){ recommendStr = recommend; }

    public void setDate(String date){ dateStr = date; }

    public String getTitle(){ return this.titleStr; }

    public String getRecommend(){ return this.recommendStr; }

    public String getDate(){ return this.dateStr; }
}
