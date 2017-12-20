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
import android.widget.Button;
import android.widget.ImageView;

import java.util.Calendar;
import java.util.Locale;

public class Timer extends Activity implements TextToSpeech.OnInitListener {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int minute = calendar.get(Calendar.MINUTE);
    Button btnStop;
    int q;
    int again;
    ImageView[] mImageViews;
    int[] imageId;
    int[] imagenumber;
    int lan;
    private TextToSpeech mTextToSpeech;
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
        editor = pref.edit();
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
        again = 0;
        lan = 0;
        final CounterClass timer = new CounterClass(q * 60000, 1000);
        if(q != 0){
            timer.start();
        }
        btnStop = (Button) findViewById(R.id.btnStop);
        mImageViews[0] = (ImageView) findViewById(R.id.imageView1);
        mImageViews[1] = (ImageView) findViewById(R.id.imageView2);
        mImageViews[2] = (ImageView) findViewById(R.id.imageView3);
        mImageViews[3] = (ImageView) findViewById(R.id.imageView4);
        mImageViews[4] = (ImageView) findViewById(R.id.imageView5);
        mImageViews[5] = (ImageView) findViewById(R.id.imageView6);
        mImageViews[6] = (ImageView) findViewById(R.id.imageView7);
        mImageViews[7] = (ImageView) findViewById(R.id.imageView8);

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                editor.putInt("ala", 1);
                editor.apply();
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
        if(mTextToSpeech.isLanguageAvailable(Locale.JAPANESE) == TextToSpeech.LANG_AVAILABLE) {
            mTextToSpeech.setLanguage(Locale.JAPANESE);
            lan = 0;
            Log.v("lan","0");
        } else {
            mTextToSpeech.setLanguage(Locale.ENGLISH);
            lan = 1;
            Log.v("lan","1");
        }
    }

    private void speechText() {
        if (lan == 0) {
            if (!TextUtils.isEmpty((hour + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3]) / 60) + "じ" + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3] + 1) % 60 + "ふんに起こします")) {
                if (mTextToSpeech.isSpeaking()) {
                    mTextToSpeech.stop();
                }
                Log.d("speechText", (hour + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3]) / 60) + "じ" + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3] + 1) % 60 + "ふんに起こします");
                mTextToSpeech.speak((hour + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3]) / 60) + "じ" + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3] + 1) % 60 + "ふんに起こします", TextToSpeech.QUEUE_FLUSH, null);
            }
        } else if (lan == 1) {
            if (!TextUtils.isEmpty((hour + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3]) / 60) + "o'clock" + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3] + 1) % 60 + "minute")) {
                if (mTextToSpeech.isSpeaking()) {
                    mTextToSpeech.stop();
                }
                Log.d("speechText", (hour + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3]) / 60) + "o'clock" + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3] + 1) % 60 + "minute");
                mTextToSpeech.speak((hour + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3]) / 60) + "o'clock" + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3] + 1) % 60 + "minute", TextToSpeech.QUEUE_FLUSH, null);

            }
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
            for(int i = 0; i < 5; i++){
                mImageViews[i].setImageResource(imageId[imagenumber[i]]);
            }
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
            new Handler().postDelayed(runnable, 0);
            Intent intent = new Intent(Timer.this, Recognize.class);
            again = 1;
            intent.putExtra("again", again);
            startActivity(intent);
        }
    }
 }
