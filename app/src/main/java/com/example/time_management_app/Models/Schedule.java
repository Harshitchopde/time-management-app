package com.example.time_management_app.Models;


import java.util.Date;

public class Schedule {
    private Date start_time;
    private Date end_time;
    private String task_string;


    public Schedule(Date start_time, Date end_time, String task_string) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.task_string = task_string;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getTask_string() {
        return task_string;
    }

    public void setTask_string(String task_string) {
        this.task_string = task_string;
    }
}
