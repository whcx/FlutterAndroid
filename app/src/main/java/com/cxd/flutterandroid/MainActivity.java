package com.cxd.flutterandroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import io.flutter.FlutterInjector;
import io.flutter.embedding.android.FlutterFragment;
import io.flutter.embedding.android.FlutterSurfaceView;
import io.flutter.embedding.android.FlutterView;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.embedding.engine.loader.FlutterLoader;
import io.flutter.embedding.engine.renderer.FlutterUiDisplayListener;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.StringCodec;
import io.flutter.view.FlutterMain;

public class MainActivity extends AppCompatActivity {
    FlutterEngine flutterEngine;
    private FlutterSurfaceView mFlutterSurfaceView;
    private FlutterView flutterView;
    private BasicMessageChannel<String> messageChannel;
    private int counter;
    private static final String CHANNEL = "increment";
    private static final String EMPTY_MESSAGE = "";
    private static final String PING = "ping";

    private String[] getArgsFromIntent(Intent intent) {
        // Before adding more entries to this list, consider that arbitrary
        // Android applications can generate intents with extra data and that
        // there are many security-sensitive args in the binary.
        ArrayList<String> args = new ArrayList<>();
        if (intent.getBooleanExtra("trace-startup", false)) {
            args.add("--trace-startup");
        }
        if (intent.getBooleanExtra("start-paused", false)) {
            args.add("--start-paused");
        }
        if (intent.getBooleanExtra("enable-dart-profiling", false)) {
            args.add("--enable-dart-profiling");
        }
        if (!args.isEmpty()) {
            String[] argsArray = new String[args.size()];
            return args.toArray(argsArray);
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] args = getArgsFromIntent(getIntent());
        //if (flutterEngine == null)
        {
            flutterEngine = new FlutterEngine(this);
            flutterEngine.getDartExecutor().executeDartEntrypoint(
                    DartExecutor.DartEntrypoint.createDefault()
            );
        }
        setContentView(R.layout.activity_main);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            //supportActionBar.hide();
        }



        messageChannel = new BasicMessageChannel<>(flutterEngine.getDartExecutor(), CHANNEL, StringCodec.INSTANCE);
        messageChannel.
                setMessageHandler(new BasicMessageChannel.MessageHandler<String>() {
                    @Override
                    public void onMessage(String s, BasicMessageChannel.Reply<String> reply) {
                        onFlutterIncrement();
                        reply.reply(EMPTY_MESSAGE);
                    }
                });

//        Button fab = new Button(this);
//        LinearLayout.LayoutParams fabParams = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.setMargins(0,1000,0,0);
//        fab.setLayoutParams(fabParams);
//        fab.setText("+++");
//        frameLayout.addView(fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendAndroidIncrement();
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mFlutterSurfaceView = new FlutterSurfaceView(this, true);
        mFlutterSurfaceView.setZOrderOnTop(true);
        mFlutterSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        flutterView = new FlutterView(this);//mFlutterSurfaceView
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0,0,0);
//        flutterView.setBackgroundColor(Color.argb(255,255,255,255));
        flutterView.setLayoutParams(layoutParams);
        FrameLayout frameLayout = findViewById(R.id.layout);
        frameLayout.addView(flutterView);
        flutterView.attachToFlutterEngine(flutterEngine);
    }

    private void onFlutterIncrement() {
        counter++;
        TextView textView = findViewById(R.id.text);
        String value = "Flutter button tapped " + counter + (counter == 1 ? " time" : " times");
        textView.setText(value);
    }

    private void sendAndroidIncrement() {
        messageChannel.send(PING);
    }
}