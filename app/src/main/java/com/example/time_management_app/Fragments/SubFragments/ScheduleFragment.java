package com.example.time_management_app.Fragments.SubFragments;

import static android.service.controls.ControlsProviderService.TAG;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.time_management_app.Adapters.ScheduleAdapter;
import com.example.time_management_app.Interface.ApiService;
import com.example.time_management_app.Models.Schedule;
import com.example.time_management_app.R;
import com.example.time_management_app.Utils.DataUtils;
import com.example.time_management_app.Utils.RetrofitClient;
import com.example.time_management_app.Utils.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.swnishan.materialdatetimepicker.timepicker.MaterialTimePickerDialog;
import com.swnishan.materialdatetimepicker.timepicker.MaterialTimePickerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScheduleFragment extends Fragment {

ArrayList<Schedule> scheduleArrayList;
RecyclerView recyclerView;
Button create_schedule;
    SimpleDateFormat simpleDateFormat;
    TextView startTime, endTime;
    Calendar startCalTime = Calendar.getInstance();
Calendar endCalTime = Calendar.getInstance();

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
        simpleDateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
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
//                openDialogBox();
//                getYourTime(startTime, startCalTime);
openDialogBox();
            }
        });
        // write code

        return view;
    }

    private void openDialogBox() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.input_schedule_interface);
       startTime = dialog.findViewById(R.id.startTime);
       endTime= dialog.findViewById(R.id.endTime);

        Log.e(TAG, "openDialogBox: sdfnsd" );
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e(TAG, "onClick: dialog dismiss ki ");
//                dialog.dismiss();


               getYourTime(startTime,startCalTime);
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e(TAG, "onClick: dialog dismiss ki ");
//                dialog.dismiss();


                getYourTime(endTime,endCalTime);
            }
        });
        Button submit = dialog.findViewById(R.id.create_schedule_btn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String start_time = simpleDateFormat.format(startCalTime.getTime());
                String end_time = simpleDateFormat.format(endCalTime.getTime());
                TextInputEditText taskTV = dialog.findViewById(R.id.taskInput);
                String taskName = taskTV.getText().toString();
                if(TextUtils.isEmpty(taskName)){
                    taskTV.setError("Required!");
                    return;
                }
//                String
                createScheduleApi(start_time,end_time,taskName);

                Log.e(TAG, "onClick: time "+start_time+" - "+end_time );
                Toast.makeText(getActivity(), start_time+" - "+end_time, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void createScheduleApi(String start_time, String end_time, String taskName) {
        ApiService apiService = RetrofitClient.getApiService(getActivity());
        String date = Utils.getTodayDate();
        Map<String,String> params = new HashMap<>();
        params.put("startTime",start_time);
        params.put("endTime",end_time);
        params.put("taskName",taskName);
        params.put("date",date);
        Call<ResponseBody> call = apiService.createSchedule(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        Log.e(TAG, "onResponse: created shcdsdf "+response.body().string() );
                        getScheduleData();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    try {
                        Log.e(TAG, "onResponse: iska"+response.errorBody().string() );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: fdfdsof" );
            }
        });

    }

    private void getYourTime(TextView timeView, Calendar CalTime) {
        Log.e(TAG, "getYourTime: fafjlskd" );
        int hour = CalTime.get(Calendar.HOUR_OF_DAY);
        int minute = CalTime.get(Calendar.MINUTE);
        MaterialTimePickerDialog builder =  MaterialTimePickerDialog.Builder.setTitle(getString(R.string.set_start_time))
                .setNegativeButtonText(getString(R.string.cancel))
                .setPositiveButtonText(getString(R.string.ok))
                .setTimeConvention(MaterialTimePickerView.TimeConvention.HOURS_12) // default 12 hours
                .setHour(hour) // default current hour
                .setMinute(minute) // default current minute
                .setTimePeriod(MaterialTimePickerView.TimePeriod.AM) // default based on the current time
                .setFadeAnimation(350L, 1050L, .3f, .7f)
                .setTheme(com.swnishan.materialdatetimepicker.R.style.ThemeOverlay_Dialog_MaterialTimePicker) // default [R.style.ThemeOverlay_Dialog_MaterialTimePicker]
                .build();
        Log.e(TAG, "getYourTime: yha" );
        builder.setOnTimePickListener(new MaterialTimePickerView.OnTimePickedListener() {
            @Override
            public void onTimePicked(long l) {
                CalTime.setTimeInMillis(l);
//                                    int hour = startCalTime.get(Calendar.HOUR_OF_DAY);
//                                    int minute = startCalTime.get(Calendar.MINUTE);
                Log.e(TAG, "onTimePicked: kha samya" );
                updateTextTime(timeView, CalTime);

            }
        });

        builder.showNow(getActivity().getSupportFragmentManager(), "Matrialtimepicker");

    }

    private void updateTextTime(TextView timeView, Calendar callTime) {
          String time = simpleDateFormat.format(callTime.getTime());
        timeView.setText(time);
        Toast.makeText(getActivity(), "Time : "+time, Toast.LENGTH_SHORT).show();
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
                            JSONArray singleSchedule = ScheduleArray.getJSONArray(i);
                            String startTime = singleSchedule.getString(0);
                            String endTime = singleSchedule.getString(1);

                            String taskString = singleSchedule.getString(2);
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