package com.example.wjdck.hakerton;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class UserItem {
    private Map<String, Boolean> bookmark = new HashMap<>();
    private Map<String, Boolean> pushalarm = new HashMap<>();

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("bookmark", bookmark);
        result.put("pushalarm", pushalarm);
        return result;
    }

    public Map<String, Boolean> getBookmark() {
        return bookmark;
    }

    public void setBookmark(Map<String, Boolean> bookmark) {
        this.bookmark = bookmark;
    }

    public Map<String, Boolean> getPushalarm() {
        return pushalarm;
    }

    public void setPushalarm(Map<String, Boolean> pushalarm) {
        this.pushalarm = pushalarm;
    }

    public UserItem() {
        bookmark.put("admin", false);
        pushalarm.put("admin", false);
    }
}
