package com.solutions.zoref.npat;

import android.app.TimePickerDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    HashSet<String> letters = new HashSet<>();
    public void startButton(View view){



        TextView mxtxt = (TextView)findViewById(R.id.ralpha);
        final EditText mins_ = (EditText)findViewById(R.id.mins);
        final EditText secs_ = (EditText)findViewById(R.id.secs);
        final Button startButton_= (Button)findViewById(R.id.startButton);

        final TextView timertext = (TextView)findViewById(R.id.timer);
        String letter = getAlphabet();
        if(letter=="-"){
            Context context = getApplicationContext();
            CharSequence text = "No more letters to show";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }else{
            mxtxt.setText(letter);
        }
        String minutes__ = mins_.getText().toString();
        String seconds__ = secs_.getText().toString();
        int minutes = Integer.valueOf(minutes__);
        int seconds = Integer.valueOf(seconds__);
        mins_.setEnabled(false);
        secs_.setEnabled(false);
        startButton_.setEnabled(false);
        long milliseconds = ((minutes*60)*1000)+(seconds*1000);
        //Toast for checking values
        /*Context context = getApplicationContext();
        CharSequence text = String.valueOf(minutes)+":"+String.valueOf(seconds);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();*/
        CountDownTimer countDownTimer = new CountDownTimer(milliseconds, 1000) {

            public void onTick(long millisUntilFinished) {
                String text = String.format(Locale.getDefault(), "%02d: %02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                timertext.setText(text);
            }

            public void onFinish() {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                        toneGen1.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD,1000);

                        mins_.setEnabled(true);
                        secs_.setEnabled(true);
                        startButton_.setEnabled(true);
                    }
                }, 1000);
            }
        }.start();
        /*Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);*/

    }
/*    public void settimeButton(View view){
        // Get Current Time
        final TextView timelimit = (TextView)findViewById(R.id.timelimit);
        final Calendar c = Calendar.getInstance();
        int mHour =0;
        int mMinute =0;

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view,
                                          int minute,int seconds) {

                        timelimit.setText(minute + ":" + seconds);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }*/
    public String getAlphabet(){
        if(letters.size()==26){
            return "-";
        }
        Random rnd = new Random();
        char c = (char) (rnd.nextInt(26) + 'a');

        if(letters.contains(String.valueOf(c))){
            getAlphabet();
        }
        letters.add(String.valueOf(c));
        return String.valueOf(c).toUpperCase();

    }
}
