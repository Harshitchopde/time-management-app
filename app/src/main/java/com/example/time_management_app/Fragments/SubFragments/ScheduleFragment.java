package com.example.time_management_app.Fragments.SubFragments;

import static android.service.controls.ControlsProviderService.TAG;

import android.app.Dialog;
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
import android.widget.Button;

import com.example.time_management_app.Adapters.ScheduleAdapter;
import com.example.time_management_app.Interface.ApiService;
import com.example.time_management_app.Models.Schedule;
import com.example.time_management_app.R;
import com.example.time_management_app.Utils.DataUtils;
import com.example.time_management_app.Utils.RetrofitClient;
import com.example.time_management_app.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScheduleFragment extends Fragment {

ArrayList<Schedule> scheduleArrayList;
RecyclerView recyclerView;
Button create_schedule;
ScheduleAdapter scheduleAdapter;
    public ScheduleFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        scheduleArrayList = DataUtils.getScheduleArrayList();
//        Log.e(TAG, "onCreateView: "+scheduleArrayList.toString() );
        create_schedule  = view.findViewById(R.id.create_schedule);
        recyclerView = view.findViewById(R.id.recycleview_schedule);
        getScheduleData();
        scheduleAdapter = new ScheduleAdapter(getParentFragment().getActivity(),scheduleArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getParentFragment().getActivity()));
        recyclerView.setAdapter(scheduleAdapter);
        create_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogBox();

            }
        });
        // write code

        return view;
    }

    private void openDialogBox() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.input_schedule_interface);
        Button submit = dialog.findViewById(R.id.create_schedule_btn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void getScheduleData() {
        ApiService apiService = RetrofitClient.getApiService(getActivity());
        String date = Utils.getTodayDate();
        Call<ResponseBody> call = apiService.getSchedules(date);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if(response.isSuccessful()){
                        ResponseBody responseBody = response.body();
                        String respo = responseBody.string();
                        Log.e(TAG, "onResponse: aya schdule : "+respo);
                        JSONObject jsonRepo =new JSONObject(respo);
                        JSONArray ScheduleArray = jsonRepo.getJSONArray("Schedule");
                        Log.e(TAG, "onResponse: chal rha hai" );
                        scheduleArrayList.clear();
                        for(int i=0;i<ScheduleArray.length();i++){
                            JSONObject singleSchedule = ScheduleArray.getJSONObject(i);
                            String startTime = singleSchedule.getString("startTime");
                            String endTime = singleSchedule.getString("endTime");
                            String taskString = singleSchedule.getString("taskName");
                            Schedule schedule = new Schedule(startTime,endTime,taskString);
                            scheduleArrayList.add(schedule);
                        }
                        scheduleAdapter.notifyDataSetChanged();

                    }else{
                        Log.e(TAG, "onResponse: galate: "+response.body().string() );
                    }

                } catch (IOException e) {
                    Log.e(TAG, "onResponse: not in string schdule "+e.toString() );
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: getSchedule faile: "+t.toString() );
            }
        });

        // Sample data

//
//        Schedule sampleSchedule2 = new Schedule(currentDateAndTime,currentDateAndTime, "Lunch");
//        Schedule sampleSchedule3 = new Schedule(currentDateAndTime,currentDateAndTime, "Meeting");
//        scheduleArrayList.add(sampleSchedule1);
//        scheduleArrayList.add(sampleSchedule2);
//        scheduleArrayList.add(sampleSchedule3);
//        scheduleArrayList.add(sampleSchedule1);
//        scheduleArrayList.add(sampleSchedule2);
//        scheduleArrayList.add(sampleSchedule3);
//        scheduleArrayList.add(sampleSchedule1);
//        scheduleArrayList.add(sampleSchedule2);
//        scheduleArrayList.add(sampleSchedule3);
//        Log.e(TAG, "demoScheduleData: "+scheduleArrayList.size() );
//        Log.e(TAG, "demoScheduleData: "+scheduleArrayList.toString() );


        // Display sample data


    }
}