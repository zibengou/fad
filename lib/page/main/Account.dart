import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:nativeapp/components/NormalList.dart';
import 'package:nativeapp/model/view.dart';
import 'package:nativeapp/page/Account_Child.dart';
import 'package:nativeapp/page/UserInfo.dart';
import 'package:nativeapp/utils/common.dart';
import 'package:intl/intl.dart';

class AccountPage extends StatelessWidget {
  final String userName;
  final String avatar;
  final String desc;

  BuildContext _context;

  AccountPage(
      {Key key,
      @required this.userName,
      @required this.avatar,
      @required this.desc})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    _context = context;
    return Scaffold(
      backgroundColor: Colors.grey[200],
      appBar: _buildTitle(),
      body: _buildItems(context),
    );
  }

  Widget _buildTitle() {
    return AppBar(
      centerTitle: true,
      backgroundColor: Colors.white,
      title: Text(
        '个人中心',
        style: TextStyle(color: Colors.black54),
        textAlign: TextAlign.center,
      ),
      actions: <Widget>[
        IconButton(
          icon: Icon(
            Icons.settings,
            color: Colors.black54,
          ),
          onPressed: () => print("setting"),
        ),
      ],
    );
  }

  Widget _buildItems(context) {
    return Column(
      children: <Widget>[
        _buildUserCard(),
        _buildStore(),
        _buildFork(),
        _buildDislikeMe(),
        _buildHelp(),
      ],
    );
  }

  Widget _buildUserCard() {
    return GestureDetector(
        onTap: () => Navigator.push(
            _context, MaterialPageRoute(builder: (context) => UserInfo())),
        child: Container(
          color: Colors.white,
          padding: EdgeInsets.only(left: 20, right: 20, top: 16, bottom: 16),
          margin: EdgeInsets.only(top: 8, bottom: 8),
          child: Row(
            children: <Widget>[
              Container(
                width: 50,
                height: 50,
                margin: EdgeInsets.only(right: 20),
                decoration:
                    BoxDecoration(color: Colors.blue, shape: BoxShape.circle),
                child: Image.network(avatar),
              ),
              Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Text(
                    userName,
                    style: TextStyle(fontSize: 17, color: Colors.black54),
                  ),
                  Text(
                    desc,
                    style: TextStyle(fontSize: 14, color: Colors.grey),
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
        ));
  }

  Widget _buildStore() {
    return _buildTile("我的收藏", Icons.star, Colors.yellow, 40, CollectPage());
  }

  Widget _buildFork() {
    return _buildTile("我不爽的", Icons.gesture, Colors.blueGrey, 36, ForkPage());
  }

  Widget _buildDislikeMe() {
    return _buildTile("不爽我的", Icons.mail, Colors.blue, 36, FuckMePage());
  }

  Widget _buildHelp() {
    return Container(
        margin: EdgeInsets.only(top: 8, bottom: 8),
        child: _buildTile(
          "帮助与彩蛋",
          Icons.help,
          Colors.green,
          36,
          HelpPage(),
        ));
  }

  Widget _buildTile(String name, IconData icon, Color iconColor, double size,
      StatelessWidget page) {
    return GestureDetector(
        onTap: () => Navigator.push(
            _context, MaterialPageRoute(builder: (context) => CollectPage())),
        child: Container(
            padding: EdgeInsets.only(top: 12, bottom: 12, left: 10),
            decoration: BoxDecoration(
                color: Colors.white,
                border: BorderDirectional(
                    bottom: BorderSide(color: Colors.grey[200]))),
            child: Row(
              children: <Widget>[
                Container(
                  width: 60,
                  margin: EdgeInsets.only(right: 16),
                  child: Icon(
                    icon,
                    color: iconColor,
                    size: size,
                  ),
                ),
                Container(
                  child: Text(
                    name,
                    style: TextStyle(fontSize: 18, color: Colors.black54),
                  ),
                )
              ],
            )));
  }
}
