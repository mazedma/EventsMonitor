// Generated by view binder compiler. Do not edit!
package com.samsung.android.eventsmonitor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.wear.widget.BoxInsetLayout;
import com.samsung.android.eventsmonitor.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySoundBinding implements ViewBinding {
  @NonNull
  private final BoxInsetLayout rootView;

  @NonNull
  public final Button stopSoundButton;

  @NonNull
  public final TextView textView;

  @NonNull
  public final TextView textViewStopwatch;

  private ActivitySoundBinding(@NonNull BoxInsetLayout rootView, @NonNull Button stopSoundButton,
      @NonNull TextView textView, @NonNull TextView textViewStopwatch) {
    this.rootView = rootView;
    this.stopSoundButton = stopSoundButton;
    this.textView = textView;
    this.textViewStopwatch = textViewStopwatch;
  }

  @Override
  @NonNull
  public BoxInsetLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySoundBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySoundBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_sound, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySoundBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.stopSoundButton;
      Button stopSoundButton = ViewBindings.findChildViewById(rootView, id);
      if (stopSoundButton == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.textViewStopwatch;
      TextView textViewStopwatch = ViewBindings.findChildViewById(rootView, id);
      if (textViewStopwatch == null) {
        break missingId;
      }

      return new ActivitySoundBinding((BoxInsetLayout) rootView, stopSoundButton, textView,
          textViewStopwatch);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
