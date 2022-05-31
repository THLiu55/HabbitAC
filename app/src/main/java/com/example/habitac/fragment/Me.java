package com.example.habitac.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.habitac.R;
import com.example.habitac.View.WheelView;
import com.example.habitac.activity.Login;
import com.example.habitac.database.User;
import com.example.habitac.model.SharedViewModel;

import java.util.Random;

public class Me extends Fragment {
    View root;
    WheelView wheelView;
    Button startNow;
    Random random = new Random();
    private int[] images;
    private String[] textInfo;
    SharedViewModel sharedViewModel;
    TextView drawCoinNumber;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sharedViewModel = new ViewModelProvider(Login.login).get(SharedViewModel.class);
        root = inflater.inflate(R.layout.fragment_shop, container, false);
        wheelView = root.findViewById(R.id.wv_wheel);
        startNow = root.findViewById(R.id.start_now);
        drawCoinNumber = root.findViewById(R.id.draw_coin_num);
        images = wheelView.getImages();
        textInfo = wheelView.getTexts();
        drawCoinNumber.setText(String.valueOf(sharedViewModel.getUser().getCurrentCoin()));

        startNow.setOnClickListener(new View.OnClickListener()  {
            public void onClick(View view) {
                if (sharedViewModel.getUser().getCurrentCoin() < 30) {
                    Toast.makeText(requireActivity(), "Coin not enough!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    User user = sharedViewModel.getUser();
                    user.setCoin(user.getCurrentCoin() - 30);
                    sharedViewModel.setUser(user);
                }
                drawCoinNumber.setText(String.valueOf(sharedViewModel.getUser().getCurrentCoin()));
                int randomNum = random.nextInt(6);
                wheelView.rotate(randomNum);
                startNow.setClickable(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        int index = (randomNum+4)%6;
                        AlertDialog alertDialog = builder
                                .setIcon(images[(randomNum+4)%6])
                                .setTitle("Congratulations")
                                .setMessage("You got this: " + textInfo[index])
                                .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        User user = sharedViewModel.getUser();
                                        Toast.makeText(getContext(), "Set", Toast.LENGTH_SHORT).show();
                                        switch (index) {
                                            case 0:
                                                user.setCoin(user.getCurrentCoin() + 10);
                                                sharedViewModel.setUser(user);
                                                drawCoinNumber.setText(String.valueOf(sharedViewModel.getUser().getCurrentCoin()));
                                                break;
                                            case 1:
                                                user.setCurrentAvatar("nb" + String.valueOf(Math.random() * 100));
                                                sharedViewModel.setUser(user);
                                                break;
                                            case 2:
                                                user.setCurrentAvatar("4");
                                                sharedViewModel.setUser(user);
                                                break;
                                            case 3:
                                                break;
                                            case 4:
                                                user.setCoin(user.getCurrentCoin() + 20);
                                                sharedViewModel.setUser(user);
                                                drawCoinNumber.setText(String.valueOf(sharedViewModel.getUser().getCurrentCoin()));
                                                break;
                                            case 5:
                                                user.setCurrentAvatar("5");
                                                sharedViewModel.setUser(user);
                                                break;
                                        }
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getContext(), "Cancel successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }).setNeutralButton("Exit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getContext(), "Exit successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }).create();
                        alertDialog.show();
                        startNow.setClickable(true);
                    }
                }, 3500);

            }
        });
        return root;
    }
}
