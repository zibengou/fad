import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:nativeapp/components/CardList.dart';
import 'package:nativeapp/model/view.dart';

final String avatar =
    "https://www.gravatar.com/avatar/55e3e272210f051084303107330ef614?s=32&d=identicon&r=PG";
final CardInfo card = CardInfo(
    1,
    "傻屌咪蒙",
    "描述描述描述",
    avatar,
    "This is a content for.....This is a content for.....This is a content for.....This is a content for.....This is a content for.....",
    null,
    12,
    104,
    IconType.SMILE,
    345,
    false);

class FollowTab extends StatelessWidget {
  CardList _list;

  @override
  Widget build(BuildContext context) {
    if (_list == null) {
      print("init cardList");
      _list = CardList(
        moreData: moreData,
        initData: initData,
        listKey: "followtab",
      );
    }
    return _list;
  }

  List<CardInfo> moreData(int lastId) {
    return [card, card, card];
  }

  List<CardInfo> initData() {
    return [card, card, card];
  }
}
