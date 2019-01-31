import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'package:nativeapp/page/Create.dart';
import 'package:nativeapp/page/main/Main.dart';
import 'package:nativeapp/page/Setting.dart';

void main() {
  runApp(new App());
}

class App extends StatelessWidget {
  final MainScreen _mainScreen = MainScreen();
  final SettingScreen _settingScreen = SettingScreen();
  final CreatePage _createPage = CreatePage();

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "test",
      locale: Locale('zh', 'CN'),
      home: MainScreen(),
      routes: {
        '/main': (BuildContext context) => _mainScreen,
        '/setting': (BuildContext context) => _settingScreen,
        '/create':(BuildContext context) => _createPage,
      },
    );
  }
}
