package com.samsung.android.eventsmonitor;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

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

    private boolean activityStopped = false;

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
                startSeizureEndActivity();
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
    }

    private void startSeizureEndActivity() {
        if (!activityStopped) {
            activityStopped = true;
            handler.removeCallbacks(stopActivityRunnable);
            String elapsedTime = binding.textViewStopwatch.getText().toString();
            String eventTime = convertMillisToEventTime(eventTimeInMillis);
            Intent intent = new Intent(SoundActivity.this, SeizureEndActivity.class);
            intent.putExtra("elapsedTime", elapsedTime);
            intent.putExtra("eventTime", eventTime);
            startActivity(intent);
            finish();
        }
    }

//    private void startCallActivity() {
//        if (!activityStopped) {
//            activityStopped = true;
//            handler.removeCallbacks(stopActivityRunnable);
//            String eventTime = convertMillisToEventTime(eventTimeInMillis);
//            Intent intent = new Intent(SoundActivity.this, CallActivity.class);
//            intent.putExtra("eventTime", eventTime);
//            startActivity(intent);
//            finish();
//        }
//    }

    private void startCallActivity() {
        if (!activityStopped) {
            activityStopped = true;
            handler.removeCallbacks(stopActivityRunnable);

            // 119에 전화를 거는 인텐트를 생성
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:01035237687"));
            startActivity(callIntent);

            finish();
        }
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
            startCallActivity();
        }
    };

    private void updateStopwatchText(long elapsedTimeInMillis) {
        long timeElapsedInMillis = System.currentTimeMillis() - eventTimeInMillis;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeElapsedInMillis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeElapsedInMillis) % 60;
        long milliseconds = timeElapsedInMillis % 1000;

        String timeString = String.format(Locale.US, "%02d:%02d:%03d", minutes, seconds, milliseconds);
        binding.textViewStopwatch.setText(timeString);

        // eventTime 표시
        String eventTime = convertMillisToEventTime(eventTimeInMillis);

        // currentTime 표시
        String currentTime = convertMillisToEventTime(System.currentTimeMillis());
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yy, h:mm a", Locale.US);
        try {
            Date date = dateFormat.parse(eventTime);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String convertMillisToEventTime(long millis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yy, h:mm a", Locale.US);
        return dateFormat.format(millis);
    }
}
