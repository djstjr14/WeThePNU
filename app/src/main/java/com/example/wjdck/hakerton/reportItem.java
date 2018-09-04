package com.example.wjdck.hakerton;

public class reportItem {
    private String agendaKey;
    private String content;

    public String getAgendaKey() {
        return agendaKey;
    }

    public void setAgendaKey(String agendaKey) {
        this.agendaKey = agendaKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public reportItem() {}

    public reportItem(String key, String content){
        this.agendaKey = key;
        this.content = content;
    }
}
