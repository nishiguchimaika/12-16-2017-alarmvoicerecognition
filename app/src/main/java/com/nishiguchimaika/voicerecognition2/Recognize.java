package com.nishiguchimaika.voicerecognition2;


import android.app.Activity;
import android.media.MediaPlayer.OnCompletionListener;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Recognize extends Activity implements OnCompletionListener{

    private static final int REQUEST_CODE = 0;
    MediaPlayer mp;
    int a;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int sounds;
    int[] soundResourceIds = {
            R.raw.sound1,
            R.raw.sound2,
            R.raw.sound9,
            R.raw.sound4,
            R.raw.sound11,
            R.raw.sound8,
            R.raw.sound10,
    };
    public static MediaPlayer[] mps;

    private void init(){
        mp = new MediaPlayer();
        mp = MediaPlayer.create(this, R.raw.sound);
        if(mp.isLooping()){
            mp.stop();
        }
        for (int w = 0; w < soundResourceIds.length; w++) {
            mps = new MediaPlayer[soundResourceIds.length];
            mps[w] = MediaPlayer.create(getApplicationContext(), soundResourceIds[w]);
            if (mps[w].isLooping()) {
                mps[w].stop();
                Log.e("mps[w]","stop");
            }

        }
        //mp.stop();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Recognize", "ok");
        a = 0;
        Intent new_intent = getIntent();
        a = new_intent.getIntExtra("again", 0);
        Log.e("TAG3", String.valueOf(a));

        pref = getSharedPreferences("select", Context.MODE_PRIVATE);
        sounds = pref.getInt("sounds", 0);
        editor = pref.edit();
        editor.putInt("day", 1);
        editor.apply();

        if (a == 0) {
            mp = MediaPlayer.create(this, R.raw.sound);
            mp.start();
            Log.e("a","0");
            mp.setOnCompletionListener(this);
            //mp.setLooping(true);
        } else if (a == 1) {
            mps = new MediaPlayer[soundResourceIds.length];
            for (int w = 0; w < soundResourceIds.length; w++) {
                mps[w] = MediaPlayer.create(getApplicationContext(), soundResourceIds[w]);
            }
            if (sounds == 0) {
                mps[0].start();
                mps[0].setLooping(true);
            } else if (sounds == 1) {
                mps[1].start();
                mps[1].setLooping(true);
            } else if (sounds == 2) {
                mps[2].start();
                mps[2].setLooping(true);
            } else if (sounds == 3) {
                mps[3].start();
                mps[3].setLooping(true);
            } else if (sounds == 4) {
                mps[4].start();
                mps[4].setLooping(true);
            } else if (sounds == 5) {
                mps[5].start();
                mps[5].setLooping(true);
            } else if (sounds == 6) {
                mps[6].start();
                mps[6].setLooping(true);
            }
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
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {
        // TODO Auto-generated method stub
        Log.v("MediaPlayer", "onCompletion");
        // ここに再生完了時の処理を追加
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            ArrayList<String> strArray = new ArrayList<String>();

            String str2 = results.get(0);
            String regex = "at|ad|@|後|あと([0-9]*)(分|分後|分前|ふん|分で|年)";
            String regex2 = "(at|ad|@|あと|後)([0-9]*)時([0-9]*)(分|分後|分前|ふん|分で|年)";
            String regex3 = "おきます|起きます|おきまーす|起きまーす|ポケモン|起き|おき|います|今|沖縄|自慢|俺ます|秋まーす|はげまーす|大きいもい";
            String regex4 = "あと(一点|一転|一善|一例|一)";
            Pattern p = Pattern.compile(regex);
            Pattern p2 = Pattern.compile(regex2);
            Pattern p3 = Pattern.compile(regex3);
            Pattern p4 = Pattern.compile(regex4);
            Matcher m2 = p2.matcher(str2);
            Matcher m3 = p3.matcher(str2);
            Matcher m4 = p4.matcher(str2);

            for (int i = 0; i < results.size(); i++) {
                Log.e("TAG", results.get(i));
                strArray.add(results.get(i));
            }

            Matcher[] m = new Matcher[results.size()];

            for (int i = 0; i < results.size(); i++) {
                m[i] = p.matcher(strArray.get(i));
            }

            for (int i = 0; i < results.size(); i++) {

                if (m[i].find() && m[i].group(1) != null) {
                    /*if(mp.isLooping()){
                        mp.stop();
                    }*/
                    init();
                    String matchstr = m[i].group(1);
                    Log.e("TAG@1", matchstr);
                    int q1 = Integer.parseInt(matchstr);
                    Log.e("TAG@2", String.valueOf(q1));
                    int q = q1;
                    Intent timerIntent = new Intent(getApplicationContext(), Timer.class);
                    timerIntent.putExtra("time", q);
                    startActivity(timerIntent);
                    break;
                } else if (i == results.size() - 1) {
                    if (m2.find()) {
                        /*if(mp.isLooping()){
                            mp.stop();
                        }*/
                        init();
                        String matchstr2 = m2.group(2);
                        String matchstr3 = m2.group(3);
                        int q1 = Integer.parseInt(matchstr2);
                        Log.e("q1", q1 + "");
                        int q2 = Integer.parseInt(matchstr3);
                        Log.e("q2", q2 + "");
                        int q = q1 * 10 + q2;
                        Log.e("q(m2)", q + "");
                        if (q2 < 10) {
                            Intent timerIntent = new Intent(getApplicationContext(), Timer.class);
                            timerIntent.putExtra("time", q);
                            startActivity(timerIntent);
                            break;
                        }
                    } else if (m3.find()) {
                       // mp.stop();
                        init();
                        /*if (sounds == 0) {
                            mps[0].stop();
                        } else if (sounds == 1) {
                            mps[1].stop();
                        } else if (sounds == 2) {
                            mps[2].stop();
                        } else if (sounds == 3) {
                            mps[3].stop();
                        } else if (sounds == 4) {
                            mps[4].stop();
                        } else if (sounds == 5) {
                            mps[5].stop();
                        } else if (sounds == 6) {
                            mps[6].stop();
                        }*/
                        Log.e("TAG@@1", str2);
                        //Day = 1;
                        Intent intent = new Intent(Recognize.this, MainActivity.class);
                        //intent.putExtra("new_time", Day);
                        startActivity(intent);
                        break;
                    } else if (m4.find()) {
                        /*if(mp.isLooping()){
                            mp.stop();
                        }*/
                        init();
                        int q = 1;
                        Log.e("1", "ok");
                        Intent timerIntent = new Intent(Recognize.this, Timer.class);
                        timerIntent.putExtra("time", q);
                        startActivity(timerIntent);
                        break;
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

    @Override
    protected void onStop () {
        super.onStop();
        //mp.stop();
        init();
        Log.e("onStop", "ok");
        finish();
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        Log.e("onDestroy", "ok");
        super.onDestroy();


    }

}

