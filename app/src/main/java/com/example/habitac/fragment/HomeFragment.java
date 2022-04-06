package com.example.habitac.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
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
import com.example.habitac.database.Task;
import com.example.habitac.database.TaskDao;
import com.example.habitac.database.TaskDatabase;
import com.example.habitac.utils.AvatarGetter;
import com.example.habitac.utils.TaskAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.widget.ProgressBar;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView; // 滚动组件的 instance
    FloatingActionButton addTask;
    String[] s1, s2;  // 文本数据的 instance
    static Context context;
    List<Integer> image;  // 照片数据的 instance
    LiveData<List<Task>> tasksLive;
    TaskDatabase database;
    TaskDao dao;


    //经验条+金币条
    public int currentProgress = 0;
    public int currentCoin = 0;
    private int currentLevel = 1;
    private ProgressBar bar_exp, bar_coin;
    private Button buttonAdd, buttonMinus;




    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String userName;

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ImageView avatar = root.findViewById(R.id.imageView);
        image = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            image.add(R.drawable.robot);
        }
        image.add(R.drawable.white);
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

        s1 = getResources().getStringArray(R.array.habit_name);
        s2 = getResources().getStringArray(R.array.description);


        recyclerView = root.findViewById(R.id.recycle_view);
        context = getActivity();

        // adapter
        TaskAdapter taskAdapter = new TaskAdapter();
        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

         // 初始化 database
        database = TaskDatabase.getDatabase(getContext());
        dao = database.getDao();
        tasksLive = dao.getAllLive();
        tasksLive.observe(requireActivity(), new Observer<List<Task>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Task> tasks) {
                taskAdapter.setTasks(tasks);
                taskAdapter.notifyDataSetChanged();
            }
        });
        // 构建 adapter

        super.onCreate(savedInstanceState);




        buttonAdd = root.findViewById((R.id.button_test1));
        buttonMinus = root.findViewById((R.id.button_test2));
        bar_exp = root.findViewById(R.id.progressbar_exp);
        bar_coin = root.findViewById((R.id.progressbar_coin));
        addTask = root.findViewById(R.id.add_task_button);


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bar_coin.setMax(500);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bar_exp.setMax(getMaxExperience(currentLevel));
                    }
                });

                currentProgress += 25;

                if (bar_exp.getProgress() < getMaxExperience(currentLevel)) {
                    currentCoin += 10;
                } else {
                    currentProgress = 0;
                    currentLevel += 1;
                    currentCoin += 100;

                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bar_exp.setProgress(currentProgress);
                        bar_coin.setProgress(currentCoin);

                    }
                });
            }

            private int getMaxExperience(int currentLevel) {
                if (currentLevel <= 4 && currentLevel >= 1) {
                    return currentLevel * 50;
                } else if (currentLevel <= 10 && currentLevel >= 5) {
                    return 300;
                } else {
                    return 500;
                }
            }
        });

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.action_navigation_home_to_taskDetails);
            }
        });


        buttonMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bar_exp.setMax(getMaxExperience(currentLevel));

                    }
                });
                if (bar_exp.getProgress() > 0) {
                    currentProgress -= 25;
                    if (currentCoin >= 20) {
                        currentCoin -= 20;
                    } else {
                        currentCoin = 0;
                    }
                } else if (bar_exp.getProgress() == 0 && currentLevel > 1) {
                    currentLevel -= 1;
                    currentProgress = getMaxExperience(currentLevel);

                    if (currentCoin >= 20) {
                        currentCoin -= 20;
                    } else {
                        currentCoin = 0;
                    }
                } else {
                    if (currentCoin > 0) {
                        currentCoin = 0;
                    }
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bar_exp.setProgress(currentProgress);
                        bar_coin.setProgress(currentCoin);

                    }
                });

            }

            private int getMaxExperience(int currentLevel) {
                if (currentLevel <= 4 && currentLevel >= 1) {
                    return currentLevel * 50;
                } else if (currentLevel <= 10 && currentLevel >= 5) {
                    return 300;
                } else {
                    return 500;
                }
            }
        });


        return root;
    }

    public static void deleteTask(int id) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                TaskDatabase database = TaskDatabase.getDatabase(context);
                TaskDao dao = database.getDao();
                Task task = new Task();
                task.setId(id);
                dao.delete(task);
            }
        });
    }
}