import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:nativeapp/components/PictureWidget.dart';
import 'package:nativeapp/page/main/tab/Follow.dart';
import 'package:nativeapp/page/main/tab/Latest.dart';
import 'package:nativeapp/page/main/tab/Recommend.dart';

class HomePage extends StatefulWidget {
  TabController _tabController = TabController(length: 0, vsync: null);
  final RecommendTab _recommendTab = RecommendTab();
  final FollowTab _followTab = FollowTab();
  final LatestTab _latestTab = LatestTab();

  @override
  createState() => HomeState();
}

class HomeState extends State<HomePage> with SingleTickerProviderStateMixin {
  @override
  void initState() {
    super.initState();
    if (widget._tabController.length == 0) {
      widget._tabController =
          TabController(length: 3, vsync: this, initialIndex: 1);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: _buildBar(),
      body: new TabBarView(
        controller: widget._tabController,
        children: <Widget>[
          widget._followTab,
          widget._recommendTab,
          widget._latestTab,
        ],
      ),
    );
  }

  Widget _buildBar() {
    return AppBar(
      title: _buildTitle(),
      bottom: TabBar(
        controller: widget._tabController,
        labelColor: Colors.blue,
        unselectedLabelColor: Colors.grey,
        labelStyle: TextStyle(
          fontSize: 17,
        ),
        tabs: [
          Tab(
            text: "跟踪",
          ),
          Tab(text: "推荐"),
          Tab(text: "最新"),
        ],
      ),
      backgroundColor: Colors.white,
    );
  }

  Widget _buildTitle() {
    return Container(
        height: 35,
        decoration: new BoxDecoration(
            color: Colors.grey[300], //new Color.fromRGBO(255, 0, 0, 0.0),
            borderRadius: BorderRadius.circular(5)),
        child: Row(
          children: <Widget>[
            GestureDetector(
                onTap: () =>
                    showSearch(context: context, delegate: DataSearch()),
                child: Container(
                  child: Row(
                    children: <Widget>[
                      Container(
                        padding: EdgeInsets.only(left: 12, right: 12),
                        child: Icon(Icons.search, color: Colors.black38),
                      ),
                      Text(
                        "Test",
                        style: TextStyle(color: Colors.black38),
                      ),
                    ],
                  ),
                )),
            Spacer(),
            Container(
              width: 1,
              height: 25,
              margin: EdgeInsets.only(top: 5, bottom: 5),
              color: Colors.black38,
            ),
            InkWell(
              onTap: () => Navigator.pushNamed(context, '/create'),
              child: Row(
                children: <Widget>[
                  Container(
                    child: PictureWidget("images/hand.png"),
                    margin: EdgeInsets.only(left: 14, right: 8),
                  ),
                  Container(
                    child: Text(
                      "不爽",
                      style: TextStyle(color: Colors.black38, fontSize: 18),
                    ),
                    margin: EdgeInsets.only(right: 14),
                  ),
                ],
              ),
            ),
          ],
        ));
  }
}

class DataSearch extends SearchDelegate<String> {
  @override
  List<Widget> buildActions(BuildContext context) {
    // TODO: implement buildActions
    return [IconButton(icon: Icon(Icons.close), onPressed: () => query = "")];
  }

  @override
  Widget buildLeading(BuildContext context) {
    return IconButton(
        icon: (Icon(Icons.arrow_back)), onPressed: () => close(context, null));
  }

  @override
  Widget buildResults(BuildContext context) {
    return Container(
      width: 100.0,
      height: 100.0,
      child: Card(
        color: Colors.redAccent,
        child: Text(query),
      ),
    );
  }

  @override
  Widget buildSuggestions(BuildContext context) {
    return ListView.builder(
      itemBuilder: (context, index) => ListTile(
            leading: Icon(Icons.search),
            title: Text("This is a Test"),
            onTap: () => showResults(context),
          ),
      itemCount: 5,
    );
  }
}
