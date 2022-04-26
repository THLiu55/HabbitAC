package com.example.habitac.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.habitac.R;
import com.example.habitac.activity.Login;
import com.example.habitac.adapter.DoneTaskAdapter;
import com.example.habitac.adapter.TodoTaskAdapter;
import com.example.habitac.database.Task;
import com.example.habitac.database.TaskDao;
import com.example.habitac.database.TaskDatabase;
import com.example.habitac.database.TaskHistory;
import com.example.habitac.model.MainViewModel;
import com.example.habitac.model.SharedViewModel;
import com.example.habitac.utils.AvatarGetter;
import com.example.habitac.utils.DateToday;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HomeFragment extends Fragment {
    private final int[] day = {0, 64, 32, 16, 8, 4, 2, 1};

    private RecyclerView recyclerView_todo; // 滚动组件的 instance
    private RecyclerView recyclerView_done;
    private FloatingActionButton addTask;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private TodoTaskAdapter todoTaskAdapter;
    private DoneTaskAdapter doneTaskAdapter;
    private TextView textView_todo, textView_complete;
    private SharedViewModel sharedViewModel;
    private MainViewModel mainViewModel;
    private TextView textView_user_name;
    private TextView textView_level;

    private  LiveData<List<Task>> todoTasksLive;
    private LiveData<List<Task>> doneTasksLive;

    private  LiveData<List<Task>> lastTodoTasksLive;
    private LiveData<List<Task>> lastDoneTasksLive;

    private MutableLiveData<String> userNameLive;
    private MutableLiveData<Integer> expLive;
    private MutableLiveData<Integer> coinLive;
    private MutableLiveData<Integer> levelLive;
    private SimpleDateFormat sdf;
    private String lastLogin;
    private String date_today;


    private int done_cnt;


//    经验条+金币条
    public int currentProgress = 0;
    public int currentCoin = 0;
    private int currentLevel = 1;
    private ProgressBar bar_exp, bar_coin;




    @SuppressLint({"UseCompatLoadingForDrawables", "SimpleDateFormat"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initView(root);


        ImageView avatar = root.findViewById(R.id.imageView);


        avatar.setOnClickListener(new View.OnClickListener() {

            // DELETE THIS
            int avatarCounter = 1;

            @Override
            public void onClick(View view) {
                Log.d("BUTTON", "Detected");
                AvatarGetter ag = new AvatarGetter();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Thread", "Created");
                        avatarCounter ++;
                        Bitmap ava = ag.getAvatar(avatarCounter + "");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                avatar.setImageBitmap(ava);
                            }
                        });
                    }
                }).start();
            }
        });

        lastTodoTasksLive.observe(getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                if (mainViewModel.isRefreshTodo() && !date_today.equals(lastLogin)) {
                    ExecutorService service = Executors.newSingleThreadExecutor();
                    service.execute(new Runnable() {
                        @Override
                        public void run() {
                            TaskDao dao = TaskDatabase.getDatabase(getContext()).getDao();
                            for (Task task : tasks) {
                                TaskHistory history = new TaskHistory();
                                history.setName(task.getName());
                                history.setIsDone(0);
                                history.setDate(lastLogin);
                                history.setId(task.getId());
                                dao.insertTaskHistory(history);
                            }
                            mainViewModel.setRefreshTodo(false);
                        }
                    });
                }
            }
        });

        lastDoneTasksLive.observe(getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                if (mainViewModel.isRefreshDone() && !date_today.equals(lastLogin)) {
                    ExecutorService service = Executors.newSingleThreadExecutor();
                    service.execute(new Runnable() {
                        @Override
                        public void run() {
                            TaskDao dao = TaskDatabase.getDatabase(getContext()).getDao();
                            for (Task task: tasks) {
                                TaskHistory history = new TaskHistory();
                                history.setName(task.getName());
                                history.setIsDone(1);
                                history.setDate(lastLogin);
                                history.setId(task.getId());
                                dao.insertTaskHistory(history);
                                complete2todo(task);
                            }
                            mainViewModel.setRefreshDone(false);
                        }
                    });
                }
            }
        });

        todoTasksLive.observe(requireActivity(), new Observer<List<Task>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Task> tasks) {
                todoTaskAdapter.setTask_todo(tasks);
                todoTaskAdapter.notifyDataSetChanged();

                int todo_cnt = 0;
                for (Task task : tasks) {
                    if (task.getIsDone() == 0) {
                        todo_cnt++;
                        textView_todo.setVisibility(View.VISIBLE);
                    }
                }

                if (todo_cnt == 0) {
                    textView_todo.setVisibility(View.GONE);
                }

                mainViewModel.setTodoTaskAmount(todo_cnt);
            }
        });

        doneTasksLive.observe(requireActivity(), new Observer<List<Task>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Task> tasks) {
                doneTaskAdapter.setTask_done(tasks);
                doneTaskAdapter.notifyDataSetChanged();
                done_cnt = mainViewModel.getDone_cnt();
                int done_cnt_new = 0;
                for (Task task : tasks) {
                    if (task.getIsDone() == 1) {
                        done_cnt_new++;
                        textView_complete.setVisibility(View.VISIBLE);
                    }
                }

                if (done_cnt_new == 0) {
                    textView_complete.setVisibility(View.GONE);
                }
                mainViewModel.setExp(done_cnt_new - done_cnt);
                mainViewModel.setCoin(done_cnt_new - done_cnt);
                mainViewModel.setDone_cnt(done_cnt_new);
            }
        });


        userNameLive.observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView_user_name.setText(s);
            }
        });

        levelLive.observe(requireActivity(), new Observer<Integer>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(Integer integer) {
                textView_level.setText(integer.toString());
            }
        });

        coinLive.observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                bar_coin.setMax(mainViewModel.getLevel().getValue() * 10);
                bar_coin.setProgress(integer);
            }
        });

        expLive.observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.d("bar", String.valueOf(integer));
                bar_exp.setMax(mainViewModel.getLevel().getValue() * 4);
                bar_exp.setProgress(integer);
            }
        });


        // 构建 adapter

        super.onCreate(savedInstanceState);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.action_navigation_home_to_taskDetails);
            }
        });


        return root;
    }

    private void initView(View root) {
        sdf = new SimpleDateFormat("yyyy-MM-dd");

        date_today = sdf.format(new Date());
        if (!requireActivity().getIntent().getStringExtra("param1").isEmpty()) {
            lastLogin = requireActivity().getIntent().getStringExtra("param1");
            Log.d("test", "1");
        } else {
            lastLogin = date_today;
            Log.d("test", "2");
        }

//        date_today = "2022-04-16";
//        lastLogin = "2022-04-11";

        todoTaskAdapter = new TodoTaskAdapter();
        doneTaskAdapter = new DoneTaskAdapter();
        recyclerView_todo = root.findViewById(R.id.recyclerView_todo);
        recyclerView_todo.setAdapter(todoTaskAdapter);
        recyclerView_todo.setLayoutManager(new LinearLayoutManager(context));
        recyclerView_todo.addItemDecoration(new DividerItemDecoration(recyclerView_todo.getContext(),DividerItemDecoration.VERTICAL));
        recyclerView_done = root.findViewById(R.id.recyclerView_done);
        recyclerView_done.setAdapter(doneTaskAdapter);
        recyclerView_done.setLayoutManager(new LinearLayoutManager(context));
        addTask = root.findViewById(R.id.add_task_button);
        context = getActivity();
        TaskDatabase database = TaskDatabase.getDatabase(getContext());
        TaskDao dao = database.getDao();
        try {
            todoTasksLive = dao.getALlTodoTask(day[DateToday.getWeekDayOfDate(sdf.parse(date_today))]);
            doneTasksLive = dao.getALlDoneTask(day[DateToday.getWeekDayOfDate(sdf.parse(date_today))]);
            Log.d("test", lastLogin);
            lastTodoTasksLive = dao.getALlTodoTask(day[DateToday.getWeekDayOfDate(sdf.parse(lastLogin))]);
            lastDoneTasksLive = dao.getALlDoneTask(day[DateToday.getWeekDayOfDate(sdf.parse(lastLogin))]);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        textView_complete = root.findViewById(R.id.home_page_text_compelete);
        textView_todo = root.findViewById(R.id.home_page_text_todo);
        sharedViewModel = new ViewModelProvider(Login.login).get(SharedViewModel.class);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.setUser(sharedViewModel.getUser());
        textView_user_name = root.findViewById(R.id.text_username);
        userNameLive = mainViewModel.getUserName();
        levelLive = mainViewModel.getLevel();
        coinLive = mainViewModel.getCoin();
        expLive = mainViewModel.getExp();
        bar_exp = root.findViewById(R.id.progressbar_exp);
        bar_coin = root.findViewById(R.id.progressbar_coin);
        textView_level = root.findViewById(R.id.level_count);
        done_cnt = mainViewModel.getDone_cnt();
    }

    public static void todo2complete(Task tarTask) {
        Task editTask = new Task(tarTask);
        editTask.setRemainDays(tarTask.getRemainDays() - 1);
        editTask.setIsDone(1);
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public synchronized void run() {
                TaskDatabase taskDatabase = TaskDatabase.getDatabase(context);
                TaskDao dao = taskDatabase.getDao();
                dao.deleteTask(tarTask);
                dao.insertTask(editTask);
            }
        });

    }

    public static void complete2todo(Task tarTask) {
        Task editTask = new Task(tarTask);
        editTask.setIsDone(0);
        editTask.setRemainDays(tarTask.getRemainDays() + 1);
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public synchronized void run() {
                TaskDatabase taskDatabase = TaskDatabase.getDatabase(context);
                TaskDao dao = taskDatabase.getDao();
                dao.deleteTask(tarTask);
                dao.insertTask(editTask);
            }
        });
    }
}