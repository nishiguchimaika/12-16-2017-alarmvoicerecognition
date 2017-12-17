package com.nishiguchimaika.voicerecognition2;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;

public class SubActivity2 extends AppCompatActivity {

    private final int[] checkBoxIds = {
            R.id.checkBox,
            R.id.checkBox2,
            R.id.checkBox3
    };
    CheckBox[] checkBoxes;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    int pic;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub4);

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
        editor.putInt("sorm",1);
        editor.apply();
        editor.putInt("level", 0);
        pic = pref.getInt("level", 0);
        checkBoxes[pref.getInt("position2",0)].setChecked(true);
    }

    private View.OnClickListener checkClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int index = (Integer) v.getTag();
            for (int i = 0; i < checkBoxes.length; i++) {
                checkBoxes[i].setChecked(false);
            }
            setCheck(index);
            checkBoxes[index].setChecked(true);
            editor.putInt("level", index);
            editor.apply();
        }
    };

    private void setCheck(int index){
        editor.putInt("position2",index);
        editor.apply();
    }

    public void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }
}