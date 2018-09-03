package com.example.wjdck.hakerton;

public class CommentItem {
    String key = "";
    String userid;
    String body;
    long date;

    public CommentItem() {}

    public CommentItem(String userid, String body, long date) {
        this.userid = userid;
        this.body = body;
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserid()
    {
        String hiddenid;
        String star="";
        for(int i=0; i<userid.length()-1;i++){
            star = star+"*";
        }
        hiddenid = userid.substring(0,1)+star;


        userid = hiddenid;

        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
