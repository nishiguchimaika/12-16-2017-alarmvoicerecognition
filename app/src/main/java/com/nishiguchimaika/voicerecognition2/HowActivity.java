package com.nishiguchimaika.voicerecognition2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.support.v7.widget.Toolbar;


public class HowActivity extends AppCompatActivity{
    ImageButton soundBtn;
    ImageButton mathBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.howto);

        Toolbar toolbar = (Toolbar) findViewById(R.id.header);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        soundBtn = (ImageButton) findViewById(R.id.sound);
        mathBtn = (ImageButton) findViewById(R.id.math);
        soundBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startMainActivity();
            }
        });
        mathBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startMainActivity2();
            }
        });
    }

    public void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), NiActivity.class);
        startActivityForResult(intent, 0);
    }
    public void startMainActivity2() {
        Intent intent = new Intent(getApplicationContext(), MuActivity.class);
        startActivityForResult(intent, 0);
    }

}
