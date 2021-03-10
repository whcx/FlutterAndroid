// Copyright 2014 The Flutter Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_localized_locales/flutter_localized_locales.dart';
import 'package:flutter_gen/gen_l10n/my_localizations.dart';
import 'routes/bottom_navigation_bar.dart';
import 'routes/bottom_app_bar.dart';

void main() {
  runApp(FlutterHome());
}

class FlutterHome extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      localizationsDelegates: const [
        ...MyLocalizations.localizationsDelegates,
        LocaleNamesLocalizationsDelegate()
      ],
      supportedLocales: MyLocalizations.supportedLocales,

      title: 'Flutter View',
      theme: ThemeData(
        primaryColor: Theme.of(context).primaryColor,
      ),
      routes: {
        '/': (context) => BottomAppBarMain(),
      },
    );
  }
}