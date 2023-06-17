// Generated by view binder compiler. Do not edit!
package com.samsung.android.eventsmonitor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
  public final TextView text;

  private ActivitySoundBinding(@NonNull BoxInsetLayout rootView, @NonNull TextView text) {
    this.rootView = rootView;
    this.text = text;
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
      id = R.id.text;
      TextView text = ViewBindings.findChildViewById(rootView, id);
      if (text == null) {
        break missingId;
      }

      return new ActivitySoundBinding((BoxInsetLayout) rootView, text);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
