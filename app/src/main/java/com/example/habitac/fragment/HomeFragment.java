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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.habitac.R;
import com.example.habitac.activity.Login;
import com.example.habitac.adapter.DoneTaskAdapter;
import com.example.habitac.adapter.TodoTaskAdapter;
import com.example.habitac.database.Task;
import com.example.habitac.database.TaskDao;
import com.example.habitac.database.TaskDatabase;
import com.example.habitac.model.MainViewModel;
import com.example.habitac.model.SharedViewModel;
import com.example.habitac.utils.AvatarGetter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.widget.ProgressBar;
import android.widget.TextView;

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
    private LiveData<List<Task>> todoTasksLive;
    private LiveData<List<Task>> doneTasksLive;
    private TodoTaskAdapter todoTaskAdapter;
    private DoneTaskAdapter doneTaskAdapter;
    private TextView textView_todo, textView_complete;
    private SharedViewModel sharedViewModel;
    private MainViewModel mainViewModel;
    private TextView textView_user_name;

    private TextView textView_todo_amount;

    private MutableLiveData<Integer> todo_task_amount_live;


//    经验条+金币条
    public int currentProgress = 0;
    public int currentCoin = 0;
    private int currentLevel = 1;
    private ProgressBar bar_exp, bar_coin;
//    private Button buttonAdd, buttonMinus;




    @SuppressLint("UseCompatLoadingForDrawables")
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

         // 初始化 database
        todoTasksLive.observe(requireActivity(), new Observer<List<Task>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Task> tasks) {
                todoTaskAdapter.setTask_todo(tasks);
                todoTaskAdapter.notifyDataSetChanged();

                int todo_cnt = 0;
                int all_task = 0;
                for (Task task : tasks) {
                    if (task.getIsDone() == 0) {
                        todo_cnt++;
                        textView_todo.setVisibility(View.VISIBLE);
                    }
                    all_task++;
                }
                if (todo_cnt == 0) {
                    Log.d("123", "no todo");
                    textView_todo.setVisibility(View.GONE);
                }
                mainViewModel.setTodoTaskAmount(todo_cnt);
                mainViewModel.setTaskAmount(all_task);
                todo_task_amount_live = mainViewModel.getTodoTaskAmount();
            }
        });

        doneTasksLive.observe(requireActivity(), new Observer<List<Task>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Task> tasks) {
                doneTaskAdapter.setTask_done(tasks);
                doneTaskAdapter.notifyDataSetChanged();

                int done_cnt = 0;
                for (Task task : tasks) {
                    if (task.getIsDone() == 1) {
                        done_cnt++;
                        textView_complete.setVisibility(View.VISIBLE);
                    }
                }

                if (done_cnt == 0) {
                    Log.d("123", "no done");
                    textView_complete.setVisibility(View.GONE);
                } else {
                    Log.d("123", "have done");
                }
            }
        });

//        todo_task_amount_live.observe(requireActivity(), new Observer<Integer>() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onChanged(Integer integer) {
//                textView_todo_amount.setText(todo_task_amount_live.getValue().toString());
//            }
//        });

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
        todoTaskAdapter = new TodoTaskAdapter();
        doneTaskAdapter = new DoneTaskAdapter();
        recyclerView_todo = root.findViewById(R.id.recyclerView_todo);
        recyclerView_todo.setAdapter(todoTaskAdapter);
        recyclerView_todo.setLayoutManager(new LinearLayoutManager(context));
        recyclerView_done = root.findViewById(R.id.recyclerView_done);
        recyclerView_done.setAdapter(doneTaskAdapter);
        recyclerView_done.setLayoutManager(new LinearLayoutManager(context));
        addTask = root.findViewById(R.id.add_task_button);
        context = getActivity();
        TaskDatabase database = TaskDatabase.getDatabase(getContext());
        TaskDao dao = database.getDao();
        todoTasksLive = dao.getALlTodoTask(day[1]);
        doneTasksLive = dao.getALlDoneTask(day[1]);
        textView_complete = root.findViewById(R.id.home_page_text_compelete);
        textView_todo = root.findViewById(R.id.home_page_text_todo);
        sharedViewModel = new ViewModelProvider(Login.login).get(SharedViewModel.class);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.setUser(sharedViewModel.getUser());
        Log.d("userInfo", String.valueOf(mainViewModel.getUserName().getValue().toString()));
        Log.d("userInfo", String.valueOf(mainViewModel.getLevel().getValue().toString()));
        Log.d("userInfo", String.valueOf(mainViewModel.getCoin().getValue().toString()));
        Log.d("userInfo", String.valueOf(mainViewModel.getExp().getValue().toString()));
//        Log.d("userInfo", String.valueOf(mainViewModel.getTaskAmount()));
        textView_user_name = root.findViewById(R.id.text_username);

        textView_user_name.setText(getActivity().getIntent().getStringExtra("param1"));

        textView_todo_amount = root.findViewById(R.id.text_number_of_tasks);
    }

    public static void todo2complete(Task tarTask) {
        Task editTask = new Task(tarTask);
        editTask.setRemainDays(tarTask.getRemainDays() - 1);
        editTask.setIsDone(1);
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
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
            public void run() {
                TaskDatabase taskDatabase = TaskDatabase.getDatabase(context);
                TaskDao dao = taskDatabase.getDao();
                dao.deleteTask(tarTask);
                dao.insertTask(editTask);
            }
        });
    }
}