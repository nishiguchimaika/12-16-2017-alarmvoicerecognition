package com.nishiguchimaika.voicerecognition2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PowerManager;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Calendar;
import java.util.Locale;

public class Timer extends Activity implements TextToSpeech.OnInitListener {

    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int minute = calendar.get(Calendar.MINUTE);
    ImageButton btnStop;
    int q;
    int a;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    int[] imageId;
    int[] imagenumber;
    private TextToSpeech mTextToSpeech;
    private SharedPreferences pref;
    int lan;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub2);

        pref = getSharedPreferences("select", Context.MODE_PRIVATE);
        lan = pref.getInt("language", 0);

        mTextToSpeech = new TextToSpeech(this, this);

        imageId = new int[11];
        imageId[0] = R.drawable.zero;
        imageId[1] = R.drawable.one;
        imageId[2] = R.drawable.two;
        imageId[3] = R.drawable.three;
        imageId[4] = R.drawable.four;
        imageId[5] = R.drawable.five;
        imageId[6] = R.drawable.six;
        imageId[7] = R.drawable.seven;
        imageId[8] = R.drawable.eight;
        imageId[9] = R.drawable.nine;
        imageId[10] = R.drawable.betw;
        imagenumber = new int[6];
        q = 0;
        Intent timerIntent = getIntent();
        q = timerIntent.getIntExtra("time", 0);
        Log.e("q", String.valueOf(q));
        a = 0;

        final CounterClass timer = new CounterClass(q * 6000, 1000);
        if(q != 0){
            timer.start();
        }

        btnStop = (ImageButton) findViewById(R.id.btnStop);

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        imageView5 = (ImageView) findViewById(R.id.imageView5);
        imageView6 = (ImageView) findViewById(R.id.imageView6);
        imageView7 = (ImageView) findViewById(R.id.imageView7);
        imageView8 = (ImageView) findViewById(R.id.imageView8);

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                Intent intent = new Intent(Timer.this, MainActivity.class);
                startActivity(intent);
        }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                speechText();
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTextToSpeech != null) {
            mTextToSpeech.shutdown();
        }
    }

    @Override
    public void onInit(int status) {
        if(lan == 0){
            mTextToSpeech.setLanguage(Locale.JAPANESE);
        }else if(lan == 1){
            mTextToSpeech.setLanguage(Locale.ENGLISH);
        }
    }

    private void speechText() {
        if (!TextUtils.isEmpty((hour + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3]) / 60) + "o'clock" + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3]) % 60 + "minutes")) {
            if (mTextToSpeech.isSpeaking()) {
                mTextToSpeech.stop();
            }
           /* HashMap<String, String> map = new HashMap<String, String>();
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "messageID");*/
            Log.d("speechText", (hour + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3]) / 60) + "o'clock" + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3]) % 60 + "minutes");
            mTextToSpeech.speak((hour + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3]) / 60) + "じ" + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3]+1) % 60 + "ふんに起こします", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            //TODO Auto_generated constructor stub
            setTime(millisInFuture);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            setTime(millisUntilFinished);
            imageView1.setImageResource(imageId[imagenumber[0]]);
            imageView2.setImageResource(imageId[imagenumber[1]]);
            imageView4.setImageResource(imageId[imagenumber[2]]);
            imageView5.setImageResource(imageId[imagenumber[3]]);
            imageView7.setImageResource(imageId[imagenumber[4]]);
            imageView8.setImageResource(imageId[imagenumber[5]]);
        }

        private void setTime(long millisUntilFinished) {
            imagenumber[0] = (int) millisUntilFinished / 1000 / 60 / 60 / 10;
            imagenumber[1] = (int) millisUntilFinished / 1000 / 60 / 60 % 10;
            imagenumber[2] = (int) (millisUntilFinished / 1000 / 60 - (10 * imagenumber[0] + imagenumber[1]) * 60) / 10;
            imagenumber[3] = (int) (millisUntilFinished / 1000 / 60 - (10 * imagenumber[0] + imagenumber[1]) * 60) % 10;
            imagenumber[4] = (int) (millisUntilFinished / 1000 - (10 * imagenumber[2] + imagenumber[3]) * 60 - (10 * imagenumber[0] + imagenumber[1]) * 3600) / 10;
            imagenumber[5] = (int) (millisUntilFinished / 1000 - (10 * imagenumber[2] + imagenumber[3]) * 60 - (10 * imagenumber[0] + imagenumber[1]) * 3600) % 10;
        }

        @Override
        public void onFinish() {
            Log.i("onclick", "onclick");
            new Handler().postDelayed(runnable, 0);
            Log.i("音声認識","again");
            Intent new_intent = new Intent(Timer.this, Recognize.class);
            a = 1;
            new_intent.putExtra("again", a);
            startActivity(new_intent);
        }
    }
 }
