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
    ImageButton soundBtn;
    ImageButton mathBtn;
    ImageButton howBtn;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int sorm;
    int ala;
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
        imageId[6] = R.drawable.plus1;
        imageId[7] = R.drawable.plus;
        imageNumber = new int[4];
        pref = getSharedPreferences("select", Context.MODE_PRIVATE);
        editor = pref.edit();
        sorm = pref.getInt("sorm", 0);
        ala = pref.getInt("ala", 1);
        day = pref.getInt("day", 0);
        startBtn = (ImageButton) findViewById(R.id.start);
        soundBtn = (ImageButton) findViewById(R.id.sound);
        mathBtn = (ImageButton) findViewById(R.id.math);
        howBtn = (ImageButton) findViewById(R.id.how);

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
        howBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startSubActivity3();
            }
        });
    }

    //startBtnが押されたら
    View.OnClickListener myAlarmListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent bootIntent = new Intent(MainActivity.this, MyService.class);
            bootIntent.putExtra("notificationId", notificationId);
            alarmIntent = PendingIntent.getService(MainActivity.this, 0, bootIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            switch (v.getId()) {
                case R.id.start:
                    if(ala == 1) {
                        editor.putInt("ala", 0);
                        ala = 0;
                        editor.apply();
                        startBtn.setImageResource(imageId[0]);
                        int hour = tPicker.getCurrentHour();
                        int minute = tPicker.getCurrentMinute();
                        Calendar startTime = Calendar.getInstance();
                        startTime.setTimeInMillis(System.currentTimeMillis());
                        startTime.set(Calendar.HOUR_OF_DAY, hour);
                        startTime.set(Calendar.MINUTE, minute);
                        startTime.set(Calendar.SECOND, 0);
                        long alarmStartTime = startTime.getTimeInMillis();

                        if (startTime.getTimeInMillis() < System.currentTimeMillis()) {
                            startTime.add(Calendar.DAY_OF_YEAR, 1);
                        } else if (day == 1) {
                            startTime.add(Calendar.DAY_OF_YEAR, 1);
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
                    }else if(ala == 0){
                        editor.putInt("ala",1);
                        ala = 1;
                        editor.apply();
                        startBtn.setImageResource(imageId[1]);
                        alarm.cancel(alarmIntent);
                        Toast.makeText(MainActivity.this, "通知をキャンセルしました!", Toast.LENGTH_SHORT).show();
                        break;
                    }
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
        soundBtn.setImageResource(imageId[4]);
        mathBtn.setImageResource(imageId[7]);
        if(sorm == 0){
            Log.i("sorm=", "0");
            soundBtn.setImageResource(imageId[4]);
            mathBtn.setImageResource(imageId[7]);
            if (ala == 0) {
                Log.i("ala", "0");
                startBtn.setImageResource(imageId[0]);
            } else if (ala == 1) {
                Log.i("ala", "1");
                startBtn.setImageResource(imageId[1]);
            }
        }else if(sorm == 1) {
            Log.i("sorm=", "1");
            soundBtn.setImageResource(imageId[5]);
            mathBtn.setImageResource(imageId[6]);
            if (ala == 0) {
                Log.i("ala", "0");
                startBtn.setImageResource(imageId[0]);
            } else if (ala == 1) {
                Log.i("ala", "1");
                startBtn.setImageResource(imageId[1]);
            }
        }
    }

    private void startSubActivity(){
        //sound
        Intent intent = new Intent(this, SubActivity.class);
        startActivityForResult(intent, 0);
    }
    private void startSubActivity2(){
        //calculate
        Intent intent2 = new Intent(this, SubActivity2.class);
        startActivityForResult(intent2, 0);
    }
    private void startSubActivity3(){
        Intent intent3 = new Intent(this, HowActivity.class);
        startActivityForResult(intent3, 0);
    }

    @Override
    protected void onDestroy(){
        Log.e("MainDestroy", "okay");
        super.onDestroy();
    }
}
