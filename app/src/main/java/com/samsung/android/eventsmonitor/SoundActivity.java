package com.samsung.android.eventsmonitor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.samsung.android.eventsmonitor.databinding.ActivitySoundBinding;

public class SoundActivity extends Activity {

    private TextView mTextView;
    private ActivitySoundBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySoundBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mTextView = binding.text;
    }
}