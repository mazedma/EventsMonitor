package com.samsung.android.eventsmonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.samsung.android.eventsmonitor.databinding.ActivityCallBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CallActivity extends AppCompatActivity {

    private ActivityCallBinding binding;
    private Handler handler;
    private long eventTimeInMillis;
    private Runnable updateElapsedTimeRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // SoundActivity로부터 전달받은 데이터 가져오기
        String eventTime = getIntent().getStringExtra("eventTime");
        String elapsedTime = getIntent().getStringExtra("elapsedTime");
        eventTimeInMillis = convertEventTimeToMillis(eventTime);

        handler = new Handler();

        // 경과 시간 업데이트
        updateElapsedTime();

        // 경과 시간을 1밀리초마다 업데이트하는 Runnable 설정
        updateElapsedTimeRunnable = new Runnable() {
            @Override
            public void run() {
                updateElapsedTime();
                handler.postDelayed(this, 1); // 1밀리초마다 업데이트
            }
        };

        handler.post(updateElapsedTimeRunnable);

        // 다른 작업 수행
        // ...
    }

    private void updateElapsedTime() {
        // 현재 시간과 eventTimeInMillis의 차이를 계산하여 경과 시간을 구합니다.
        long elapsedTimeInMillis = System.currentTimeMillis() - eventTimeInMillis;

        // 경과 시간을 분, 초, 밀리초로 변환합니다.
        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTimeInMillis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTimeInMillis) % 60;
        long milliseconds = elapsedTimeInMillis % 1000;

        // 경과 시간을 문자열로 포맷합니다.
        String elapsedTime = String.format(Locale.US, "%02d:%02d:%03d", minutes, seconds, milliseconds);

        // TextView에 경과 시간을 설정합니다.
        binding.textViewElapsedTime.setText(elapsedTime);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 핸들러의 업데이트 Runnable을 제거합니다.
        handler.removeCallbacks(updateElapsedTimeRunnable);
    }
}
