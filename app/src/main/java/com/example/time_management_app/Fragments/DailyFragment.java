package com.example.time_management_app.Fragments;

import static android.service.controls.ControlsProviderService.TAG;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.time_management_app.Fragments.SubFragments.ActualFragment;
import com.example.time_management_app.Fragments.SubFragments.OtherFactorFragment;
import com.example.time_management_app.Fragments.SubFragments.ScheduleFragment;
import com.example.time_management_app.Interface.ApiService;
import com.example.time_management_app.Models.Schedule;
import com.example.time_management_app.R;
import com.example.time_management_app.Utils.DataUtils;
import com.example.time_management_app.Utils.FragLoad;
import com.example.time_management_app.Utils.RetrofitClient;
import com.example.time_management_app.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DailyFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private TextView dateTextView;
    private Spinner spinner;
    private FrameLayout frameLayout;
    String currentDate;
private  FragmentManager fragmentManager;
    private Calendar selectedDate = Calendar.getInstance();
    public DailyFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily,container,false);
        dateTextView = view.findViewById(R.id.dateTextView);
        spinner= view.findViewById(R.id.daily_spinner);
        frameLayout = view.findViewById(R.id.sub_fragement_daily);
//        getCurrentDateDetail();
        updateDateTextView();
         fragmentManager = getChildFragmentManager();

        FragLoad.loadFrag(new ScheduleFragment(currentDate),frameLayout,fragmentManager,1);


        spinner.setOnItemSelectedListener(this);

        dateTextView.setOnClickListener(view1 -> {
            showDatePickerDialog();
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.options_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return view;
    }

    private void getCurrentDateDetail() {
        ApiService apiService = RetrofitClient.getApiService(getActivity());
        String date = Utils.getTodayDate();
        Call<ResponseBody> call = apiService.getDateDetails(date);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
//                    Log.e(TAG, "onResponse: maza aaya "+response.body() );
                    try {
                        ResponseBody responseBody = response.body();
//                        Log.e(TAG, "onResponse:11 "+response.body().string() );

//                        if(responseBody!=null){
//                            String respo = responseBody.string();
//                            Log.e(TAG, "onResponse: khale"+respo );
//                            JSONObject jsonObjectResponse = new JSONObject(respo);
//                            JSONObject jsonObjectDate = jsonObjectResponse.getJSONObject("date");
//                            JSONArray scheduleArray = jsonObjectDate.getJSONArray("Schedule");
//
//                            ArrayList<Schedule> scheduleArrayList =DataUtils.getScheduleArrayList();
//                            for(int i =0;i<scheduleArray.length();i++){
//                                JSONObject scheduleObj = scheduleArray.getJSONObject(i);
//                                String startTime = scheduleObj.getString("startTime");
//                                String endTime = scheduleObj.getString("endTime");
//                                String taskName = scheduleObj.getString("taskName");
//                                Schedule schedule = new Schedule(startTime,endTime,taskName);
//                                scheduleArrayList.add(schedule);
//                                Log.e(TAG, "onResponse: dd"+scheduleObj.toString() );
//                            }
//                            return;
//
//                        }


//                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: "+e.toString() );
                    }
                }else {
                    try {
                        Log.e(TAG, "onResponse: response mai errror"+response.errorBody().string() );
                    } catch (IOException e) {
                        Log.e(TAG, "onResponse: "+e.toString() );
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: fail hogya"+t.toString() );
            }
        });
    }

    public void showDatePickerDialog() {
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (DatePicker view1, int year1, int monthOfYear, int dayOfMonth) -> {
                    selectedDate.set(year1, monthOfYear, dayOfMonth);
                    updateDateTextView();
                }, year, month, day);

        datePickerDialog.show();
    }

    private void updateDateTextView() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        currentDate = yyyyMMdd.format(selectedDate.getTime());
        String formattedDate = sdf.format(selectedDate.getTime());
        dateTextView.setText(formattedDate);
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // show the different fragments in it
        Log.e(TAG, "onItemSelected: "+i );
        String selectedItem = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getActivity(), "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();



        if(i==0){
            // 0 Schedule
            FragLoad.loadFrag(new ScheduleFragment(currentDate),frameLayout,fragmentManager,0);
        }else if(i==1){
            // 1 Actual
            FragLoad.loadFrag(new ActualFragment(),frameLayout,fragmentManager,0);
        }else {
           // 2 Other Factors
            FragLoad.loadFrag(new OtherFactorFragment(),frameLayout,fragmentManager,0);

        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}