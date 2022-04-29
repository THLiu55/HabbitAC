package com.example.habitac.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.habitac.R;
import com.example.habitac.adapter.CalendarDoneAdapter;
import com.example.habitac.adapter.CalendarTodoAdapter;
import com.example.habitac.database.Task;
import com.example.habitac.database.TaskDao;
import com.example.habitac.database.TaskDatabase;
import com.example.habitac.database.TaskHistory;
import com.example.habitac.model.MainViewModel;
import com.example.habitac.utils.DateToday;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CalendarFragment extends Fragment implements
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener {
    private View root;
    TextView todayView;
    TextView mTextMonthDay;
    private final String[] month = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    TextView mTextYear;

    TextView mTextCurrentDay;

    TaskDao dao;

    RecyclerView recyclerView_todo;
    RecyclerView recyclerView_done;
    private LiveData<List<TaskHistory>> todoHistory;
    private LiveData<List<TaskHistory>> doneHistory;
    private LiveData<List<Task>> todayDoneHistory;
    private LiveData<List<Task>> todayTodoHistory;

    CalendarView mCalendarView;
    RelativeLayout mRelativeTool;
    private int mYear;
    private int mMonth;
    private int mDay;
    CalendarLayout mCalendarLayout;
    CalendarTodoAdapter calendarTodoAdapter;
    CalendarDoneAdapter calendarDoneAdapter;
    MainViewModel mainViewModel;
    Map<String, List<TaskHistory>> todoOfDay = new HashMap<>();
    Map<String, List<TaskHistory>> doneOfDay = new HashMap<>();
    SimpleDateFormat sdf;

    MutableLiveData<Map<String, int[]>> progressBarMap = new MutableLiveData<>(new HashMap<>());

//    GroupRecyclerView mRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_calendar, container, false);
        mTextMonthDay = root.findViewById(R.id.tv_month_day);
        mTextYear = root.findViewById(R.id.tv_year);
        mRelativeTool = root.findViewById(R.id.rl_tool);
        mCalendarView = root.findViewById(R.id.calendarView);
        mTextCurrentDay = root.findViewById(R.id.tv_current_day);
        mCalendarLayout = root.findViewById(R.id.calendarLayout);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarLayout.expand();
                    return;
                }
                mCalendarView.showYearSelectLayout(mYear);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });
        root.findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.scrollToCurrent();
            }
        });
        mCalendarLayout = root.findViewById(R.id.calendarLayout);
        mCalendarView.setOnCalendarSelectListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mMonth = mCalendarView.getCurMonth();
        mDay = mCalendarView.getCurDay();
        recyclerView_todo = root.findViewById(R.id.recycler_view_calendar_todo);
        recyclerView_done = root.findViewById(R.id.recycler_view_calendar_done);
        mTextMonthDay.setText(mCalendarView.getCurDay() + " " + month[mCalendarView.getCurMonth()]);
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
        dao = TaskDatabase.getDatabase(getContext()).getDao();

        String initDay = monthFormat(mYear, mMonth);
        todoHistory = dao.getNotDoneHistoryOfMonth(initDay);
        doneHistory = dao.getDoneHistoryOfMonth(initDay);
        todayDoneHistory = dao.getALlDoneTask(DateToday.getWeekDayOfDate(new Date()));
        todayTodoHistory = dao.getALlTodoTask(DateToday.getWeekDayOfDate(new Date()));

        calendarTodoAdapter = new CalendarTodoAdapter();
        calendarDoneAdapter = new CalendarDoneAdapter();
        recyclerView_todo.setAdapter(calendarTodoAdapter);
        recyclerView_done.setAdapter(calendarDoneAdapter);
        recyclerView_done.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_done.addItemDecoration(new DividerItemDecoration(recyclerView_done.getContext(),DividerItemDecoration.VERTICAL));
        recyclerView_todo.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_todo.addItemDecoration(new DividerItemDecoration(recyclerView_todo.getContext(),DividerItemDecoration.VERTICAL));


        todoHistory.observe(getActivity(), new Observer<List<TaskHistory>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<TaskHistory> taskHistories) {
                Map<String, Integer> map = new HashMap<>();
                for (TaskHistory history : taskHistories) {
                    List<TaskHistory> histories;
                    if (todoOfDay.containsKey(history.getDate())) {
                        histories = todoOfDay.get(history.getDate());
                    } else {
                        histories = new ArrayList<>();
                    }
                    histories.add(history);
                    todoOfDay.put(history.getDate(), histories);
                    map.put(history.getDate(), histories.size());
                }
                mainViewModel.setTodoHistoryMap(map);
            }
        });

        doneHistory.observe(getActivity(), new Observer<List<TaskHistory>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<TaskHistory> taskHistories) {
                Map<String, Integer> map = new HashMap<>();
                for (TaskHistory history : taskHistories) {
                    List<TaskHistory> histories;
                    if (doneOfDay.containsKey(history.getDate())) {
                        histories = doneOfDay.get(history.getDate());
                    } else {
                        histories = new ArrayList<>();
                    }
                    histories.add(history);
                    doneOfDay.put(history.getDate(), histories);
                    map.put(history.getDate(), histories.size());
                }
                mainViewModel.setDoneHistoryMap(map);
            }
        });

        todayTodoHistory.observe(getActivity(), new Observer<List<Task>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Task> tasks) {
                List<TaskHistory> histories = new ArrayList<>();
                for (Task task : tasks) {
                    TaskHistory history = new TaskHistory();
                    history.setName(task.getName());
                    history.setIsDone(task.getIsDone());
                    history.setId(task.getId());
                    history.setDate(sdf.format(new Date()));
                    histories.add(history);
                }
                todoOfDay.put(sdf.format(new Date()), histories);
                calendarTodoAdapter.setTaskHistories(histories);
                calendarTodoAdapter.notifyDataSetChanged();
            }
        });

        todayDoneHistory.observe(getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                List<TaskHistory> histories = new ArrayList<>();
                Log.d("ttt", String.valueOf(tasks.size()));
                for (Task task : tasks) {
                    TaskHistory history = new TaskHistory();
                    history.setName(task.getName());
                    history.setIsDone(task.getIsDone());
                    history.setId(task.getId());
                    history.setDate(sdf.format(new Date()));
                    histories.add(history);
                }
                doneOfDay.put(sdf.format(new Date()), histories);
                calendarDoneAdapter.setTaskHistories(histories);
                calendarDoneAdapter.notifyDataSetChanged();
            }
        });

        mainViewModel.getDoneHistoryMap().observe(getActivity(), new Observer<Map<String, Integer>>() {
            @Override
            public synchronized void onChanged(Map<String, Integer> stringIntegerMap) {
                Map<String, int[]> map = progressBarMap.getValue();
                for (Map.Entry<String, Integer> entry : stringIntegerMap.entrySet()) {
                    int[] progress;
                    if (map.containsKey(entry.getKey())) {
                        progress = map.get(entry.getKey());
                    } else {
                        progress = new int[2];
                    }
                    assert progress != null;
                    progress[1] = entry.getValue();
                    map.put(entry.getKey(), progress);
                }
                progressBarMap.setValue(map);
            }
        });


        mainViewModel.getTodoHistoryMap().observe(getActivity(), new Observer<Map<String, Integer>>() {
            @Override
            public synchronized void onChanged(Map<String, Integer> stringIntegerMap) {
                Map<String, int[]> map = progressBarMap.getValue();
                for (Map.Entry<String, Integer> entry : stringIntegerMap.entrySet()) {
                    int[] progress;
                    if (map.containsKey(entry.getKey())) {
                        progress = map.get(entry.getKey());
                    } else {
                        progress = new int[2];
                    }
                    assert progress != null;
                    progress[0] = entry.getValue();
                    map.put(entry.getKey(), progress);
                }
                progressBarMap.setValue(map);
            }
        });

        progressBarMap.observe(getActivity(), new Observer<Map<String, int[]>>() {
            @Override
            public void onChanged(Map<String, int[]> stringMap) {
                Map<String, Calendar> map = new HashMap<>();
                for (Map.Entry<String, int[]> entry : stringMap.entrySet()) {
                    int[] progress = entry.getValue();
                    double todo_per = (double) progress[0] / ((double) progress[0] + progress[1] + 0.1);
                    double done_per = (double) progress[1] / ((double) progress[0] + progress[1] + 0.1);
                    int todo = (int) (100 * (todo_per));
                    int done = (int) (100 * (done_per));
                    int day = Integer.parseInt(toDate(entry.getKey()));
                    map.put(getSchemeCalendar(mYear, mMonth, day, Color.YELLOW, String.valueOf(todo)).toString(),
                            getSchemeCalendar(mYear, mMonth, day, Color.GRAY, String.valueOf(done)));
                }
                mCalendarView.setSchemeDate(map);
            }
        });




        return root;
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getDay() + " " + month[calendar.getMonth()]);
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mYear = calendar.getYear();
        mMonth = calendar.getMonth();
        mDay = calendar.getDay();
        String date = dateFormat(mYear, mMonth, mDay);

        Log.d("tttt", todoOfDay.toString());

        if (todoOfDay.get(date) != null) {
            calendarTodoAdapter.setTaskHistories(todoOfDay.get(date));
        } else {
            calendarTodoAdapter.setTaskHistories(new ArrayList<>());
        }
        calendarTodoAdapter.notifyDataSetChanged();
        if (doneOfDay.get(date) != null) {
            calendarDoneAdapter.setTaskHistories(doneOfDay.get(date));
        } else {
            calendarDoneAdapter.setTaskHistories(new ArrayList<>());
        }
        calendarDoneAdapter.notifyDataSetChanged();
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    private String dateFormat(int y, int mon, int d) {
        StringBuilder sb = new StringBuilder();
        sb.append(y).append("-");
        if (mon < 10) {
            sb.append(0);
        }
        sb.append(mon).append("-");
        if (d < 10) {
            sb.append(0);
        }
        sb.append(d);
        return sb.toString();
    }

    private String monthFormat(int y, int mon) {
        StringBuilder sb = new StringBuilder();
        sb.append(y).append("-");
        if (mon < 10) {
            sb.append(0);
        }
        return sb.toString();
    }

    private String toDate(String s) {
        return s.substring(s.length() - 2);
    }
}