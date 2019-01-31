import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:nativeapp/components/NavigationBottom.dart';
import 'package:nativeapp/page/main/Account.dart';
import 'package:nativeapp/page/main/Dynamic.dart';
import 'package:nativeapp/page/main/Home.dart';

class MainScreen extends StatefulWidget {
  @override
  createState() => MainState();
}

class MainState extends State<MainScreen> {
  String _currentIndex = "首页";
  final Map<String, Widget> _children = {
    "首页": HomePage(),
    "动态": DynamicPage(
      color: Colors.grey,
    ),
    "我的": AccountPage(
      color: Colors.green,
    ),
  };

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: _children[_currentIndex],
        bottomNavigationBar: BottomNavigation(
          onTap: (k) => onTabTapped(k),
          data: {
            "首页": Icons.home,
            "动态": Icons.email,
            "我的": Icons.person,
          },
        ));
  }

  void onTabTapped(String value) {
    setState(() {
      _currentIndex = value;
    });
  }
}
