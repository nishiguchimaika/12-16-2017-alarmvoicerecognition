package com.nishiguchimaika.voicerecognition2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.Calendar;
//Main,(SubActivity),alarmBroadcast,MyService,Recognize,onActivityResult,Timer



public class MainActivity extends AppCompatActivity {
    TimePicker tPicker;
    int notificationId;
    private PendingIntent alarmIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Target viewTarget = new ViewTarget(R.id.start, this);
        new ShowcaseView.Builder(this, true)
                .setTarget(viewTarget)
                .setContentTitle("タイトル")
                .setContentText("テキスト")
                .singleShot(42)
                .build();


        tPicker  =  (TimePicker)findViewById(R.id.timePicker);
        tPicker.setIs24HourView(true);

        Calendar setTimeCalendar = Calendar.getInstance();
        tPicker.setCurrentHour(setTimeCalendar.get(Calendar.HOUR_OF_DAY));

        ImageButton startBtn = (ImageButton)findViewById(R.id.start);
        ImageButton stopBtn = (ImageButton)findViewById(R.id.stop);
        final ImageButton soundBtn=(ImageButton)findViewById(R.id.sound);
        stopBtn.setOnClickListener(myAlarmListener);
        startBtn.setOnClickListener(myAlarmListener);
        soundBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startSubActivity();
            }
        });
    }

    private void startSubActivity(){
        Intent intent = new Intent(this, SubActivity.class);
        startActivityForResult(intent,0);
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
                    int hour = tPicker.getCurrentHour();
                    int minute = tPicker.getCurrentMinute();

                    Calendar startTime = Calendar.getInstance();
//                    startTime.setTimeInMillis(System.currentTimeMillis());
//                    Log.d("Current Time",String.valueOf(startTime.get(Calendar.HOUR) + ":" + String.valueOf(startTime.get(Calendar.MINUTE))));

                    startTime.set(Calendar.HOUR_OF_DAY, hour);
                    startTime.set(Calendar.MINUTE, minute);
                    startTime.set(Calendar.SECOND, 0);
                 //   startTime.add(Calendar.SECOND,5);
                    long alarmStartTime = startTime.getTimeInMillis();

                    alarm.set(
                            AlarmManager.RTC_WAKEUP,
                            alarmStartTime,
                            alarmIntent
                    );
                    Toast.makeText(MainActivity.this, "通知セット完了!", Toast.LENGTH_SHORT).show();
                    notificationId++;

                    break;
                case R.id.stop:
                    stopService(new Intent(getApplicationContext(),MyService.class));
                    alarm.cancel(alarmIntent);
                    Toast.makeText(MainActivity.this, "通知をキャンセルしました!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}

