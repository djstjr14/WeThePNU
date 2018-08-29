package com.example.wjdck.hakerton;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class UserItem {
    private Map<String, PostItem> bookmark = new HashMap<>();
    private Map<String, PostItem> pushalarm = new HashMap<>();

    @Exclude
    public Map<String, PostItem> toMap() {
        HashMap<String, PostItem> result = new HashMap<>();

        result.put("bookmark", null);
        result.put("pushalarm", null);
        return result;
    }

    public Map<String, PostItem> getBookmark() {
        return bookmark;
    }

    public void setBookmark(Map<String, PostItem> bookmark) {
        this.bookmark = bookmark;
    }

    public Map<String, PostItem> getPushalarm() {
        return pushalarm;
    }

    public void setPushalarm(Map<String, PostItem> pushalarm) {
        this.pushalarm = pushalarm;
    }

    public UserItem() {}
}