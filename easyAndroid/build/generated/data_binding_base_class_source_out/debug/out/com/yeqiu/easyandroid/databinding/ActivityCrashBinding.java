// Generated by view binder compiler. Do not edit!
package com.yeqiu.easyandroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yeqiu.easyandroid.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityCrashBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout sRoot;

  @NonNull
  public final ScrollView sScrollView;

  @NonNull
  public final TextView sTextMessage;

  @NonNull
  public final RelativeLayout sToolbar;

  @NonNull
  public final TextView sTvBrand;

  @NonNull
  public final TextView sTvClassName;

  @NonNull
  public final TextView sTvCpuAbi;

  @NonNull
  public final TextView sTvExceptionType;

  @NonNull
  public final TextView sTvFullException;

  @NonNull
  public final TextView sTvLineNumber;

  @NonNull
  public final TextView sTvMethodName;

  @NonNull
  public final TextView sTvModel;

  @NonNull
  public final TextView sTvShare;

  @NonNull
  public final TextView sTvTime;

  @NonNull
  public final TextView sTvVersion;

  @NonNull
  public final TextView sTvVersionCode;

  @NonNull
  public final TextView sTvVersionName;

  private ActivityCrashBinding(@NonNull LinearLayout rootView, @NonNull LinearLayout sRoot,
      @NonNull ScrollView sScrollView, @NonNull TextView sTextMessage,
      @NonNull RelativeLayout sToolbar, @NonNull TextView sTvBrand, @NonNull TextView sTvClassName,
      @NonNull TextView sTvCpuAbi, @NonNull TextView sTvExceptionType,
      @NonNull TextView sTvFullException, @NonNull TextView sTvLineNumber,
      @NonNull TextView sTvMethodName, @NonNull TextView sTvModel, @NonNull TextView sTvShare,
      @NonNull TextView sTvTime, @NonNull TextView sTvVersion, @NonNull TextView sTvVersionCode,
      @NonNull TextView sTvVersionName) {
    this.rootView = rootView;
    this.sRoot = sRoot;
    this.sScrollView = sScrollView;
    this.sTextMessage = sTextMessage;
    this.sToolbar = sToolbar;
    this.sTvBrand = sTvBrand;
    this.sTvClassName = sTvClassName;
    this.sTvCpuAbi = sTvCpuAbi;
    this.sTvExceptionType = sTvExceptionType;
    this.sTvFullException = sTvFullException;
    this.sTvLineNumber = sTvLineNumber;
    this.sTvMethodName = sTvMethodName;
    this.sTvModel = sTvModel;
    this.sTvShare = sTvShare;
    this.sTvTime = sTvTime;
    this.sTvVersion = sTvVersion;
    this.sTvVersionCode = sTvVersionCode;
    this.sTvVersionName = sTvVersionName;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityCrashBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityCrashBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_crash, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityCrashBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      LinearLayout sRoot = (LinearLayout) rootView;

      id = R.id.sScrollView;
      ScrollView sScrollView = ViewBindings.findChildViewById(rootView, id);
      if (sScrollView == null) {
        break missingId;
      }

      id = R.id.sTextMessage;
      TextView sTextMessage = ViewBindings.findChildViewById(rootView, id);
      if (sTextMessage == null) {
        break missingId;
      }

      id = R.id.sToolbar;
      RelativeLayout sToolbar = ViewBindings.findChildViewById(rootView, id);
      if (sToolbar == null) {
        break missingId;
      }

      id = R.id.sTvBrand;
      TextView sTvBrand = ViewBindings.findChildViewById(rootView, id);
      if (sTvBrand == null) {
        break missingId;
      }

      id = R.id.sTvClassName;
      TextView sTvClassName = ViewBindings.findChildViewById(rootView, id);
      if (sTvClassName == null) {
        break missingId;
      }

      id = R.id.sTvCpuAbi;
      TextView sTvCpuAbi = ViewBindings.findChildViewById(rootView, id);
      if (sTvCpuAbi == null) {
        break missingId;
      }

      id = R.id.sTvExceptionType;
      TextView sTvExceptionType = ViewBindings.findChildViewById(rootView, id);
      if (sTvExceptionType == null) {
        break missingId;
      }

      id = R.id.sTvFullException;
      TextView sTvFullException = ViewBindings.findChildViewById(rootView, id);
      if (sTvFullException == null) {
        break missingId;
      }

      id = R.id.sTvLineNumber;
      TextView sTvLineNumber = ViewBindings.findChildViewById(rootView, id);
      if (sTvLineNumber == null) {
        break missingId;
      }

      id = R.id.sTvMethodName;
      TextView sTvMethodName = ViewBindings.findChildViewById(rootView, id);
      if (sTvMethodName == null) {
        break missingId;
      }

      id = R.id.sTvModel;
      TextView sTvModel = ViewBindings.findChildViewById(rootView, id);
      if (sTvModel == null) {
        break missingId;
      }

      id = R.id.sTvShare;
      TextView sTvShare = ViewBindings.findChildViewById(rootView, id);
      if (sTvShare == null) {
        break missingId;
      }

      id = R.id.sTvTime;
      TextView sTvTime = ViewBindings.findChildViewById(rootView, id);
      if (sTvTime == null) {
        break missingId;
      }

      id = R.id.sTvVersion;
      TextView sTvVersion = ViewBindings.findChildViewById(rootView, id);
      if (sTvVersion == null) {
        break missingId;
      }

      id = R.id.sTvVersionCode;
      TextView sTvVersionCode = ViewBindings.findChildViewById(rootView, id);
      if (sTvVersionCode == null) {
        break missingId;
      }

      id = R.id.sTvVersionName;
      TextView sTvVersionName = ViewBindings.findChildViewById(rootView, id);
      if (sTvVersionName == null) {
        break missingId;
      }

      return new ActivityCrashBinding((LinearLayout) rootView, sRoot, sScrollView, sTextMessage,
          sToolbar, sTvBrand, sTvClassName, sTvCpuAbi, sTvExceptionType, sTvFullException,
          sTvLineNumber, sTvMethodName, sTvModel, sTvShare, sTvTime, sTvVersion, sTvVersionCode,
          sTvVersionName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
