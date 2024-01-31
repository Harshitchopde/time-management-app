package com.example.time_management_app.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.time_management_app.Models.Schedule;
import com.example.time_management_app.R;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

private Context context;
private ArrayList<Schedule> scheduleArrayList;

    public ScheduleAdapter(Context context,ArrayList<Schedule> scheduleArrayList){
        this.context = context;
        this.scheduleArrayList = scheduleArrayList;

    }
    @NonNull
    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.schedule_single_card,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.ViewHolder holder, int position) {
// here you bind value;
        Schedule oneSchedule = scheduleArrayList.get(position);
        holder.startTime.setText(""+oneSchedule.getStart_time());
        holder.endTime.setText(""+oneSchedule.getEnd_time());
        holder.taskString.setText(oneSchedule.getTask_string());

    }

    @Override
    public int getItemCount() {
        return scheduleArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView startTime,endTime,taskString;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            startTime = itemView.findViewById(R.id.start_time_txt);
            endTime = itemView.findViewById(R.id.end_time_txt);
            taskString = itemView.findViewById(R.id.task_string);

        }
    }
}
