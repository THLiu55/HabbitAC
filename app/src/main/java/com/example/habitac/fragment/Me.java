package com.example.habitac.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habitac.R;
import com.example.habitac.activity.Login;
import com.example.habitac.database.Item;
import com.example.habitac.database.User;
import com.example.habitac.model.MainViewModel;
import com.example.habitac.model.SharedViewModel;
import com.example.habitac.utils.AvatarGetter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Me#} factory method to
 * create an instance of this fragment.
 */
public class Me extends Fragment{

    Button drawBuy, drawDrop, drawEquip;

    ImageView imageView_avatar, cover_avatar, questionMark;
    String avatarId;
    SharedViewModel sharedViewModel;
    User user;
    TextView myCoin;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shop, container, false);
        drawBuy = root.findViewById(R.id.draw_buy);
        drawDrop = root.findViewById(R.id.draw_drop);
        drawEquip = root.findViewById(R.id.draw_equip);

        imageView_avatar = root.findViewById(R.id.draw_avatar);
        cover_avatar = root.findViewById(R.id.cover_avatar);
        questionMark = root.findViewById(R.id.que);
        myCoin = root.findViewById(R.id.my_coin);
        sharedViewModel = new ViewModelProvider(Login.login).get(SharedViewModel.class);
        user = sharedViewModel.getUser();
        avatarId = "";
        myCoin.setText(String.valueOf(user.getCurrentCoin()));
        User user1 = new User();
        drawBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getCurrentCoin() < 100) {
                    Toast.makeText(requireActivity(), "coin not enough", Toast.LENGTH_SHORT).show();
                    return;
                }
                drawBuy.setVisibility(View.INVISIBLE);
                user.setCoin(user.getCurrentCoin() - 100);
                user.update(user.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            myCoin.setText(String.valueOf(user.getCurrentCoin()));
                            drawBuy.setClickable(false);
                            AvatarGetter ag = new AvatarGetter();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    avatarId = randomWord();
                                    Bitmap ava = ag.getAvatar(avatarId);
                                    requireActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            questionMark.setVisibility(View.GONE);
                                            cover_avatar.setVisibility(View.GONE);
                                            imageView_avatar.setImageBitmap(ava);
                                            imageView_avatar.setVisibility(View.VISIBLE);
                                            drawDrop.setVisibility(View.VISIBLE);
                                            drawEquip.setVisibility(View.VISIBLE);
                                        }
                                    });
                                }
                            }).start();
                        }
                        return;
                    }

                });
            }
        });

        drawEquip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("equip", "here");
                user1.setCoin(user.getCurrentCoin());
                Log.d("mony", String.valueOf(user1.getCurrentCoin()));
                user1.setPassword(user.getPassword());
                user1.setProgress(user.getCurrentExp());
                user1.setUser_name(user.getUser_name());
                user1.setEmail(user.getEmail());
                user1.setCurrentRank(user.getCurrentRank());
                user1.setHighestRank(user.getHighestRank());
                user1.setCurrentAvatar(avatarId);
                sharedViewModel.setUser(user1);
                user1.update(user.getObjectId(), new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            cover_avatar.setVisibility(View.VISIBLE);
                            questionMark.setVisibility(View.VISIBLE);
                            drawBuy.setVisibility(View.VISIBLE);
                            drawBuy.setClickable(true);
                            drawDrop.setVisibility(View.GONE);
                            drawEquip.setVisibility(View.GONE);
                            imageView_avatar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });

        drawDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cover_avatar.setVisibility(View.VISIBLE);
                questionMark.setVisibility(View.VISIBLE);
                drawBuy.setVisibility(View.VISIBLE);
                drawBuy.setClickable(true);
                drawDrop.setVisibility(View.GONE);
                drawEquip.setVisibility(View.GONE);
                imageView_avatar.setVisibility(View.INVISIBLE);
            }
        });

        return root;
    }

    public void getRandomAvatar(ImageView avatar) {
        AvatarGetter ag = new AvatarGetter();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap ava = ag.getAvatar(randomWord());
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        avatar.setImageBitmap(ava);
                    }
                });
            }
        }).start();
    }

    private String randomWord() {
        int l = 5 + (int) (Math.random() * 9);
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < l; i++) {
            sb.append((char) randomChar());
        }
        return sb.toString();
    }

    private byte randomChar() {
        int flag = (int) (Math.random() * 2);
        byte bt = (byte) (Math.random() * 26);
        if (flag == 0) {
            return (byte) (65 + bt);
        } else {
            return (byte) (97 + bt);
        }
    }



}