import 'package:flutter/services.dart';

class PlatformLog {
  //PlatformChannelManage.LOG_CHANNEL keep the same.
  static const MethodChannel _platform_log_channel = const MethodChannel("platform.channel.manage.log");
  static const String _LogMethodNameV = "LOGV";
  static const String _LogMethodNameI = "LOGI";
  static const String _LogMethodNameD = "LOGD";
  static const String _LogMethodNameW = "LOGW";
  static const String _LogMethodNameE = "LOGE";
  static const String _LogArgumentsTAG = "Tag";
  static const String _LogArgumentsMsg = "Msg";
  static const String _LogDefaultTag = "Flutter";

  static void v({String tag = _LogDefaultTag, String message}) {
    _platform_log_channel.invokeMethod(_LogMethodNameV, {_LogArgumentsTAG:tag, _LogArgumentsMsg:message});
  }
  static void i({String tag = _LogDefaultTag, String message}) {
    _platform_log_channel.invokeMethod(_LogMethodNameI, {_LogArgumentsTAG:tag, _LogArgumentsMsg:message});
  }
  static void d({String tag = _LogDefaultTag, String message}) {
    _platform_log_channel.invokeMethod(_LogMethodNameD, {_LogArgumentsTAG:tag, _LogArgumentsMsg:message});
  }
  static void w({String tag = _LogDefaultTag, String message}) {
    _platform_log_channel.invokeMethod(_LogMethodNameW, {_LogArgumentsTAG:tag, _LogArgumentsMsg:message});
  }
  static void e({String tag = _LogDefaultTag, String message}) {
    _platform_log_channel.invokeMethod(_LogMethodNameE, {_LogArgumentsTAG:tag, _LogArgumentsMsg:message});
  }
}