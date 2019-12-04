package com.dauntlessdev.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView counter;
    int start = 2;
    int maxCount = start*15000;
    boolean clicked = false;
    Button goButton;
    MediaPlayer mp;

    CountDownTimer cdt = new CountDownTimer(maxCount,1000){
        @Override
        public void onTick(long millisUntilFinished) {
            setTime(millisUntilFinished/1000);
        }

        @Override
        public void onFinish() {
            mp.start();
            resetTimer();
        }
    };

    public void goButton(View view){
        Button bview = (Button) view;
        if(!clicked){
            cdt.start();
            bview.setText("STOP");
            clicked = true;
        }else{
            cdt.cancel();
            bview.setText("GO");
            clicked = false;
        }
    }

    public void resetTimer(){
        goButton.setText("GO");
        clicked = false;
    }

    public void setTime(long second){
        int min = 0;
        if (second/60 >= 1){
            min = (int) second/60;
            second = second - min*60;
        }
        counter.setText(min+":"+String.format("%02d",second));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar seekBar = findViewById(R.id.seekBar);
        counter = findViewById(R.id.counterTextView);
        mp = MediaPlayer.create(this, R.raw.crow);

        seekBar.setMax(20);
        seekBar.setProgress(start);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                goButton = findViewById(R.id.goButton);
                resetTimer();
                cdt.cancel();
                if (progress == 0) ++progress;
                setTime(progress*15);
                maxCount = progress*15000;
                cdt = new CountDownTimer(maxCount,1000){
                    @Override
                    public void onTick(long millisUntilFinished) {
                        setTime(millisUntilFinished/1000);
                    }

                    @Override
                    public void onFinish() {
                        mp.start();
                        resetTimer();
                    }
                };
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
