package com.example.wjdck.hakerton;

public class answerItem {
    private String answer_number;
    private String answering_date;
    private String startdate;
    private String enddate;
    private String title;
    private long answer_people;

    public String getAnswer_number(){return answer_number;}
    public void setAnswer_number(String answer_number){this.answer_number=answer_number;}

    public String getAnswering_date(){return answering_date;}
    public void setAnswering_date(String answering_date){this.answering_date=answering_date;}

    public String getStartdate(){return startdate;}
    public void setStartdate(String startdate){this.startdate=startdate;}

    public String getEnddate(){return enddate;}
    public void setEnddate(String enddate){this.enddate = enddate;}

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public long getAnswer_people() {return answer_people;}
    public void setAnswer_people() {this.answer_people = answer_people;}



    public answerItem() {}

    public answerItem(String answer_number, String title, String answering_date, String startdate, long answer_people, String enddate){
        this.answer_number = answer_number;
        this.title = title;
        this.answer_people = answer_people;
        this.startdate= startdate;
        this.enddate = enddate;
        this.answering_date = answering_date;

    }
}
