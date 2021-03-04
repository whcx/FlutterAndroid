package com.cxd.flutterandroid;

import android.app.Application;

import java.sql.Driver;

import io.flutter.FlutterInjector;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.embedding.engine.loader.FlutterLoader;
import io.flutter.view.FlutterMain;

public class MyApplication extends Application {
    FlutterEngine flutterEngine;
    static final String FLUTTER_ENGINE_ID = "flutter_engine_id";
    public MyApplication() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        flutterEngine = new FlutterEngine(getApplicationContext());
        flutterEngine.getDartExecutor().executeDartEntrypoint(
                new DartExecutor.DartEntrypoint(
                        FlutterInjector.instance().flutterLoader().findAppBundlePath(),"main"));
        flutterEngine.getNavigationChannel().setInitialRoute("/");
        FlutterEngineCache.getInstance().put(FLUTTER_ENGINE_ID, flutterEngine);
    }
}
