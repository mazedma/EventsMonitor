package com.samsung.android.eventsmonitor;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.samsung.android.eventsmonitor.CallActivity;
import com.samsung.android.eventsmonitor.databinding.ActivitySoundBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SoundActivity extends AppCompatActivity {
    private ActivitySoundBinding binding;
    private Handler handler;
    private MediaPlayer mediaPlayer;

    private long eventTimeInMillis;
    private long startTimeInMillis;
    private boolean stopwatchRunning;
    private static final long MAX_DURATION_MILLIS = TimeUnit.SECONDS.toMillis(15); // 신고까지 걸리는 시간

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySoundBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 인텐트에서 eventTime 가져오기
        String eventTime = getIntent().getStringExtra("eventTime");
        eventTimeInMillis = convertEventTimeToMillis(eventTime);
        startTimeInMillis = System.currentTimeMillis();
        stopwatchRunning = true;

        handler = new Handler();

        binding.stopSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSound();
            }
        });

        playSound(); // 소리 재생

        handler.post(updateStopwatchRunnable);
        handler.postDelayed(stopActivityRunnable, MAX_DURATION_MILLIS);
    }

    private void playSound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.alert_sound);
        mediaPlayer.start();
    }

    private void stopSound() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        stopwatchRunning = false;
        finish();
    }

    private Runnable updateStopwatchRunnable = new Runnable() {
        @Override
        public void run() {
            if (stopwatchRunning) {
                long elapsedTimeInMillis = System.currentTimeMillis() - startTimeInMillis;
                updateStopwatchText(elapsedTimeInMillis);
            }

            handler.postDelayed(this, 1); // 1밀리초마다 업데이트
        }
    };

    private Runnable stopActivityRunnable = new Runnable() {
        @Override
        public void run() {
            stopSound();
            Intent intent = new Intent(SoundActivity.this, CallActivity.class);
            startActivity(intent);
        }
    };

    private void updateStopwatchText(long elapsedTimeInMillis) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTimeInMillis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTimeInMillis) % 60;
        long milliseconds = elapsedTimeInMillis % 1000;

        String timeString = String.format(Locale.US, "%02d:%02d:%03d", minutes, seconds, milliseconds);
        binding.textViewStopwatch.setText(timeString);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacks(updateStopwatchRunnable);
        handler.removeCallbacks(stopActivityRunnable);
    }

    private long convertEventTimeToMillis(String eventTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy, h:mma", Locale.US);
        try {
            Date date = dateFormat.parse(eventTime);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
