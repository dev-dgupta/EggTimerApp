package com.example.eggtimerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerText;
    SeekBar timerSeekBar;
    boolean counterIsActive = false;
    Button goButton;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = findViewById(R.id.goButton);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerSeekBar.setProgress(30);
        timerSeekBar.setMax(600);

        timerText = findViewById(R.id.timerText);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void updateTimer(int progress) {
        int minutes = progress / 60;
        int seconds = progress - (minutes * 60);
        String secondString = Integer.toString(seconds);
        if (seconds < 10) {
            secondString = "0" + secondString;
        }
        timerText.setText(minutes + ":" + secondString);
    }

    public void startTimer(View view) {

        if (counterIsActive) {
            resetTimer();
        } else {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            goButton.setText("STOP!");
            //100s added so that the time taken to start button and end timer is not lost in timer
            int futureInMilli = (timerSeekBar.getProgress() * 1000) + 100;
            countDownTimer = new CountDownTimer(futureInMilli, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    int timeLeft = (int) (millisUntilFinished / 1000);
                    updateTimer(timeLeft);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.air_horn);
                    mediaPlayer.start();

                    resetTimer();
                }
            }.start();
        }
    }

    private void resetTimer() {
        timerSeekBar.setProgress(30);
        timerText.setText("0:30");
        countDownTimer.cancel();
        timerSeekBar.setEnabled(true);
        counterIsActive = false;
        goButton.setText("GO!");
    }

}