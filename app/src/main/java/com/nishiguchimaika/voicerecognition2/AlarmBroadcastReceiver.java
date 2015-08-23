package com.nishiguchimaika.voicerecognition2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AlarmBroadcastReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            Intent serviceIntent = new Intent(context,MyService.class);
            context.startService(serviceIntent);
        }
}