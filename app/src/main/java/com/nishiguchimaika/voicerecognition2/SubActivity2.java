package com.nishiguchimaika.voicerecognition2;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

public class SubActivity2 extends AppCompatActivity {

    private final int[] checkBoxIds = {
            R.id.checkBox,
            R.id.checkBox2,
            R.id.checkBox3
    };
    CheckBox[] checkBoxes;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    ImageView imageView;
    int pic;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub4);

        init();

        imageView = (ImageView) findViewById(R.id.imageView);

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
        editor.putInt("color",1);
        editor.apply();
        editor.putInt("level", 0);
        pic = pref.getInt("level", 0);
        lan = pref.getInt("language", 0);
        checkBoxes[pref.getInt("position2",0)].setChecked(true);
        if(pic==0){
            imageView.setImageResource(R.drawable.calculate);
        }else if(pic==1){
            imageView.setImageResource(R.drawable.calculatee2);
        }else if(pic==2){
            imageView.setImageResource(R.drawable.calculatee3);
        }

        if(lan == 0){
            checkBoxes[0].setText("レベル１");
            checkBoxes[1].setText("レベル２");
            checkBoxes[2].setText("レベル３");
        }else if(lan == 1){
            checkBoxes[0].setText("Level 1");
            checkBoxes[1].setText("Level２");
            checkBoxes[2].setText("Level３");
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
            checkBoxes[index].setChecked(true);
            if(index == 0){
                imageView.setImageResource(R.drawable.calculate);
            }else if(index == 1){
                imageView.setImageResource(R.drawable.calculatee2);
            }else if(index == 2){
                imageView.setImageResource(R.drawable.calculatee3);
            }
            /*checkBoxes[index].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub
                    // チェック状態が変更された時の処理を記述
                    if(index == 0){
                        imageView.setImageResource(R.drawable.calculate);
                    }else if(index == 1){
                        imageView.setImageResource(R.drawable.calculatee2);
                    }else if(index == 2){
                        imageView.setImageResource(R.drawable.calculatee3);
                    }
                }
            });*/
            editor.putInt("level", index);
            editor.apply();
            editor.putInt("way",2);
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