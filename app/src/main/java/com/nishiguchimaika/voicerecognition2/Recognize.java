package com.nishiguchimaika.voicerecognition2;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Recognize extends Activity {

    private static final int REQUEST_CODE = 0;
    MediaPlayer mp;
    int t;
    int a;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        a=0;
        Intent new_intent = getIntent();
        a = new_intent.getIntExtra("again", 0);
        Log.e("TAG3", String.valueOf(a));

        mp = MediaPlayer.create(this, R.raw.sound);
        if(a==0){
            mp.start();
            mp.setLooping(true);
        }
        t=0;

        try {
            Intent intent = new Intent(
                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(
                    RecognizerIntent.EXTRA_PROMPT,
                    "Try speech.");

            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(Recognize.this,
                    "ActivityNotFoundException", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            ArrayList<String> strArray=new ArrayList<String>();

            String str2 = results.get(0);
            String regex = "at|ad|@|後|あと([0-9]*)(分|分後|分前|ふん|分で|年)";
            String regex2 = "(at|ad|@|あと|後)([0-9]*)時([0-9]*)(分|分後|分前|ふん|分で|年)";
            String regex3 = "おきます|起きます|おきまーす|起きまーす|ポケモン|起き|おき|います";
            Pattern p = Pattern.compile(regex);
            Pattern p2 = Pattern.compile(regex2);
            Pattern p3 = Pattern.compile(regex3);
            Matcher m2 = p2.matcher(str2);
            Matcher m3 = p3.matcher(str2);

            for (int i = 0; i< results.size(); i++) {
                Log.e("TAG",results.get(i));
                strArray.add(results.get(i));
            }

            Matcher[] m = new Matcher[results.size()];

            for (int i = 0; i< results.size(); i++) {
                m[i]=p.matcher(strArray.get(i));
            }

            for (int i = 0; i< results.size(); i++) {

                if (m[i].find()&&m[i].group(1)!=null) {
                    mp.stop();
                    String matchstr = m[i].group(1);
                    Log.e("TAG@1",matchstr);
                    int q1 = Integer.parseInt(matchstr);
                    Log.e("TAG@2", String.valueOf(q1));
                    int q = q1;
                    Intent timerIntent = new Intent(getApplicationContext(), Timer.class);
                    timerIntent.putExtra("time", q);
                    startActivity(timerIntent);
                    break;
                }else if (i==results.size()-1){
                    if(m2.find()){
                        mp.stop();
                        String matchstr2 = m2.group(2);
                        String matchstr3 = m2.group(3);
                        int q1 = Integer.parseInt(matchstr2);
                        Log.e("q1",q1+"");
                        int q2 = Integer.parseInt(matchstr3);
                        Log.e("q2",q2+"");
                        int q = q1*10+q2;
                        Log.e("q(m2)",q+"");
                        if(q2<10){
                        Intent timerIntent = new Intent(getApplicationContext(),Timer.class);
                        timerIntent.putExtra("time",q);
                        startActivity(timerIntent);
                        break;
                        }
                    }else if(m3.find()){
                        mp.stop();
                        Log.e("TAG@1",str2);
                        Intent intent = new Intent(Recognize.this, Timer.class);
                        t = 1;
                        intent.putExtra("new_time",t);
                        startActivity(intent);
                    }

                    try {
                        Intent intent = new Intent(
                                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(
                                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        intent.putExtra(
                                RecognizerIntent.EXTRA_PROMPT,
                                "Try speech.");

                        startActivityForResult(intent, REQUEST_CODE);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(Recognize.this,
                                "ActivityNotFoundException", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Intent intent = new Intent(Recognize.this, Recognize.class);
                    startActivity(intent);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}