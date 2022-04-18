package com.example.habitac.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.habitac.R;
import com.example.habitac.database.Task;
import com.example.habitac.database.TaskDao;
import com.example.habitac.database.TaskDatabase;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_task_details);
        editText_taskName = findViewById(R.id.habitTitle_edit);
        Button confirm_btn = findViewById(R.id.confirm_button_task);
        Button cancel_btn = findViewById(R.id.cancel_button_task);
        Spinner spin_keep = findViewById(R.id.keep_spinner);
        Spinner spin_repeat = findViewById(R.id.repeat_spinner);
        Spinner spin_class = findViewById(R.id.classify_spinner);
        selectDayLayout = findViewById(R.id.select_day_to_repeat);
        customizedRepeat = false;
        alarmSwitch = findViewById(R.id.switch_alarm);

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
                    if (sun.isChecked()) { repeat_val += 1; }
                    if (sat.isChecked())  { repeat_val += 2; }
                    if (fr.isChecked())  { repeat_val += 4; }
                    if (th.isChecked())  { repeat_val += 8; }
                    if (we.isChecked())  { repeat_val += 16; }
                    if (tu.isChecked())  { repeat_val += 32; }
                    if (mo.isChecked())  { repeat_val += 64; }
                }
                task.setFrequency(repeat_val);
                if (alarmSwitch.isChecked()) {
                    task.setRemindHour(timePicker.getHour());
                    task.setRemindMin(timePicker.getMinute());
                } else {
                    task.setRemindHour(-1);
                    task.setRemindMin(-1);
                }
                taskDao.insertTask(task);
            });
            Main.actionStart(TaskDetails.this, null, null);
        });

        timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(false);
        timePicker.setOnTimeChangedListener((timePicker, hours, minutes) -> {});

        cancel_btn.setOnClickListener(view -> Main.actionStart(TaskDetails.this, null, null));
    }

}


