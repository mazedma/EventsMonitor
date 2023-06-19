package com.samsung.android.eventsmonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.samsung.android.eventsmonitor.databinding.ActivityAfterEventBinding;

public class AfterEventActivity extends Activity {

    private ActivityAfterEventBinding binding;
    private CountDownTimer countDownTimer;
    private boolean stopButtonClicked = false;
    private String eventTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAfterEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String eventType = intent.getStringExtra("eventType");
        eventTime = intent.getStringExtra("eventTime");


        binding.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 화면 전환을 중지하고 현재 액티비티를 종료
                stopButtonClicked = true;
                stopScreenTransition();
                finish();
            }
        });

        // 일정 시간이 지난 후 화면 전환을 자동으로 수행
        long timeRemainingInMillis = 15000; // 15초
        countDownTimer = new CountDownTimer(timeRemainingInMillis, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                // 남은 시간을 업데이트
                String timeFormatted = formatTime(millisUntilFinished);
                binding.textTimeRamaining.setText(timeFormatted);
            }

            @Override
            public void onFinish() {
                if (!stopButtonClicked) {
                    startNextScreen();
                }
            }
        };
        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 화면 종료 시 화면 전환을 중지
        stopScreenTransition();
    }

    private void stopScreenTransition() {
        // 카운트다운 타이머를 취소하여 화면 전환을 중지
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void startNextScreen() {
        // 다음 화면으로 전환
        Intent intent = new Intent(AfterEventActivity.this, SoundActivity.class);
        intent.putExtra("eventTime", eventTime);
        intent.putExtra("elapsedTime", eventTime);
        startActivity(intent);
        finish();
    }

    private String formatTime(long millis) {
        int seconds = (int) (millis / 1000) % 60;
        int milliseconds = (int) (millis % 1000);
        return String.format("%02d:%03d", seconds, milliseconds);
    }
}
