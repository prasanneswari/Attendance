package com.jts.root.attendance;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.net.DatagramPacket;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class Notifications extends Service {
    private Resources resources;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        resources=getResources();
        super.onCreate();
        mTimer=new Timer();
        mTimer.schedule(timerTask,2000,2*1000);
    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        try {

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return super.onStartCommand(intent,flags,startId);
    }
    private Timer mTimer;
    TimerTask timerTask=new TimerTask() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void run() {
            System.out.print("running the app");
            notifys();
        }
    };
    public void onDestory(){
        try{
            mTimer.cancel();
            timerTask.cancel();
        }catch (Exception e){
            e.printStackTrace();
        }
        Intent intent=new Intent("com.jts.root.attendance");
        intent.putExtra("yourvalue","torestore");
        sendBroadcast(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void notifys(){
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("RSS pullservice");

        Intent myintent=new Intent(Intent.ACTION_VIEW,Uri.parse(""));

        @SuppressLint("WrongConstant") PendingIntent pendingIntent=PendingIntent.getActivity(getBaseContext(),0,myintent,Intent.FLAG_ACTIVITY_NEW_TASK);
        Context context=getApplicationContext();
        Bitmap bitmapI=BitmapFactory.decodeResource(resources,R.mipmap.ic_launcher);

        Notification.Builder builder;
        builder=new Notification.Builder(context);
                builder.setTicker("Attendance")
                .setContentTitle("New Text from :"+"Attendance")
                .setContentText("SUBSCRIBE FOR LAETST CODE.NO DEPRECATED CODE")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(bitmapI)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        Notification notification=builder.build();
        NotificationManager notificationManager=(NotificationManager)getSystemService(context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1,notification);
        }
    }
}

