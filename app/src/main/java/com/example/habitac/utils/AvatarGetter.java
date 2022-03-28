package com.example.habitac.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.habitac.activity.Main;
import com.example.habitac.fragment.HomeFragment;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.BindException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class AvatarGetter {

    public Bitmap getAvatar(String seed) {
        String full_url = "https://robohash.org/" + seed + ".png";
        Bitmap bit = null;
        Log.d("AVATAR GETTER", "START");
        try {
            URL url = new URL(full_url);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            int code = connection.getResponseCode();
            Log.d(code+" Get",getClass().getSimpleName());
            if (code == 200) {
                InputStream inputStream = connection.getInputStream();
                bit = BitmapFactory.decodeStream(inputStream);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return bit;

    }

}