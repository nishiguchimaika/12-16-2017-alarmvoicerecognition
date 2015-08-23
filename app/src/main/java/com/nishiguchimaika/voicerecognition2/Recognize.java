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
   // Intent recognizerIntent;
    private static final int REQUEST_CODE = 0;
    private final int[] soundResourceIds = {
            R.raw.sound,
            R.raw.sound1,
            R.raw.sound2,
            R.raw.sound3,
            R.raw.sound4,
            R.raw.sound5,
            R.raw.sound6,
            R.raw.sound7,
    };
    MediaPlayer[] mps;
   // int[] mpIds = new int[soundResourceIds.length];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        mps = new MediaPlayer[soundResourceIds.length];
        try {
            // インテント作成
            Intent intent = new Intent(
                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH); // ACTION_WEB_SEARCH
            intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(
                    RecognizerIntent.EXTRA_PROMPT,
                    "Try speech."); // お好きな文字に変更できます

            // インテント発行
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // このインテントに応答できるアクティビティがインストールされていない場合
            Toast.makeText(Recognize.this,
                    "ActivityNotFoundException", Toast.LENGTH_LONG).show();
        }
    }

    // アクティビティ終了時に呼び出される
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 自分が投げたインテントであれば応答する
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            ArrayList<String> strArray=new ArrayList<String>();

            //String str2 = "あと8時5分";
            String str2 = results.get(0);
            String regex = "at|ad|@|後|あと([0-9]*)(分|分後|分前|ふん|年)";
            String regex2 = "(あと|後)([0-9]*)時([0-9]*)(分|分後|分前|分で)";
            Pattern p = Pattern.compile(regex);
            Pattern p2 = Pattern.compile(regex2);
            Matcher m2 = p2.matcher(str2);

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
                    for(int n = 0; n < mps.length; n++){
                            if(mps[n].isPlaying()){
                                mps[n].stop();
                                /*break;*/
                            }
                    }
                    String matchstr = m[i].group(1);
                    Log.e("TAG@1",matchstr);
                    int q = Integer.parseInt(matchstr);
                    Log.e("TAG", String.valueOf(q));
                    Intent timerIntent = new Intent(getApplicationContext(), Timer.class);
                    timerIntent.putExtra("time", q);
                    startActivity(timerIntent);
                    break;
                }else if (i==results.size()-1){

                    if(m2.find()){
                        for(int n = 0; n < mps.length; n++) {
                            if(mps[n].isPlaying()){
                                mps[n].stop();
                                /*break;*/
                            }
                        }


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
                    }

                    try {
                        // インテント作成
                        Intent intent = new Intent(
                                RecognizerIntent.ACTION_RECOGNIZE_SPEECH); // ACTION_WEB_SEARCH
                        intent.putExtra(
                                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        intent.putExtra(
                                RecognizerIntent.EXTRA_PROMPT,
                                "Try speech.");

                        // インテント発行
                        startActivityForResult(intent, REQUEST_CODE);
                    } catch (ActivityNotFoundException e) {
                        // このインテントに応答できるアクティビティがインストールされていない場合
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