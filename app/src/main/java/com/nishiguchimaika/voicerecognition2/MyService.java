package com.nishiguchimaika.voicerecognition2;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

public class MyService extends Service {
    //public static MediaPlayer mp;
    SharedPreferences pref;
    int way;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        pref = getSharedPreferences("select", Context.MODE_PRIVATE);
        way = pref.getInt("way", 0);

        if(way==2){
            Intent new_intent = new Intent(MyService.this, Calculate.class);
            new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(new_intent);
        }else if(way==1) {
            Intent new_intent = new Intent(MyService.this, Recognize.class);
            new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(new_intent);
            /*mp = MediaPlayer.create(this, R.raw.sound);
            mp.start();
*/
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        /*if(mp.isPlaying()){
            mp.stop();
        }*/
    }
}

