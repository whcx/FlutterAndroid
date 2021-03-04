package com.cxd.flutterandroid;

import android.content.Intent;
import android.util.Log;
import android.view.ViewGroup;

import androidx.activity.ComponentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import io.flutter.embedding.android.FlutterView;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.renderer.FlutterUiDisplayListener;
import io.flutter.plugin.platform.PlatformPlugin;

public class FlutterViewEngine implements LifecycleObserver, FlutterUiDisplayListener {
    static final String TAG = FlutterViewEngine.class.getSimpleName();
    private FlutterView flutterView;
    private ComponentActivity componentActivity;
    private PlatformPlugin platformPlugin;
    private FlutterEngine flutterEngine;
    private ViewGroup mContentView;

    static private void Log(String string)
    {
        Log.e(TAG, string);
    }
    public FlutterViewEngine(FlutterEngine engine) {
        Log("FlutterViewEngine....");
        flutterEngine = engine;
    }

    @Override
    public void onFlutterUiDisplayed() {
        Log("onFlutterUiDisplayed....");
    }

    @Override
    public void onFlutterUiNoLongerDisplayed() {
        Log("onFlutterUiNoLongerDisplayed....");
    }

    private void hootActivityAndView() {
        Log("hootActivityAndView....");
        if (componentActivity != null) {
            if (flutterView != null) {
                platformPlugin = new PlatformPlugin(componentActivity, flutterEngine.getPlatformChannel());
                flutterEngine.getActivityControlSurface().attachToActivity(componentActivity, componentActivity.getLifecycle());
                flutterView.attachToFlutterEngine(flutterEngine);
                componentActivity.getLifecycle().addObserver(this);
                mContentView= componentActivity.getWindow().getDecorView().findViewById(android.R.id.content);
                mContentView.addView(flutterView);
                flutterView.addOnFirstFrameRenderedListener(this);
            }
        }
    }

    private void unhookActivityAndView() {
        Log("unhookActivityAndView....");
        if (componentActivity != null) {
            componentActivity.getLifecycle().removeObserver(this);
        }
        flutterEngine.getActivityControlSurface().detachFromActivity();
        if (platformPlugin != null) {
            platformPlugin.destroy();
            platformPlugin = null;
        }
        flutterEngine.getLifecycleChannel().appIsDetached();
        if (flutterView != null) {
            mContentView.removeView(flutterView);
            flutterView.removeOnFirstFrameRenderedListener(this);
            flutterView.detachFromFlutterEngine();
        }
    }

    public void attachToActivity(ComponentActivity activity) {
        Log("attachToActivity....");
        componentActivity = activity;
        if (flutterView != null) {
            hootActivityAndView();
        }
    }

    public void detachActivity() {
        Log("detachActivity....");
        if (flutterView != null) {
            unhookActivityAndView();
        }
        flutterView = null;
    }

    public void attachFlutterView(FlutterView flutterView_) {
        Log("attachFlutterView....");
        flutterView = flutterView_;
        if (componentActivity != null) {
            hootActivityAndView();
        }
    }

    public void detachFlutterView() {
        Log("detachFlutterView....");
        unhookActivityAndView();
        flutterView = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void resumeActivity() {
        Log("resumeActivity....");
        if (componentActivity != null) {
            flutterEngine.getLifecycleChannel().appIsResumed();
        }
        if (platformPlugin != null) {
            platformPlugin.updateSystemUiOverlays();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void pauseActivity() {
        Log("pauseActivity....");
        if (componentActivity != null) {
            flutterEngine.getLifecycleChannel().appIsInactive();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void stopActivity() {
        Log("stopActivity....");
        if (componentActivity != null) {
            flutterEngine.getLifecycleChannel().appIsPaused();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void destroyActivity() {
        Log("destroyActivity....");
        detachActivity();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (componentActivity != null && flutterView != null) {
            flutterEngine.getActivityControlSurface().onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (componentActivity != null && flutterView != null) {
            flutterEngine.getActivityControlSurface().onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onUserLeaveHint() {
        if (componentActivity != null && flutterView != null) {
            flutterEngine.getActivityControlSurface().onUserLeaveHint();
        }
    }
}
