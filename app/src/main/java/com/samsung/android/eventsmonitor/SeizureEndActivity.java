package com.samsung.android.eventsmonitor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.samsung.android.eventsmonitor.databinding.ActivitySeizureEndBinding;

public class SeizureEndActivity extends Activity {

    private ActivitySeizureEndBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySeizureEndBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // SoundActivity로부터 전달받은 데이터 가져오기
        String eventTime = getIntent().getStringExtra("eventTime");
        String elapsedTime = getIntent().getStringExtra("elapsedTime");

        // 텍스트뷰에 데이터 설정
        binding.textEventTime.setText(eventTime);
        binding.textElapsedTime.setText(elapsedTime);
    }
}
