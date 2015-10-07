package com.nishiguchimaika.voicerecognition2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SetActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set);

        Toolbar toolbar = (Toolbar) findViewById(R.id.header);
        toolbar.setNavigationIcon(R.mipmap.ic_keyboard_backspace_black_24px);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });
    }

    public void lanBtn(View v){
        Intent intent = new Intent(SetActivity.this, Language.class);
        startActivity(intent);
    }
    public void genBtn(View v) {
        Intent intent = new Intent(SetActivity.this, GenActivity.class);
        startActivityForResult(intent, 0);
    }
    public void howBtn(View v){
        Intent intent = new Intent(SetActivity.this, HowActivity.class);
        startActivityForResult(intent, 0);
    }

    public void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }
}
