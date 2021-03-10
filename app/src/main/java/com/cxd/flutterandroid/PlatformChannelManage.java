package com.cxd.flutterandroid;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.net.MulticastSocket;

import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StringCodec;

public class PlatformChannelManage {
    //PlatformLog._platform_log_channel keep the same.
    public static final String LOG_CHANNEL = "platform.channel.manage.log";
    private static MethodChannel mPlatformLog;

    static void registerLog() {
        if (mPlatformLog != null)
            return;
        mPlatformLog = new MethodChannel(
                FlutterEngineCache.getInstance().get(MyApplication.FLUTTER_ENGINE_ID).getDartExecutor(), LOG_CHANNEL);
        mPlatformLog.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
                final String TAG = call.argument("Tag");
                final String Msg = call.argument("Msg");
                //PlatformLog.logMethodNameV keep the same.
                if (call.method.equals("LOGV")) {
                    Log.v(TAG, Msg);
                } else if (call.method.equals("LOGD")) {
                    Log.d(TAG, Msg);
                } else if (call.method.equals("LOGI")) {
                    Log.i(TAG, Msg);
                } else if (call.method.equals("LOGW")) {
                    Log.d(TAG, Msg);
                } else if (call.method.equals("LOGE")) {
                    Log.e(TAG, Msg);
                }
            }
        });
    }

//    mPlatformLog = new BasicMessageChannel<>(
//            FlutterEngineCache.getInstance().get(MyApplication.FLUTTER_ENGINE_ID).getDartExecutor(),
//    LOG_CHANNEL, StringCodec.INSTANCE);
//        mPlatformLog.setMessageHandler(new BasicMessageChannel.MessageHandler<String>() {
//        @Override
//        public void onMessage(@Nullable String message, @NonNull BasicMessageChannel.Reply<String> reply) {
//
//        }
//    });
}
