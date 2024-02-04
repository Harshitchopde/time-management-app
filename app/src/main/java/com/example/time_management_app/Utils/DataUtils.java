package com.example.time_management_app.Utils;

import com.example.time_management_app.Models.Schedule;

import java.util.ArrayList;

public class DataUtils {
   public static ArrayList<Schedule> scheduleArrayList;
    public static ArrayList<Schedule> getScheduleArrayList(){
        if(scheduleArrayList== null){
            scheduleArrayList = new ArrayList<>();
        }
        return scheduleArrayList;
    }

}
