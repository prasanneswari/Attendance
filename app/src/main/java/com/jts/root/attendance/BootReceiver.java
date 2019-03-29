package com.jts.root.attendance;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

/**
 * Created by root on 4/7/18.
 */

public class BootReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent){
        System.out.print("service stoped");
        ComponentName comp = new ComponentName(context.getPackageName(), Notifications.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);

       // context.startService(new Intent(context,Notifications.class));
    }
}
    // Start the service, keeping the device awake while it is launching.
