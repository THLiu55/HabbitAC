package com.example.habitac.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.habitac.R;
import com.example.habitac.database.TaskDone;
import com.example.habitac.database.TaskTodo;
import com.example.habitac.database.TasksDao;
import com.example.habitac.database.TasksDatabase;
import com.example.habitac.utils.AvatarGetter;
import com.example.habitac.utils.DoneTaskAdapter;
import com.example.habitac.utils.TodoTaskAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView_todo; // 滚动组件的 instance
    private RecyclerView recyclerView_done;
    private FloatingActionButton addTask;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private LiveData<List<TaskTodo>> todoTasksLive;
    private LiveData<List<TaskDone>> doneTasksLive;
    private TodoTaskAdapter todoTaskAdapter;
    private DoneTaskAdapter doneTaskAdapter;
    private TextView textView_todo, textView_complete;


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
        Button refreshAvatar = root.findViewById(R.id.getAvatar);


        refreshAvatar.setOnClickListener(new View.OnClickListener() {

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
        todoTasksLive.observe(requireActivity(), new Observer<List<TaskTodo>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<TaskTodo> taskTodos) {
                todoTaskAdapter.setTasks_todo(taskTodos);
                todoTaskAdapter.notifyDataSetChanged();
                if (taskTodos.size() == 0) {
                    textView_todo.setVisibility(View.GONE);
                } else {
                    textView_todo.setVisibility(View.VISIBLE);
                }
            }
        });

        doneTasksLive.observe(requireActivity(), new Observer<List<TaskDone>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<TaskDone> taskDones) {
                doneTaskAdapter.setTasks_done(taskDones);
                doneTaskAdapter.notifyDataSetChanged();
                if (taskDones.size() == 0) {
                    textView_complete.setVisibility(View.INVISIBLE);
                } else {
                    textView_complete.setVisibility(View.VISIBLE);
                }
            }
        });

        // 构建 adapter

        super.onCreate(savedInstanceState);




//        buttonAdd = root.findViewById((R.id.button_test1));
//        buttonMinus = root.findViewById((R.id.button_test2));
//        bar_exp = root.findViewById(R.id.progressbar_exp);
//        bar_coin = root.findViewById((R.id.progressbar_coin));


//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                bar_coin.setMax(500);
//            }
//        });

//        buttonAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        bar_exp.setMax(getMaxExperience(currentLevel));
//                    }
//                });
//
//                currentProgress += 25;
//
//                if (bar_exp.getProgress() < getMaxExperience(currentLevel)) {
//                    currentCoin += 10;
//                } else {
//                    currentProgress = 0;
//                    currentLevel += 1;
//                    currentCoin += 100;
//
//                }
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        bar_exp.setProgress(currentProgress);
//                        bar_coin.setProgress(currentCoin);
//
//                    }
//                });
//            }
//
//            private int getMaxExperience(int currentLevel) {
//                if (currentLevel <= 4 && currentLevel >= 1) {
//                    return currentLevel * 50;
//                } else if (currentLevel <= 10 && currentLevel >= 5) {
//                    return 300;
//                } else {
//                    return 500;
//                }
//            }
//        });


//        buttonMinus.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        bar_exp.setMax(getMaxExperience(currentLevel));
//
//                    }
//                });
//                if (bar_exp.getProgress() > 0) {
//                    currentProgress -= 25;
//                    if (currentCoin >= 20) {
//                        currentCoin -= 20;
//                    } else {
//                        currentCoin = 0;
//                    }
//                } else if (bar_exp.getProgress() == 0 && currentLevel > 1) {
//                    currentLevel -= 1;
//                    currentProgress = getMaxExperience(currentLevel);
//
//                    if (currentCoin >= 20) {
//                        currentCoin -= 20;
//                    } else {
//                        currentCoin = 0;
//                    }
//                } else {
//                    if (currentCoin > 0) {
//                        currentCoin = 0;
//                    }
//                }
//
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        bar_exp.setProgress(currentProgress);
//                        bar_coin.setProgress(currentCoin);
//
//                    }
//                });
//
//            }
//
//            private int getMaxExperience(int currentLevel) {
//                if (currentLevel <= 4 && currentLevel >= 1) {
//                    return currentLevel * 50;
//                } else if (currentLevel <= 10 && currentLevel >= 5) {
//                    return 300;
//                } else {
//                    return 500;
//                }
//            }
//        });


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
        recyclerView_todo.setLayoutManager(new LinearLayoutManager(context));
        recyclerView_todo.addItemDecoration(new DividerItemDecoration(recyclerView_todo.getContext(),DividerItemDecoration.VERTICAL
        ));
//        recyclerView_todo.addItemDecoration(new MyDecoration());
        recyclerView_todo.setAdapter(todoTaskAdapter);
        recyclerView_done = root.findViewById(R.id.recyclerView_done);
        recyclerView_done.setAdapter(doneTaskAdapter);
        recyclerView_done.setLayoutManager(new LinearLayoutManager(context));
        addTask = root.findViewById(R.id.add_task_button);
        context = getActivity();
        TasksDatabase database = TasksDatabase.getDatabase(getContext());
        TasksDao dao = database.getDao();
        todoTasksLive = dao.getALlTodo();
        doneTasksLive = dao.getAllDone();
        textView_complete = root.findViewById(R.id.home_page_text_compelete);
        textView_todo = root.findViewById(R.id.home_page_text_todo);
    }

    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0,0,0,getResources().getDimensionPixelOffset(R.dimen.dividerHeight));
        }
    }

    public static void deleteTask(int id) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                TasksDatabase database = TasksDatabase.getDatabase(context);
                TasksDao dao = database.getDao();
                TaskDone taskDone = new TaskDone();
                taskDone.setId(id);
                dao.deleteDone(taskDone);
            }
        });
    }

    public static void todo_to_complete(int id) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                TasksDatabase database = TasksDatabase.getDatabase(context);
                TasksDao dao = database.getDao();
                TaskTodo taskTodo = dao.getToDo(id);
                TaskDone taskDone = new TaskDone();
                taskDone.setTaskName(taskTodo.getTaskName());
                taskDone.setId(taskTodo.getId());
                dao.deleteTodo(taskTodo);
                dao.insertDone(taskDone);
            }
        });
    }

    public static void complete_to_todo(TaskDone taskDone) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                TasksDatabase database = TasksDatabase.getDatabase(context);
                TasksDao dao = database.getDao();
                TaskTodo taskTodo = new TaskTodo();
                taskTodo.setTaskName(taskDone.getTaskName());
                dao.insertTodo(taskTodo);
                dao.deleteDone(taskDone);
            }
        });
    }
}