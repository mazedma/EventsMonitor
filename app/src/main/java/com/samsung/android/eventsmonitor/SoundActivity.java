package com.samsung.android.eventsmonitor;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.samsung.android.eventsmonitor.databinding.ActivitySoundBinding;

public class SoundActivity extends Activity {

    private ActivitySoundBinding binding;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySoundBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 음원 재생
        mediaPlayer = MediaPlayer.create(this, R.raw.alert_sound);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        binding.stopSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 음원 재생 중지
                stopSound();
                finish();
            }
        });
    }

    private void stopSound() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 액티비티 종료 시 음원 재생 중지
        stopSound();
    }
}
