package com.samsung.android.eventsmonitor;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.TextView;

import com.samsung.android.eventsmonitor.databinding.ActivityAfterEventBinding;


public class AfterEventActivity extends Activity {

    private ActivityAfterEventBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAfterEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String eventType = intent.getStringExtra("eventType");
        String eventTime = intent.getStringExtra("eventTime");

        // UI 업데이트
        binding.textEventType.setText(eventType);
        binding.textEventTime.setText(eventTime);

        // 음원 재생
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.alert_sound);
        mediaPlayer.start();
    }
}
