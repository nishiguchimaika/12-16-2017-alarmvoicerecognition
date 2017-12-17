package com.nishiguchimaika.voicerecognition2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Random;


public class Calculate extends AppCompatActivity {
    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    int answer;
    int correct;
    int number;
    MediaPlayer mp;
    SoundPool soundPool;
    int SoundResource = R.raw.soundd4;
    int SoundId;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int level;

    private void init(){
        if(level==0) {
            Random random = new Random();
            int index = random.nextInt(20);
            Random random2 = new Random();
            int index2 = random2.nextInt(20);
            textView4.setText(index + "+" + index2 + "=");
            correct = index + index2;
            answer = 0;
            textView.setText(String.valueOf(answer));
        }else if(level==1){
            Random random = new Random();
            int index = random.nextInt(70);
            Random random2 = new Random();
            int index2 = random2.nextInt(70);
            textView4.setText(index + "+" + index2 + "=");
            correct = index + index2;
            answer = 0;
            textView.setText(String.valueOf(answer));
        }else if(level==2){
            Random random = new Random();
            int index = random.nextInt(150);
            Random random2 = new Random();
            int index2 = random2.nextInt(150);
            textView4.setText(index + "+" + index2 + "=");
            correct = index + index2;
            answer = 0;
            textView.setText(String.valueOf(answer));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub3);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        answer = 0;
        correct = 0;
        number = 0;
        mp = MediaPlayer.create(this,R.raw.sound);
        mp.setVolume(150.0f,150.0f);
        AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int vol = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        manager.setStreamVolume(AudioManager.STREAM_MUSIC, vol/2,0);

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        SoundId = soundPool.load(this,SoundResource, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener(){
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status){
            }
        });
        mp.start();
        mp.setLooping(true);
        pref = getSharedPreferences("select", Context.MODE_PRIVATE);
        editor = pref.edit();
        level = pref.getInt("level", 0);
        init();
    }

    public void go(View v){
        if(correct == answer){
            soundPool.stop(SoundId);
            textView2.setText("正解！");
            number = number + 1;
            if(number == 15){
                mp.stop();
                mp.release();
                editor.putInt("ala", 1);
                editor.apply();
                Intent intent = new Intent(Calculate.this, MainActivity.class);
                startActivity(intent);
                soundPool.unload(SoundId);
                soundPool.release();
            }
        }else{
            textView2.setText("間違い！");
            number = 0;
            soundPool.play(SoundId,1,1,0,0,0);
        }
        textView3.setText("あと"+(15-number)+"問！");
        init();
    }

    public void one(View v){
        answer = answer*10+1;
        textView.setText(String.valueOf(answer));
    }
    public void two(View v){
        answer = answer*10+2;
        textView.setText(String.valueOf(answer));
    }
    public void three(View v){
        answer = answer*10+3;
        textView.setText(String.valueOf(answer));
    }
    public void four(View v){
        answer = answer*10+4;
        textView.setText(String.valueOf(answer));
    }
    public void five(View v){
        answer = answer*10+5;
        textView.setText(String.valueOf(answer));
    }
    public void six(View v){
        answer = answer*10+6;
        textView.setText(String.valueOf(answer));
    }
    public void seven(View v){
        answer = answer*10+7;
        textView.setText(String.valueOf(answer));
    }
    public void eight(View v){
        answer = answer*10+8;
        textView.setText(String.valueOf(answer));
    }
    public void nine(View v){
        answer = answer*10+9;
        textView.setText(String.valueOf(answer));
    }
    public void zero(View v){
        answer = answer*10;
        textView.setText(String.valueOf(answer));
    }
    public void clear(View v){
        answer = 0;
        textView.setText(String.valueOf(answer));
    }


    @Override
    protected void onStop(){
        super.onStop();
        Log.e("CalculateStop", "okay");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.e("CalculateDestroy", "okay");
    }
}
