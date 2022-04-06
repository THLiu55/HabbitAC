package com.example.habitac.utils;

import android.graphics.Bitmap;

public class AvatarObj {
    public String seed;

    AvatarObj(String seed) {
        this.seed = seed;
    }

    public Bitmap getBitmap() {
        Bitmap local = checkLocalAvatar(seed);
        // 先在缓存中检查是否有此头像
        if (local != null) {
            return local;
        }

        AvatarGetter ag = new AvatarGetter();
        saveLocalAvatar(seed);
        return ag.getAvatar(seed);

    }

    public static boolean saveLocalAvatar(String seed) {
        // 保存到本地
        return false;
    }

    public static Bitmap checkLocalAvatar(String seed) {
        return null;
    }
}
