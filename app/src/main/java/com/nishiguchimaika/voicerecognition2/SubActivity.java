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
            R.raw.sound9,
            R.raw.soundd4,
            R.raw.sound11,
            R.raw.sound8,
            R.raw.sound10};

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
    int lan;

    private void init(){
        checkBoxes = new CheckBox[checkBoxIds.length];
        soundPools = new SoundPool[soundResourceIds.length];
        soundPoolIds = new int[soundResourceIds.length];
        for (int i = 0; i < checkBoxes.length; i++) {
            checkBoxes[i] = (CheckBox) findViewById(checkBoxIds[i]);
            checkBoxes[i].setChecked(false);
            checkBoxes[i].setTag(new Integer(i));
            checkBoxes[i].setOnClickListener(soundClickListener);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub);

        init();

        Toolbar toolbar = (Toolbar) findViewById(R.id.header);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        pref = getSharedPreferences("select", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putInt("color",0);
        editor.apply();
        editor.putInt("sounds", 0);
        lan = pref.getInt("language", 0);
        checkBoxes[pref.getInt("position",0)].setChecked(true);

        for (int i = 0; i < soundPools.length; i++) {
            soundPools[i] = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            soundPoolIds[i] = soundPools[i].load(this, soundResourceIds[i], 1);
            soundPools[i].setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                }
            });
        }

        if(lan == 0){
            checkBoxes[0].setText("電話");
            checkBoxes[1].setText("インターホン");
            checkBoxes[2].setText("不安な音");
            checkBoxes[3].setText("女の人の叫び声");
            checkBoxes[4].setText("パトカー");
            checkBoxes[5].setText("救急車のサイレン");
            checkBoxes[6].setText("拳銃");
        }else if(lan == 1){
            checkBoxes[0].setText("Phone");
            checkBoxes[1].setText("Interphone");
            checkBoxes[2].setText("Horror");
            checkBoxes[3].setText("Screaming of woman");
            checkBoxes[4].setText("Patrol car");
            checkBoxes[5].setText("Ambulance siren");
            checkBoxes[6].setText("Handgun");
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
            setCheck(index);
            checkBoxes[index].setChecked(true);
            editor.putInt("way",0);
            editor.apply();
            soundPools[index].play(soundPoolIds[index],1,1,0,0,0);
            editor.putInt("sounds", index);
            editor.apply();
        }
    };

    private void setCheck(int index){
        editor.putInt("position",index);
        editor.apply();
    }
}
