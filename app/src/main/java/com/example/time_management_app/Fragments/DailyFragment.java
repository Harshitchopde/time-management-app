package com.example.time_management_app.Fragments;

import static android.service.controls.ControlsProviderService.TAG;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.time_management_app.Fragments.SubFragments.ActualFragment;
import com.example.time_management_app.Fragments.SubFragments.OtherFactorFragment;
import com.example.time_management_app.Fragments.SubFragments.ScheduleFragment;
import com.example.time_management_app.R;
import com.example.time_management_app.Utils.FragLoad;
import com.example.time_management_app.Utils.Utils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;


public class DailyFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private TextView dateTextView;
    private Spinner spinner;
    private FrameLayout frameLayout;
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
        // for getDateDetails
        getDateDetails();

         fragmentManager = getChildFragmentManager();
        FragLoad.loadFrag(new ScheduleFragment(),frameLayout,fragmentManager,1);


        spinner.setOnItemSelectedListener(this);
        updateDateTextView();
        dateTextView.setOnClickListener(view1 -> {
            showDatePickerDialog();
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.options_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return view;
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
            FragLoad.loadFrag(new ScheduleFragment(),frameLayout,fragmentManager,0);
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
    private void getDateDetails() {
        try{
            AndroidNetworking.get(Utils.BASE_URL+"date/getDateDetails")
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e(TAG, "onResponse: hogya bhai"+response.toString() );
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e(TAG, "onError: error"+anError.toString() );
                        }
                    });
        }catch (Exception e){
            Log.e(TAG, "createTodaysDetails: error hogy bhai"+e.toString() );
        }
    }
}