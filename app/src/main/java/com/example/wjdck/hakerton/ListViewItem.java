package com.example.wjdck.hakerton;

public class ListViewItem {

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


    public void setTitle(String title){ titleStr = title; }

   public void setRecommend(String recommend){ recommendStr = recommend; }

    public void setDate(String date){ dateStr = date; }

    public String getTitle(){ return this.titleStr; }

    public String getRecommend(){ return this.recommendStr; }

    public String getDate(){ return this.dateStr; }
}
