package com.example.time_management_app.Models;


import java.util.Date;

public class Schedule {
    private String start_time;
    private String end_time;
    private String task_string;


    public Schedule(String start_time, String end_time, String task_string) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.task_string = task_string;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getTask_string() {
        return task_string;
    }

    public void setTask_string(String task_string) {
        this.task_string = task_string;
    }
}
