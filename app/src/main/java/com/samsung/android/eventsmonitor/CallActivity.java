package com.samsung.android.eventsmonitor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.samsung.android.eventsmonitor.databinding.ActivityCallBinding;

public class CallActivity extends Activity {

    private TextView mTextView;
    private ActivityCallBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mTextView = binding.text;
    }
}