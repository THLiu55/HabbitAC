package com.example.habitac.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.example.habitac.activity.TaskDetails;
import com.example.habitac.utils.AvatarGetter;
import com.example.habitac.utils.ItemAdapter;

import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements ItemAdapter.ItemViewHolder.ItemClickListener {

    RecyclerView recyclerView; // 滚动组件的 instance
    String[] s1, s2;  // 文本数据的 instance
    Context context;
    List<Integer> image;  // 照片数据的 instance


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
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ImageView avatar = root.findViewById(R.id.imageView);
        image = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            image.add(R.drawable.robot);
        }
        Button refreshAvatar = root.findViewById(R.id.getAvatar);
        refreshAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("BUTTON", "Detected");
                AvatarGetter ag = new AvatarGetter();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Thread", "Created");
                        Bitmap ava = ag.getAvatar("test");
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
        // 构建 adapter
        ItemAdapter myAdapter = new ItemAdapter(context, s1, s2, image, this::click);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        super.onCreate(savedInstanceState);




        buttonAdd = root.findViewById((R.id.button_test1));
        buttonMinus = root.findViewById((R.id.button_test2));
        bar_exp = root.findViewById(R.id.progressbar_exp);
        bar_coin = root.findViewById((R.id.progressbar_coin));


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


    @Override
    public void click(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_navigation_home_to_testTaskDetails2);
    }
}