import 'package:flutter/material.dart';
import 'package:nativeapp/model/view.dart';

class Common {
  static click() async {}

  static Icon parseIcon(IconType type, double iconSize, iconColor) {
    switch (type) {
      case IconType.SMILE:
        return Icon(
          IconData(0xe60c, fontFamily: "iconfont"),
          size: iconSize,
          color: iconColor,
        );
      case IconType.SAD:
        return Icon(IconData(0xe621, fontFamily: "iconfont"),
            size: iconSize, color: iconColor);
      case IconType.DISLIKE:
        return Icon(IconData(0xe620, fontFamily: "iconfont"),
            size: iconSize, color: iconColor);
      case IconType.HEART:
        return Icon(IconData(0xe772, fontFamily: "iconfont"),
            size: iconSize, color: iconColor);
      case IconType.APPLAUSE:
        return Icon(IconData(0xe652, fontFamily: "iconfont"),
            size: iconSize, color: iconColor);
      case IconType.FUCK:
        return Icon(IconData(0xe617, fontFamily: "iconfont"),
            size: iconSize, color: iconColor);
      case IconType.MESSAGE:
        return Icon(IconData(0xe667, fontFamily: "iconfont"), size: iconSize, color: iconColor);
      default:
        return Icon(IconData(0xe60c, fontFamily: "iconfont"),
            size: iconSize, color: iconColor);
    }
  }
}
