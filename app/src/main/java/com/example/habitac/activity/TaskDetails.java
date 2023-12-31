package com.example.habitac.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.habitac.R;
import com.example.habitac.database.Task;
import com.example.habitac.database.TaskDao;
import com.example.habitac.database.TaskDatabase;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskDetails extends AppCompatActivity {
    private final String[] keepingDays = { "30 days", "60 days", "90 days", "180 days"};
    private final String[] repeatDay = {"every day", "except weekend", "custom"};
    private final String[] classification = {"study", "exercise", "routine", "else"};

    private int keep_days, repeat_val, class_index;
    private EditText editText_taskName;
    private TimePicker timePicker;
    private View selectDayLayout;
    private boolean customizedRepeat;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch alarmSwitch;
    private boolean edit;
    private Button confirm_btn;

    private Button switchBtn;

    private int tHours, tMinutes;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        edit = false;

        setContentView(R.layout.activity_task_details);
        editText_taskName = findViewById(R.id.habitTitle_edit);
        confirm_btn = findViewById(R.id.confirm_button_task);
        Button cancel_btn = findViewById(R.id.cancel_button_task);
        Spinner spin_keep = findViewById(R.id.keep_spinner);
        Spinner spin_repeat = findViewById(R.id.repeat_spinner);
        Spinner spin_class = findViewById(R.id.classify_spinner);
        selectDayLayout = findViewById(R.id.select_day_to_repeat);

        //alarm
        switchBtn = findViewById(R.id.switch_alarm);
        timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(false);
        //timePicker.setOnTimeChangedListener((timePicker, hours, minutes) -> {});


        customizedRepeat = false;
        ArrayAdapter<String> ad_keep = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, keepingDays);
        ArrayAdapter<String> ad_repeat = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, repeatDay);
        ArrayAdapter<String> ad_class = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classification);

        ad_keep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ad_repeat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ad_class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin_keep.setAdapter(ad_keep);
        spin_class.setAdapter(ad_class);
        spin_repeat.setAdapter(ad_repeat);

        spin_keep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        keep_days = 30;
                        break;
                    case 1:
                        keep_days = 60;
                        break;
                    case 2:
                        keep_days = 90;
                        break;
                    case 3:
                        keep_days = 180;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        spin_repeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        repeat_val = 127;
                        customizedRepeat = false;
                        selectDayLayout.setVisibility(View.GONE);
                        break;
                    case 1:
                        repeat_val = 124;
                        customizedRepeat = false;
                        selectDayLayout.setVisibility(View.GONE);
                        break;
                    case 2:
                        customizedRepeat = true;
                        selectDayLayout.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        spin_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        class_index = 0;
                        break;
                    case 1:
                        class_index = 1;
                        break;
                    case 2:
                        class_index = 2;
                        break;
                    case 3:
                        class_index = 3;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        Intent intent = getIntent();
        String taskId = intent.getStringExtra("id");

        if (taskId == null) {
            Log.d("edit", "null");
        } else {
            Log.d("edit", "not null");
        }
        if (taskId != null) {
            edit = true;
            editText_taskName.setText(intent.getStringExtra("name"));
        }



        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int timePickerHour, int timePickerMinutes) {
                tHours = timePickerHour;
                tMinutes = timePickerMinutes;
            }
        });

        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

                Date date = new Date();
                Calendar calToday = Calendar.getInstance();
                Calendar calAlarm = Calendar.getInstance();

                calToday.setTime(date);
                calAlarm.setTime(date);

                calAlarm.set(Calendar.HOUR_OF_DAY,tHours);
                calAlarm.set(Calendar.MINUTE,tMinutes);
                calAlarm.set(Calendar.SECOND,0);

                if(calAlarm.before(calToday)){
                    calAlarm.add(Calendar.DATE,1);
                }

                int alarmCount=1;
                Intent intent = new Intent(TaskDetails.this,AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(TaskDetails.this,alarmCount++,intent,0);
                alarmManager.set(AlarmManager.RTC_WAKEUP,calAlarm.getTimeInMillis(),pendingIntent);


            }
        });

        confirm_btn.setOnClickListener(view -> {
            ExecutorService service = Executors.newSingleThreadExecutor();
            service.execute(() -> {
                TaskDatabase taskDatabase = TaskDatabase.getDatabase(getApplicationContext());
                TaskDao taskDao = taskDatabase.getDao();
                Task task = new Task();
                task.setName(editText_taskName.getText().toString());
                task.setPlanDays(keep_days);
                task.setIsDone(0);
                task.setRemainDays(keep_days);
                task.setClassification(class_index);
                if (customizedRepeat) {
                    repeat_val = 0;
                    CheckBox sun = findViewById(R.id.checkBox_day_7);
                    CheckBox sat = findViewById(R.id.checkBox_day_6);
                    CheckBox fr = findViewById(R.id.checkBox_day_5);
                    CheckBox th = findViewById(R.id.checkBox_day_4);
                    CheckBox we = findViewById(R.id.checkBox_day_3);
                    CheckBox tu = findViewById(R.id.checkBox_day_2);
                    CheckBox mo = findViewById(R.id.checkBox_day_1);
                    if (sun.isChecked()) { repeat_val += 1;}
                    if (sat.isChecked())  { repeat_val += 2; }
                    if (fr.isChecked())  { repeat_val += 4; }
                    if (th.isChecked())  { repeat_val += 8; }
                    if (we.isChecked())  { repeat_val += 16; }
                    if (tu.isChecked())  { repeat_val += 32; }
                    if (mo.isChecked())  { repeat_val += 64; }
                }
                task.setFrequency(repeat_val);

                if (edit) {
                    assert taskId != null;
                    task.setId(Integer.parseInt(taskId));
                    taskDao.updateTask(task);
                    Log.d("edit", "true");
                } else {
                    taskDao.insertTask(task);
                    Log.d("edit", "false");
                }
            });
            Main.actionStart(TaskDetails.this, null, null);
        });



        cancel_btn.setOnClickListener(view -> Main.actionStart(TaskDetails.this, null, null));
    }




}



