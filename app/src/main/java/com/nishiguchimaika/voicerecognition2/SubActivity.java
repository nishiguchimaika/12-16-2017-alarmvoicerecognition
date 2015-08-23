package com.nishiguchimaika.voicerecognition2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;


public class SubActivity extends AppCompatActivity {

    private final int[] soundResourceIds = {
            R.raw.soundd1,
            R.raw.soundd2,
            R.raw.soundd3,
            R.raw.soundd4,
            R.raw.soundd5,
            R.raw.soundd6,
            R.raw.soundd7};

    private final int[] checkBoxIds = {
            R.id.checkBox1,
            R.id.checkBox2,
            R.id.checkBox3,
            R.id.checkBox4,
            R.id.checkBox5,
            R.id.checkBox6,
            R.id.checkBox7};

    SoundPool[] soundPools;
    int[] soundPoolIds;
    CheckBox[] checkBoxes;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub);
        Toolbar toolbar = (Toolbar) findViewById(R.id.header);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_keyboard_backspace_black_24px);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        //SharedPreferencesを使うための初期設定
        pref = getSharedPreferences("select", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putInt("sounds", 0);
        editor.apply();

        soundPools = new SoundPool[soundResourceIds.length];
        soundPoolIds = new int[soundResourceIds.length];
        for (int i = 0; i < soundPools.length; i++) {
            soundPools[i] = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            soundPoolIds[i] = soundPools[i].load(this,soundResourceIds[i], 1);
            soundPools[i].setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener(){
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status){
                }
            });
        }

        checkBoxes = new CheckBox[checkBoxIds.length];
        for (int i = 0; i < checkBoxes.length; i++) {
            checkBoxes[i] = (CheckBox) findViewById(checkBoxIds[i]);
            checkBoxes[i].setChecked(false);
            checkBoxes[i].setTag(new Integer(i));
            checkBoxes[i].setOnClickListener(soundClickListener);
        }
    }

    public void startMainActivity() {
        for (int i = 0; i < soundPools.length; i++) {
            soundPools[i].stop(soundPoolIds[i]);
            soundPools[i].unload(soundPoolIds[i]);
            soundPools[i].release();
        }
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }

    private View.OnClickListener soundClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = (Integer) v.getTag();
            for (int i = 0; i < checkBoxes.length; i++) {
                checkBoxes[i].setChecked(false);
                soundPools[i].stop(soundPoolIds[i]);
            }
            checkBoxes[index].setChecked(true);
            soundPools[index].play(soundPoolIds[index],1,1,0,0,0);
            editor.putInt("sounds", index);
            editor.apply();
        }
    };
}
