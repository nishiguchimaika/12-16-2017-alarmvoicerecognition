package com.nishiguchimaika.voicerecognition2;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Recognize extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;
    int again;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int sounds;
    int media;
    SoundPool firstSoundPool;
    int mSoundId;
    SoundPool[] soundPools;
    int[] soundPoolIds;
    int[] soundResourceIds = {
            R.raw.soundd1,
            R.raw.soundd2,
            R.raw.sound9,
            R.raw.soundd4,
            R.raw.sound11,
            R.raw.sound8,
            R.raw.sound10
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        again = 0;
        Intent intent = getIntent();
        again = intent.getIntExtra("again", 0);
        pref = getSharedPreferences("select", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putInt("day", 1);
        editor.apply();
        sounds = pref.getInt("sounds", 0);
        media = pref.getInt("two", 0);

        if (again == 0) {
            //一回目なら
            editor.putInt("two", 0);
            editor.apply();
            firstSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            mSoundId = firstSoundPool.load(getApplicationContext(), R.raw.sound, 1);
            firstSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    firstSoundPool.play(mSoundId, 100, 100, 0, -1, 0);
                }
            });
            firstSoundPool.load(getApplicationContext(), R.raw.sound, 1);
        } else if (again == 1) {
            //二回目以上なら
            editor.putInt("two", 1);
            editor.apply();
            soundPools = new SoundPool[soundResourceIds.length];
            soundPoolIds = new int[soundResourceIds.length];
            for (int i = 0; i < soundPools.length; i++) {
                soundPools[i] = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                soundPools[i].setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        int music = sounds;
                        soundPools[music].play(soundPoolIds[music], 100, 100, 0, -1, 0);
                    }
                });
                soundPoolIds[i] = soundPools[i].load(this, soundResourceIds[i], 1);
            }
        }
        try {
            Intent intent2 = new Intent(
                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent2.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent2.putExtra(
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
            ArrayList<String> strArray = new ArrayList<String>();
            String str2 = results.get(0);
            String rminutes = "at|ad|@|後|あと([0-9]*)(分|分後|分前|ふん|分で|年)";
            String rhourminute = "(at|ad|@|あと|後)([0-9]*)時([0-9]*)(分|分後|分前|ふん|分で|年)";
            String regexWake = "おきます|起きます|おきまーす|起きまーす|ポケモン|起き|おき|います|今|沖縄|自慢|俺ます|秋まーす|はげまーす|大きいもい";
            //String regex4 = "あと一点|あと一転|あと一善|あと一例|あと一|一|対戦|台北";
            Pattern pminutes = Pattern.compile(rminutes);
            Pattern phourminute = Pattern.compile(rhourminute);
            Pattern pwake = Pattern.compile(regexWake);
            //Pattern p4 = Pattern.compile(regex4);
            Matcher hourminute = phourminute.matcher(str2);
            Matcher wakeup = pwake.matcher(str2);
            //Matcher m4 = p4.matcher(str2);

            for (int i = 0; i < results.size(); i++) {
                Log.e("TAG", results.get(i));
                strArray.add(results.get(i));
            }

            Matcher[] minutes = new Matcher[results.size()];
            for (int i = 0; i < results.size(); i++) {
                minutes[i] = pminutes.matcher(strArray.get(i));
            }

            for (int i = 0; i < results.size(); i++) {

                if (minutes[i].find() && minutes[i].group(1) != null) {
                    stop();
                    String matchstr = minutes[i].group(1);
                    int q1 = Integer.parseInt(matchstr);
                    Log.e("TAG@2", String.valueOf(q1));
                    int q = q1;
                    Intent timerIntent = new Intent(getApplicationContext(), Timer.class);
                    timerIntent.putExtra("time", q);
                    startActivity(timerIntent);
                    break;
                } else if (i == results.size() - 1) {
                    if (hourminute.find()) {
                        stop();
                        String matchstr2 = hourminute.group(2);
                        String matchstr3 = hourminute.group(3);
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
                    } else if (wakeup.find()) {
                        stop();
                        editor.putInt("ala", 1);
                        editor.apply();
                        Intent intent = new Intent(Recognize.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    } /*else if (m4.find()) {
                        stop();
                        int q = 1;
                        Log.e("1", "ok");
                        Intent timerIntent = new Intent(Recognize.this, Timer.class);
                        timerIntent.putExtra("time", q);
                        startActivity(timerIntent);
                        break;
                    }*/
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
                } else {
                    Intent intent = new Intent(Recognize.this, Recognize.class);
                    startActivity(intent);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void stop(){
        if (firstSoundPool != null) {
            firstSoundPool.stop(mSoundId);
            firstSoundPool.unload(mSoundId);
            firstSoundPool.release();
        }
        if (soundPools != null) {
            for (int e = 0; e < soundPools.length; e++) {
                soundPools[e].stop(soundPoolIds[e]);
                soundPools[e].unload(soundPoolIds[e]);
                soundPools[e].release();
            }
        }
    }

    @Override
    protected void onStop () {
        super.onStop();
        if (firstSoundPool != null) {
            firstSoundPool.stop(mSoundId);
            firstSoundPool.unload(mSoundId);
            firstSoundPool.release();
        }
        if (soundPools != null) {
            for (int e = 0; e < soundPools.length; e++) {
                soundPools[e].stop(soundPoolIds[e]);
                soundPools[e].unload(soundPoolIds[e]);
                soundPools[e].release();
            }
        }
        Log.e("onStop", "ok");
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("RecognizeDestroy", "okay");
        finish();
    }

}

