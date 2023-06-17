package com.samsung.android.eventsmonitor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.samsung.android.eventsmonitor.databinding.ActivitySeizureEndBinding;

public class SeizureEndActivity extends Activity {

    private TextView mTextView;
    private ActivitySeizureEndBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySeizureEndBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mTextView = binding.text;
    }
}