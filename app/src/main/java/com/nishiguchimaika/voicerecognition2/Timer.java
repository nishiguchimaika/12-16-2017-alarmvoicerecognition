package com.nishiguchimaika.voicerecognition2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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
    //int Day;
    /*int[] soundResourceIds = {
            R.raw.sound1,
            R.raw.sound2,
            R.raw.sound9,
            R.raw.sound4,
            R.raw.sound11,
            R.raw.sound8,
            R.raw.sound10,
    };
    public static MediaPlayer[] mps;
    SharedPreferences pref;
    int sounds;*/
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub2);

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

        /*Day = 0;
        Intent intent = getIntent();
        Day=intent.getIntExtra("new_time",0);
        Log.e("Day",String.valueOf(Day));*/

        final CounterClass timer = new CounterClass(q * 1000, 1000);
        if(q != 0){
            timer.start();
        }

        /*pref = getSharedPreferences("select", Context.MODE_PRIVATE);
        sounds = pref.getInt("sounds", 0);*/

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
                /*mps = new MediaPlayer[soundResourceIds.length];
                for(int i = 0; i< soundResourceIds.length; i++) {
                    mps[i] = MediaPlayer.create(getApplicationContext(), soundResourceIds[i]);
                    if(mps[i].isPlaying()){
                        mps[i].stop();
                    }
                }*/
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
        mTextToSpeech.setLanguage(Locale.ENGLISH);
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        mps = new MediaPlayer[soundResourceIds.length];
        for(int i = 0; i< soundResourceIds.length; i++){
            mps[i] = MediaPlayer.create(getApplicationContext(),soundResourceIds[i]);
            if(mps[i].isPlaying()){
                mps[i].stop();
            }else if(Day == 1){
                Log.e("Day","1");
                mps[i].stop();
                Intent new_intent = new Intent(Timer.this, MainActivity.class);
                startActivity(new_intent);
            }
        }

    }*/

    void speechText() {
        if (!TextUtils.isEmpty((hour + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3]) / 60) + "o'clock" + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3]) % 60 + "minutes")) {
            if (mTextToSpeech.isSpeaking()) {
                mTextToSpeech.stop();
            }
            Log.d("speechText", (hour + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3]) / 60) + "o'clock" + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3]) % 60 + "minutes");
            mTextToSpeech.speak((hour + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3]) / 60) + "o'clock" + (minute + (imagenumber[0] * 10 + imagenumber[1]) * 60 + imagenumber[2] * 10 + imagenumber[3]) % 60 + "minutes", TextToSpeech.QUEUE_FLUSH, null);
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
                /*if (sounds == 0) {
                    mps[0].start();
                    mps[0].setLooping(true);
                } else if (sounds == 1) {
                    mps[1].start();
                    mps[1].setLooping(true);
                } else if (sounds == 2) {
                    mps[2].start();
                    mps[2].setLooping(true);
                } else if (sounds == 3) {
                    mps[3].start();
                    mps[3].setLooping(true);
                } else if (sounds == 4) {
                    mps[4].start();
                    mps[4].setLooping(true);
                } else if (sounds == 5) {
                    mps[5].start();
                    mps[5].setLooping(true);
                } else if (sounds == 6) {
                    mps[6].start();
                    mps[6].setLooping(true);
                }*/

                Log.i("音声認識","again");
                Intent new_intent = new Intent(Timer.this, Recognize.class);
                a = 1;
                new_intent.putExtra("again", a);
                startActivity(new_intent);
            }
        }
 }
