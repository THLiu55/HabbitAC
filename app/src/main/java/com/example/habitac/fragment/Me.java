package com.example.habitac.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.habitac.R;
import com.example.habitac.View.WheelView;

import java.util.Random;

public class Me extends Fragment {
    View root;
    WheelView wheelView;
    ImageView avatar;
    Random random = new Random();
    private int[] images;
    private String[] textInfo;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_shop, container, false);
        wheelView = root.findViewById(R.id.wv_wheel);
        avatar = root.findViewById(R.id.iv_play);
        images = wheelView.getImages();
        textInfo = wheelView.getTexts();
//        onClick(avatar);
        avatar.setOnClickListener(new View.OnClickListener()  {
            public void onClick(View view) {
                int randomNum = random.nextInt(6);
                wheelView.rotate(randomNum);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                AlertDialog alertDialog = builder
                        .setIcon(images[randomNum+1])
                        .setTitle("Congratulations")
                        .setMessage("You got this: " + textInfo[randomNum+1])
                        .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(), "Set", Toast.LENGTH_SHORT).show();
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
            }
        });
        return root;
    }
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertDialog alertDialog = builder
                .setIcon(R.drawable.random_avatar)
                .setTitle("Congratulations")
                .setMessage("You got this new avatar")
                .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Set", Toast.LENGTH_SHORT).show();
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
    }

}
