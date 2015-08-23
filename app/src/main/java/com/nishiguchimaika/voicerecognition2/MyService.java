package com.nishiguchimaika.voicerecognition2;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService extends Service {
  //  final static String TAG = "MyService";
    MediaPlayer mp2;
 //   private PendingIntent voiceintent;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mp2 = MediaPlayer.create(this, R.raw.sound);
        mp2.start();

        Intent new_intent = new Intent(MyService.this, Recognize.class);
        new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(new_intent);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mp2.isPlaying()){
            mp2.stop();
        }
    }
}
