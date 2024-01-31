package com.example.time_management_app.Fragments.SubFragments;

import static android.service.controls.ControlsProviderService.TAG;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.time_management_app.Adapters.ScheduleAdapter;
import com.example.time_management_app.Models.Schedule;
import com.example.time_management_app.R;

import java.lang.reflect.Array;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;


public class ScheduleFragment extends Fragment {

ArrayList<Schedule> scheduleArrayList;
RecyclerView recyclerView;
ScheduleAdapter scheduleAdapter;
    public ScheduleFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        scheduleArrayList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycleview_schedule);
        demoScheduleData();
        scheduleAdapter = new ScheduleAdapter(getParentFragment().getActivity(),scheduleArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getParentFragment().getActivity()));
        recyclerView.setAdapter(scheduleAdapter);
        // write code

        return view;
    }


    private void demoScheduleData() {
        // Sample data
        Date currentDateAndTime = new Date();
        Schedule sampleSchedule1 = new Schedule(currentDateAndTime,currentDateAndTime, "Work on Project A");
        Schedule sampleSchedule2 = new Schedule(currentDateAndTime,currentDateAndTime, "Lunch");
        Schedule sampleSchedule3 = new Schedule(currentDateAndTime,currentDateAndTime, "Meeting");
        scheduleArrayList.add(sampleSchedule1);
        scheduleArrayList.add(sampleSchedule2);
        scheduleArrayList.add(sampleSchedule3);
        scheduleArrayList.add(sampleSchedule1);
        scheduleArrayList.add(sampleSchedule2);
        scheduleArrayList.add(sampleSchedule3);
        scheduleArrayList.add(sampleSchedule1);
        scheduleArrayList.add(sampleSchedule2);
        scheduleArrayList.add(sampleSchedule3);
        Log.e(TAG, "demoScheduleData: "+scheduleArrayList.size() );
        Log.e(TAG, "demoScheduleData: "+scheduleArrayList.toString() );


        // Display sample data


    }
}