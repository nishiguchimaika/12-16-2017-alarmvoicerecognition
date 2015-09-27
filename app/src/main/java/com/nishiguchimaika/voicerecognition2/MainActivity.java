package com.nishiguchimaika.voicerecognition2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    int[] imageNumber;
    ImageButton startBtn;
    ImageButton stopBtn;
    ImageButton soundBtn;
    ImageButton mathBtn;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int color;
    int color2;
    int day;

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
        imageId[6] = R.drawable.calculate1;
        imageId[7] = R.drawable.calculate2;
        imageNumber = new int[4];

        pref = getSharedPreferences("select", Context.MODE_PRIVATE);
        editor = pref.edit();
        color = pref.getInt("color", 0);
        color2 = pref.getInt("color2", 0);
        Log.e("color2",String.valueOf(color2));
        day = pref.getInt("day", 0);

        startBtn = (ImageButton)findViewById(R.id.start);
        stopBtn = (ImageButton)findViewById(R.id.stop);
        soundBtn = (ImageButton)findViewById(R.id.sound);
        mathBtn = (ImageButton)findViewById(R.id.math);
        startBtn.setOnClickListener(myAlarmListener);
        stopBtn.setOnClickListener(myAlarmListener);
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
    }

    View.OnClickListener myAlarmListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent bootIntent = new Intent(MainActivity.this, MyService.class);
            bootIntent.putExtra("notificationId", notificationId);
            alarmIntent = PendingIntent.getService(MainActivity.this, 0, bootIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            switch (v.getId()) {
                case R.id.start:
                    editor.putInt("color2",0);
                    editor.apply();
                    startBtn.setImageResource(imageId[0]);
                    stopBtn.setImageResource(imageId[3]);
                    int hour = tPicker.getCurrentHour();
                    int minute = tPicker.getCurrentMinute();
                    Calendar startTime = Calendar.getInstance();
                    startTime.setTimeInMillis(System.currentTimeMillis());
                    startTime.set(Calendar.HOUR_OF_DAY, hour);
                    startTime.set(Calendar.MINUTE, minute);
                    startTime.set(Calendar.SECOND, 0);
                    long alarmStartTime = startTime.getTimeInMillis();
                    Log.e("startTime",String.valueOf(startTime));

                    if(startTime.getTimeInMillis() < System.currentTimeMillis()){
                        startTime.add(Calendar.DAY_OF_YEAR,1);
                    }else if(day == 1){
                        Log.e("day","1");
                        startTime.add(Calendar.DAY_OF_YEAR,1);
                    }

                    alarm.setRepeating(
                            AlarmManager.RTC_WAKEUP,
                            alarmStartTime,
                            AlarmManager.INTERVAL_DAY,
                            alarmIntent

                    );
                    Toast.makeText(MainActivity.this, "通知セット完了!", Toast.LENGTH_SHORT).show();
                    notificationId++;
                    break;
                case R.id.stop:
                    editor.putInt("color2",1);
                    editor.apply();
                    startBtn.setImageResource(imageId[1]);
                    stopBtn.setImageResource(imageId[2]);
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

    @Override
    protected void onResume(){
        super.onResume();
        startBtn.setImageResource(imageId[1]);
        stopBtn.setImageResource(imageId[2]);
        soundBtn.setImageResource(imageId[4]);
        mathBtn.setImageResource(imageId[7]);
        if(color == 0){
            Log.i("color=", "0");
            soundBtn.setImageResource(imageId[4]);
            mathBtn.setImageResource(imageId[7]);
            if (color2 == 0) {
                Log.i("color2", "0");
                startBtn.setImageResource(imageId[0]);
                stopBtn.setImageResource(imageId[3]);
            } else if (color2 == 1) {
                Log.i("color2", "1");
                startBtn.setImageResource(imageId[1]);
                stopBtn.setImageResource(imageId[2]);
            }
        }else if(color == 1) {
            Log.i("color=", "1");
            soundBtn.setImageResource(imageId[5]);
            mathBtn.setImageResource(imageId[6]);
            if (color2 == 0) {
                Log.i("color2", "0");
                startBtn.setImageResource(imageId[0]);
                stopBtn.setImageResource(imageId[3]);
            } else if (color2 == 1) {
                Log.i("color2", "1");
                startBtn.setImageResource(imageId[1]);
                stopBtn.setImageResource(imageId[2]);
            }
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

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.e("onDestroy2","ok");
    }
}
