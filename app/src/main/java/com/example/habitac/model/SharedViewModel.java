package com.example.habitac.model;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.habitac.database.User;
import com.example.habitac.utils.AvatarGetter;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class SharedViewModel extends ViewModel {
    User user;
    Bitmap curAvatar;
    String seed;
    boolean localAvatar;

    public void setUser(User user) {
        this.user = user;
        this.user.update(user.getObjectId(), new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){

                }else{
                }
            }

        });
    }

    public User getUser() {
        return user;
    }

    public void setCurAvatar(Bitmap curAvatar) {
        this.curAvatar = curAvatar;
    }

    public Bitmap getCurAvatar() {
        return curAvatar;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getSeed() {
        return seed;
    }

    public void setLocalAvatar(boolean localAvatar) {
        this.localAvatar = localAvatar;
    }

    public boolean isLocalAvatar() {
        return localAvatar;
    }
}
