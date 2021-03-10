package com.cxd.flutterandroid;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.flutter.embedding.android.FlutterView;
import io.flutter.embedding.engine.FlutterEngineCache;

import static com.cxd.flutterandroid.MyApplication.FLUTTER_ENGINE_ID;

public class MainActivityFlutter extends AppCompatActivity {
    private FlutterView flutterView;
    private FlutterViewEngine flutterViewEngine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        PlatformChannelManage.registerLog();
        flutterViewEngine = new FlutterViewEngine(FlutterEngineCache.getInstance().get(FLUTTER_ENGINE_ID));
        flutterView = new FlutterView(this);
        flutterViewEngine.attachFlutterView(flutterView);
        flutterViewEngine.attachToActivity(this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        flutterViewEngine.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        flutterViewEngine.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onUserLeaveHint() {
        flutterViewEngine.onUserLeaveHint();
        super.onUserLeaveHint();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}