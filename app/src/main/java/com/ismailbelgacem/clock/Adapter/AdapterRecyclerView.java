package com.ismailbelgacem.clock.Adapter;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ismailbelgacem.clock.Data.AlarmRspo;
import com.ismailbelgacem.clock.Model.Alarme;
import com.ismailbelgacem.clock.R;
import com.ismailbelgacem.clock.ViewModel.CreateAlarmViewModel;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.MyViewHolder> {
    private List<Alarme> alarms;
    private OnToggleAlarmListener listener;
    private CreateAlarmViewModel viewModel;
    Context context;
    public AdapterRecyclerView(OnToggleAlarmListener listener,Context context) {
        this.alarms = new ArrayList<Alarme>();
        this.listener = listener;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterRecyclerView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
        return new MyViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerView.MyViewHolder holder, int position) {
        Alarme alarm = alarms.get(position);
        holder.bind(alarm);
    }
    @Override
    public int getItemCount() {
        return alarms.size();
    }
    public void setAlarms(List<Alarme> alarms) {
        this.alarms = alarms;
        notifyDataSetChanged();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView alarmTime;
        private ImageView alarmRecurring,item_delete;
        private TextView alarmRecurringDays;
        private OnToggleAlarmListener listener;
        Switch alarmStarted;
        public MyViewHolder(@NonNull View itemView,OnToggleAlarmListener listener) {
            super(itemView);
            alarmTime = itemView.findViewById(R.id.item_alarm_time);
            alarmStarted = itemView.findViewById(R.id.item_alarm_started);
            alarmRecurring = itemView.findViewById(R.id.item_alarm_recurring);
            alarmRecurringDays = itemView.findViewById(R.id.item_alarm_recurringDays);
            item_delete= itemView.findViewById(R.id.item_delete);
            this.listener = listener;
        }
        public void bind(Alarme alarm) {
            String alarmText = String.format("%02d:%02d", alarm.getHour(), alarm.getMinute());

            alarmTime.setText(alarmText);
            alarmStarted.setChecked(alarm.isStarted());

            if (alarm.isRecurring()) {
                alarmRecurring.setImageResource(R.drawable.ic_baseline_repeat_24);
                alarmRecurringDays.setText(alarm.getRecurringDaysText());
            } else {
                alarmRecurring.setImageResource(R.drawable.ic_baseline_looks_one_24);
                alarmRecurringDays.setText("Once Off");
            }
            alarmStarted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    listener.onToggle(alarm);
                }
            });
            item_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewModel = new CreateAlarmViewModel((Application) context);
                    viewModel.delete(alarm);
                    listener.onToggle(alarm);
                }
            });
        }
    }
}
