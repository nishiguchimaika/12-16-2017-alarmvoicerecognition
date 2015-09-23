package com.nishiguchimaika.voicerecognition2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    TimePicker tPicker;
    int notificationId;
    private PendingIntent alarmIntent;
    int[] imageId;
    int[] imagenumber;
    ImageButton startBtn;
    ImageButton stopBtn;
    SharedPreferences pref;
    int color;
    int t;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tPicker  =  (TimePicker)findViewById(R.id.timePicker);
        tPicker.setIs24HourView(true);
        Calendar setTimeCalendar = Calendar.getInstance();
        tPicker.setCurrentHour(setTimeCalendar.get(Calendar.HOUR_OF_DAY));
        imageId = new int[8];
        imageId[0] = R.drawable.start1;
        imageId[1] = R.drawable.start2;
        imageId[2] = R.drawable.cancel1;
        imageId[3] = R.drawable.cancel2;
        imageId[4] = R.drawable.sound1;
        imageId[5] = R.drawable.sound2;
        imageId[6] = R.drawable.sub1;
        imageId[7] = R.drawable.sub2;
        imagenumber = new int[4];
        t=0;
        Intent intent = getIntent();
        t = intent.getIntExtra("new_time", 0);
        Log.e("TAG3", String.valueOf(t));

        pref = getSharedPreferences("select", Context.MODE_PRIVATE);
        color = pref.getInt("color", 0);
        startBtn = (ImageButton)findViewById(R.id.start);
        stopBtn = (ImageButton)findViewById(R.id.stop);
        final ImageButton soundBtn=(ImageButton)findViewById(R.id.sound);
        final ImageButton mathBtn=(ImageButton)findViewById(R.id.math);
        stopBtn.setOnClickListener(myAlarmListener);
        startBtn.setOnClickListener(myAlarmListener);
        soundBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startSubActivity();
            }
        });
        mathBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startSubActivity2();
            }
        });

        if(color==0){
            Log.i("color=", "0");
            Resources res = getResources();
            Drawable drawable = res.getDrawable(imageId[4]);
            soundBtn.setImageDrawable(drawable);
            Resources res2 = getResources();
            Drawable drawable2 = res2.getDrawable(imageId[7]);
            mathBtn.setImageDrawable(drawable2);
        }else if(color==1){
            Log.i("color=","1");
            Resources res = getResources();
            Drawable drawable = res.getDrawable(imageId[5]);
            soundBtn.setImageDrawable(drawable);
            Resources res2 = getResources();
            Drawable drawable2 = res2.getDrawable(imageId[6]);
            mathBtn.setImageDrawable(drawable2);
        }
    }

    private void startSubActivity(){
        Intent intent = new Intent(this, SubActivity.class);
        startActivityForResult(intent,0);
    }

    private void startSubActivity2(){
        Intent intent2 = new Intent(this, SubActivity2.class);
        startActivityForResult(intent2,0);
    }

    View.OnClickListener myAlarmListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent bootIntent = new Intent(MainActivity.this, AlarmBroadcastReceiver.class);
            bootIntent.putExtra("notificationId", notificationId);
            alarmIntent = PendingIntent.getBroadcast(MainActivity.this, 0, bootIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            switch (v.getId()) {
                case R.id.start:
                    Resources res = getResources();
                    Drawable drawable = res.getDrawable(imageId[0]);
                    startBtn.setImageDrawable(drawable);
                    Resources res2 = getResources();
                    Drawable drawable2 = res2.getDrawable(imageId[3]);
                    stopBtn.setImageDrawable(drawable2);
                    int hour = tPicker.getCurrentHour();
                    int minute = tPicker.getCurrentMinute();
                    Calendar startTime = Calendar.getInstance();
                    startTime.setTimeInMillis(System.currentTimeMillis());

                    /*startTime.set(Calendar.YEAR,2015);
                    startTime.set(Calendar.MONTH,9);
                    startTime.set(Calendar.DAY_OF_MONTH,22);*/
                    startTime.set(Calendar.HOUR_OF_DAY, hour);
                    startTime.set(Calendar.MINUTE, minute);
                    startTime.set(Calendar.SECOND, 0);
                    long alarmStartTime = startTime.getTimeInMillis();
                    Log.e("startTime",String.valueOf(startTime));

                    if(startTime.getTimeInMillis() < System.currentTimeMillis()){
                        startTime.add(Calendar.DAY_OF_YEAR,1);
                    }else if(t==1){
                        startTime.add(Calendar.DAY_OF_YEAR,1);
                    }

                    alarm.set(
                            AlarmManager.RTC_WAKEUP,
                            alarmStartTime,
                            alarmIntent
                    );
                    Toast.makeText(MainActivity.this, "通知セット完了!", Toast.LENGTH_SHORT).show();
                    notificationId++;

                    break;
                case R.id.stop:
                    Resources res3 = getResources();
                    Drawable drawable3 = res3.getDrawable(imageId[1]);
                    startBtn.setImageDrawable(drawable3);
                    Resources res4 = getResources();
                    Drawable drawable4 = res4.getDrawable(imageId[2]);
                    stopBtn.setImageDrawable(drawable4);
                    alarm.cancel(alarmIntent);
                    Toast.makeText(MainActivity.this, "通知をキャンセルしました!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        /*private PendingIntent getPendingIntent() {
            // アラーム時に起動するアプリケーションを登録
            Intent intent = new Intent(c, MyService.class);
            PendingIntent pendingIntent = PendingIntent.getService(c, PendingIntent.FLAG_ONE_SHOT, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            return pendingIntent;
        }*/
    };
}

