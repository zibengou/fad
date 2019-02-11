import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:nativeapp/components/NormalList.dart';
import 'package:nativeapp/model/view.dart';
import 'package:nativeapp/utils/common.dart';
import 'package:intl/intl.dart';

class DynamicPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: _buildTitle(),
      body: _buildMessageList(),
//      body: Container(color: Colors.red),
    );
  }

  Widget _buildTitle() {
    return AppBar(
      centerTitle: true,
      backgroundColor: Colors.white,
      title: Text(
        '动态',
        style: TextStyle(color: Colors.black54),
        textAlign: TextAlign.center,
      ),
      actions: <Widget>[
        IconButton(
          icon: Icon(
            Icons.accessibility,
            color: Colors.black54,
          ),
          onPressed: () => print("father"),
        ),
      ],
    );
  }

  Widget _buildSystemMessage() {
    return GestureDetector(
      onTap: () => print("click system message"),
      child: Row(
        children: <Widget>[
          Container(
            width: 50,
            height: 50,
            margin: EdgeInsets.only(right: 20),
            decoration:
                BoxDecoration(color: Colors.blue, shape: BoxShape.circle),
            child: Icon(
              Icons.volume_up,
              color: Colors.white,
            ),
          ),
          Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              Text(
                "系统消息",
                style: TextStyle(fontSize: 19, color: Colors.black54),
              ),
              Text(
                "暂时没有新通知消息",
                style: TextStyle(fontSize: 16, color: Colors.grey),
              )
            ],
          ),
          Spacer(),
          Icon(
            Icons.chevron_right,
            size: 32,
            color: Colors.grey,
          )
        ],
      ),
    );
  }

  Widget _buildMessageList() {
    final String avatar =
        "https://www.gravatar.com/avatar/55e3e272210f051084303107330ef614?s=32&d=identicon&r=PG";
    return NormalList<MessageInfo>(
      moreData: (MessageInfo i) {
        return [];
      },
      initData: () {
        MessageInfo sys =
            MessageInfo(type: MessageType.SYSTEM, time: DateTime.now());
        MessageInfo info = MessageInfo(
            id: 0,
            type: MessageType.CLICK,
            iconType: IconType.SMILE,
            name: "网闭嘴",
            content: null,
            msg: "这是消息内容这是消息内容这是消息内容这是消息内容这是消息内容",
            desc: "对你的评论表示难过",
            avatar: avatar,
            time: DateTime.parse("2019-02-10 13:00:00"));
        MessageInfo info2 = MessageInfo(
            id: 1,
            type: MessageType.REPLY,
            iconType: null,
            name: "网闭嘴",
            content: "这是评论这是评论这是评论这是评论这是评论这是评论",
            msg: "这是消息内容这是消息内容这是消息内容这是消息内容这是消息内容",
            desc: "回复了你的评论",
            avatar: avatar,
            time: DateTime.parse("2018-12-12 00:00:00"));
//        MessageInfo info3 = MessageInfo(2, MessageType.FOLLOW, null, "网闭嘴",
//            null, null, "关注了你", avatar, DateTime.now());
        return [sys, info, info2];
      },
      buildItem: (info, i) => _buildMessageItem(info, i),
      listKey: "message_list",
    );
  }

  Widget _buildMessageItem(MessageInfo info, int index) {
    Widget content;
    Duration duration = DateTime.now().difference(info.time);
    String time = info.time.toIso8601String();
    if (duration.inSeconds < 60) {
      time = "${duration.inSeconds}秒钟前";
    } else if (duration.inMinutes < 60) {
      time = "${duration.inMinutes}分钟前";
    } else {
      time = DateFormat("MM/dd HH:mm").format(info.time);
    }
    switch (info.type) {
      case MessageType.REPLY:
        content = _buildMessageContent(IconType.MESSAGE, info.name, info.desc,
            time, info.avatar, info.msg, info.content);
        break;
      case MessageType.FOLLOW:
        content = _buildMessageContent4Follow();
        break;
      case MessageType.SYSTEM:
        content = _buildSystemMessage();
        break;
      default:
        content = _buildMessageContent(info.iconType, info.name, info.desc,
            time, info.avatar, info.msg, null);
    }
    return GestureDetector(
      onTap: () => print("item click"),
      child: Container(
          padding: EdgeInsets.only(left: 20, right: 20, top: 15, bottom: 15),
          margin: index == 0 ? EdgeInsets.only(bottom: 8, top: 8) : null,
          decoration: BoxDecoration(
              color: Colors.white,
              border: index == 0
                  ? BorderDirectional()
                  : BorderDirectional(
                      bottom: BorderSide(color: Colors.grey[200]))),
          child: content),
    );
  }

  Widget _buildMessageContent(IconType iconType, String name, String desc,
      String time, String avatar, String msg, String content) {
    return Column(
      children: <Widget>[
        Container(
          margin: EdgeInsets.only(bottom: 8),
          child: Row(
            children: <Widget>[
              Container(
                child: Common.parseIcon(iconType, 16, Colors.grey[500]),
                margin: EdgeInsets.only(right: 8),
              ),
              _buildItemTitle(name, desc),
              Spacer(),
              _buildItemTime(time)
            ],
          ),
        ),
        Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            _buildAvatar(avatar),
            content == null
                ? Container()
                : Container(
                    width: 200,
                    margin: EdgeInsets.only(left: 8),
                    child: Text(
                      content,
                      style: TextStyle(fontSize: 13),
                    )),
            Spacer(),
            _buildItemMsg(msg)
          ],
        )
      ],
    );
  }

  Widget _buildMessageContent4Follow() {}

  Widget _buildAvatar(String path) {
    return CircleAvatar(
      radius: 25,
      backgroundColor: Colors.grey[200],
      backgroundImage: NetworkImage(path),
    );
  }

  Widget _buildItemTitle(String name, String desc) {
    return Row(children: <Widget>[
      Container(
        margin: EdgeInsets.only(right: 4),
        child: Text(name,
            style:
                TextStyle(color: Colors.black54, fontWeight: FontWeight.bold)),
      ),
      Text(
        desc,
        style: TextStyle(color: Colors.grey),
      )
    ]);
  }

  Widget _buildItemMsg(String msg) {
    return Container(
        width: 90,
        child: Text(
          msg,
          style: TextStyle(color: Colors.grey, fontSize: 12),
        ));
  }

  Widget _buildItemTime(String time) {
    return Text(
      time,
      style: TextStyle(color: Colors.grey, fontSize: 12),
    );
  }
}
