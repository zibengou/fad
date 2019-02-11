import 'package:meta/meta.dart';

class CardInfo {
  int id;
  String title;
  String desc;
  String avatar;
  String content;
  List<CardImageInfo> images;
  int forkCount;
  int commentCount;
  IconType iconType;
  int iconCount;
  bool analysable;

  CardInfo(
      this.id,
      this.title,
      this.desc,
      this.avatar,
      this.content,
      this.images,
      this.forkCount,
      this.commentCount,
      this.iconType,
      this.iconCount,
      this.analysable);

  testId(int id) {
    this.id = id;
    return this;
  }
}

class CardImageInfo {
  String path;
  ImageType type;

  CardImageInfo(this.path, this.type);
}

enum ImageType { GIF, LONG, DEFAULT }

// todo 补全名称与图表
enum IconType { SMILE, DISLIKE, HEART, SAD, APPLAUSE, FUCK, MESSAGE }

enum MessageType { CLICK, REPLY, FOLLOW, SYSTEM }

class MessageInfo {
  int id;
  MessageType type;
  IconType iconType;
  String name;
  String content;
  String msg;
  String desc;
  String avatar;
  DateTime time;

  MessageInfo(
      {this.id,
      @required this.type,
      this.iconType,
      this.name,
      this.content,
      this.msg,
      this.desc,
      this.avatar,
      this.time});
}
