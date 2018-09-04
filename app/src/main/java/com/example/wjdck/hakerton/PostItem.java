package com.example.wjdck.hakerton;

public class PostItem {
    private String key;
    private String title;
    private String text;
    private String category;
    private long recommend;
    private String date;
    private boolean book;
    private boolean push;
    private int answerNum;
    private String answerDate;

    public int getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(int answerNum) {
        this.answerNum = answerNum;
    }

    public String getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(String answerDate) {
        this.answerDate = answerDate;
    }

    public boolean isBook() {
        return book;
    }

    public void setBook(boolean book) {
        this.book = book;
    }

    public boolean isBookmark() {
        return book;
    }

    public void setBookmark(boolean book) {
        this.book = book;
    }

    public boolean isPush() {
        return push;
    }

    public void setPush(boolean push) {
        this.push = push;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getRecommend() {
        return recommend;
    }

    public void setRecommend(long recommend) {
        this.recommend = recommend;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public PostItem() {}

    public PostItem(String key, String title, String text, String category, long recommend, String date, int answerNum, String answerDate){
        this.key = key;
        this.title = title;
        this.text = text;
        this.category = category;
        this.recommend = recommend;
        this.date = date;
        this.book = false;
        this.push = false;
        this.answerNum = answerNum;
        this.answerDate = answerDate;
    }
}
