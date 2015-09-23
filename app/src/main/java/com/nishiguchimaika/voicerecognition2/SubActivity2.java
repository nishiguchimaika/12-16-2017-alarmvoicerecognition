package com.nishiguchimaika.voicerecognition2;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SubActivity2 extends Activity implements CompoundButton.OnCheckedChangeListener {

    Switch switch1;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub4);

        Toolbar toolbar = (Toolbar) findViewById(R.id.header);
        toolbar.setNavigationIcon(R.mipmap.ic_keyboard_backspace_black_24px);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(this);

        pref = getSharedPreferences("select", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked == true) {
            switch1.setChecked(true);
            Log.i("true","ですよ");
            editor.putInt("color",1);
            editor.putInt("way", 2);
            editor.apply();
        } else {
            Log.i("false","ですよ");
            switch1.setChecked(false);
            editor.putInt("way", 1);
            editor.putInt("color",0);
            editor.apply();
        }
    }
}