package com.nishiguchimaika.voicerecognition2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;

public class Language extends AppCompatActivity {
    private final int[] checkBoxIds = {
            R.id.checkBox1,
            R.id.checkBox2};
    CheckBox[] checkBoxes;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    int lan;

    private void init(){
        checkBoxes = new CheckBox[checkBoxIds.length];
        for (int i = 0; i < checkBoxes.length; i++) {
            checkBoxes[i] = (CheckBox) findViewById(checkBoxIds[i]);
            checkBoxes[i].setChecked(false);
            checkBoxes[i].setTag(new Integer(i));
            checkBoxes[i].setOnClickListener(checkClickListener);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language);

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
        editor.putInt("language", 0);
        lan = pref.getInt("language", 0);
        checkBoxes[pref.getInt("position3",0)].setChecked(true);

        if(lan == 0){
            checkBoxes[0].setText("日本語");
            checkBoxes[1].setText("英語");
        }else if(lan == 1){
            checkBoxes[0].setText("Japanese");
            checkBoxes[1].setText("English");
        }
    }

    private View.OnClickListener checkClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int index = (Integer) v.getTag();
            for (int i = 0; i < checkBoxes.length; i++) {
                checkBoxes[i].setChecked(false);
            }
            setCheck(index);
            editor.putInt("language", index);
            editor.apply();
            checkBoxes[index].setChecked(true);
        }
    };

    private void setCheck(int index){
        editor.putInt("position3",index);
        editor.apply();
    }

    public void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }
}
