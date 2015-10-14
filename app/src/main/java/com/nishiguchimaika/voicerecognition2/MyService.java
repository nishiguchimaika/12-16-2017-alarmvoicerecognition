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
    int way;

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
        way = pref.getInt("way", 0);
        Log.e("MyService","ok");

        Log.i("onclick", "onclick");
        new Handler().postDelayed(runnable, 0);

        if(way==2){
            Intent new_intent = new Intent(MyService.this, Calculate.class);
            new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(new_intent);
        }else if(way==0) {
            Intent new_intent = new Intent(MyService.this, Recognize.class);
            new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(new_intent);
            Log.e("way","0");
            /*mp = MediaPlayer.create(this, R.raw.sound);
            mp.start();
*/
        }

        return super.onStartCommand(intent, flags, startId);
    }
}

