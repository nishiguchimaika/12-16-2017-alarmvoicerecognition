package com.nishiguchimaika.voicerecognition2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
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
    int[] soundResourceIds = {
            R.raw.sound1,
            R.raw.sound2,
            R.raw.sound3,
            R.raw.sound4,
            R.raw.sound5,
            R.raw.sound6,
            R.raw.sound7,
    };
    MediaPlayer[] mps;
    SharedPreferences pref;
    int sounds;
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

        mps = new MediaPlayer[soundResourceIds.length];

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

        Log.e("TAG3", String.valueOf(q));

        final CounterClass timer = new CounterClass(q * 60000, 1000);
        timer.start();


        pref = getSharedPreferences("select", Context.MODE_PRIVATE);
        sounds = pref.getInt("", 0);

        //  btnStart = (Button) findViewById(R.id.btnStart);
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
        }, 1 * 1000);

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
        if (TextToSpeech.SUCCESS == status) {
            Locale locale = Locale.ENGLISH;
            if (mTextToSpeech.isLanguageAvailable(locale) >= TextToSpeech.LANG_AVAILABLE) {
                mTextToSpeech.setLanguage(locale);
            }
        }
    }

    void speechText() {
        if (!TextUtils.isEmpty((hour+(minute+(imagenumber[0] * 10 + imagenumber[1])*60+imagenumber[2]*10+imagenumber[3])/60) + "o'clock" + (minute+(imagenumber[0] * 10 + imagenumber[1])*60+imagenumber[2]*10+imagenumber[3])%60+1 + "minutes")) {
            if (mTextToSpeech.isSpeaking()) {
                mTextToSpeech.stop();
            }
            Log.d("speechText", (hour+(minute+(imagenumber[0] * 10 + imagenumber[1])*60+imagenumber[2]*10+imagenumber[3])/60) + "o'clock" + (minute+(imagenumber[0] * 10 + imagenumber[1])*60+imagenumber[2]*10+imagenumber[3])%60+1 + "minutes");
            mTextToSpeech.speak((hour+(minute+(imagenumber[0] * 10 + imagenumber[1])*60+imagenumber[2]*10+imagenumber[3])/60) + "o'clock" + (minute+(imagenumber[0] * 10 + imagenumber[1])*60+imagenumber[2]*10+imagenumber[3])%60+1 + "minutes", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    //  @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    //@SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            //TODO Auto_generated constructor stub
            setTime(millisInFuture);
        }

        //@SuppressLint("NewApi")
        //@TargetApi(Build.VERSION_CODES.GINGERBREAD)
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
            /*Log.e("Time1", String.valueOf(imagenumber[0]));
            Log.e("Time2", String.valueOf(imagenumber[1]));
            Log.e("Time3", String.valueOf(imagenumber[2]));
            Log.e("Time4", String.valueOf(imagenumber[3]));
            Log.e("Time5", String.valueOf(imagenumber[4]));
            Log.e("Time6", String.valueOf(imagenumber[5]));*/

        }


        @Override
        public void onFinish() {
            //TODO Auto_generated method stub



          //  mp.stop();
            /*for(int n = 0; n < 7; n++) {
                mps[n].start();
            }*/
            if (sounds == 0) {
                    mps[0].start();
                } else if (sounds == 1) {
                    mps[1].start();
                } else if (sounds == 2) {
                    mps[2].start();
                } else if (sounds == 3) {
                    mps[3].start();
                } else if (sounds == 4) {
                    mps[4].start();
                } else if (sounds == 5) {
                    mps[5].start();
                } else if (sounds == 6) {
                    mps[6].start();
                }
            Intent new_intent = new Intent(Timer.this, Recognize.class);
            //new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(new_intent);

            btnStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int n = 0; n < 7; n++) {
                        if(mps[n].isPlaying()) {
                            mps[n].stop();
                            break;
                        }
                    }
                    Intent intent = new Intent(Timer.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}