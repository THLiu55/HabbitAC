package com.example.habitac.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.widget.TextView;


public class AlarmReceiver extends BroadcastReceiver {
    TextView userName;

    @Override
    public void onReceive(Context context, Intent intent) {
        // vibrate 30 s
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(3 * 10000);

        //todo 写上username 和 任务名字
        Notification notification = new Notification.Builder(context)
                .setContentTitle("Hello,Riley")
                .setContentText("It is time to finish your" + " ")
                .build();

        //自动消失
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //显示在状态栏
        notificationManager.notify(10,notification);

        //铃声
        Uri noti = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Ringtone ringtone = RingtoneManager.getRingtone(context,noti);
        ringtone.play();

    }
}