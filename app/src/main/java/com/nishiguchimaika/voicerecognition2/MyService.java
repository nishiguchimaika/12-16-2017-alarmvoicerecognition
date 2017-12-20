package com.nishiguchimaika.voicerecognition2;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

public class MyService extends Service {
    SharedPreferences pref;
    int sorm;

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.i("run", "run");
            final PowerManager pm = (PowerManager)getSystemService(POWER_SERVICE);
            PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "TimerExample");
            wakeLock.acquire();
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        pref = getSharedPreferences("select", Context.MODE_PRIVATE);
        sorm = pref.getInt("sorm", 0);
        Log.e("MyService","ok");

        new Handler().postDelayed(runnable, 0);

        if(sorm == 1){
            Intent newIntent = new Intent(MyService.this, Calculate.class);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newIntent);
            Log.e("sorm","1");
        }else if(sorm == 0) {
            Intent newIntent = new Intent(MyService.this, Recognize.class);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newIntent);
            Log.e("sorm","0");
        }

        return super.onStartCommand(intent, flags, startId);
    }
}

