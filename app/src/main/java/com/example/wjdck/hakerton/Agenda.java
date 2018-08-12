package com.example.wjdck.hakerton;

public class Agenda {
    public String title;
    public String agenda;
    public long recommend;
    public long date;

    public Agenda() {}

    public Agenda(String title, String agenda, long recommend, long date){
        this.title = title;
        this.agenda = agenda;
        this.recommend = recommend;
        this.date = date;
    }
}
