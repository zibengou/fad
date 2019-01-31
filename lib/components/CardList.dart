import 'package:flutter/material.dart';
import 'package:nativeapp/model/view.dart';

typedef LoadDataFunction = List<CardInfo> Function(int f);

typedef InitDataFunction = List<CardInfo> Function();

final double iconSize = 16;
final Color iconColor = Colors.grey[500];

class CardList extends StatefulWidget {
  final List<CardInfo> _cards = [];
  final ScrollController _controller = ScrollController();
  final LoadDataFunction moreData;
  final InitDataFunction initData;
  final String listKey;

  CardList(
      {Key key,
      @required this.moreData,
      @required this.initData,
      @required this.listKey})
      : super(key: key);

  @override
  createState() {
    return CardListState();
  }
}

class CardListState extends State<CardList> {
  @override
  void initState() {
    super.initState();
    print("switch to ${widget.listKey}");
    if (widget._cards.length < 1) {
      print("init card list");
      setState(() {
        widget._cards.addAll(widget.initData());
      });
      widget._controller.addListener(() {
        var maxScroll = widget._controller.position.maxScrollExtent;
        var pixels = widget._controller.position.pixels;
        if (maxScroll - pixels < 500 || maxScroll < 500) {
          // todo debounce
          if (mounted) {
            setState(() {
              print("more data");
              widget._cards.addAll(widget.moreData(widget._cards.last.id));
            });
          }
        }
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    if (widget._cards.length < 1) {
      return Container(
        color: Colors.green,
      );
    } else {
      return Container(
        color: Colors.grey[200],
        child: RefreshIndicator(
          child: ListView.builder(
            key: new PageStorageKey(widget.listKey),
            itemCount: widget._cards.length,
            itemBuilder: (context, i) => _buildCard(widget._cards[i]),
            controller: widget._controller,
          ),
          onRefresh: _refreshData,
        ),
      );
    }
  }

  Future<Null> _refreshData() async {
    setState(() {
      widget._cards.clear();
      widget._cards.addAll(widget.initData());
    });
    return null;
  }

  Widget _buildCard(CardInfo c) {
    return Container(
      margin: EdgeInsets.only(bottom: 8),
      padding: EdgeInsets.only(left: 8, right: 8),
      color: Colors.white,
      child: Column(
        children: <Widget>[
          Container(
              padding: EdgeInsets.only(top: 8, left: 8, right: 8, bottom: 0),
              child: _buildCardTitle(c.avatar, c.title, c.desc)),
          Container(
            padding: EdgeInsets.only(left: 10, right: 10, top: 6, bottom: 6),
            child: _buildCardContent(c.content, c.images),
          ),
          Divider(
            color: Colors.grey[400],
          ),
          Container(
              padding: EdgeInsets.only(left: 8, right: 8, top: 4, bottom: 12),
              child: _buildBottom(c.iconCount, c.forkCount, c.commentCount,
                  parseIcon(c.iconType), c.analysable)),
        ],
      ),
    );
  }

  Icon parseIcon(IconType type) {
    switch (type) {
      case IconType.SMILE:
        return Icon(
          Icons.android,
          size: iconSize,
          color: iconColor,
        );
      case IconType.SAD:
        return Icon(Icons.settings_input_antenna,
            size: iconSize, color: iconColor);
      case IconType.DISLIKE:
        return Icon(Icons.close, size: iconSize, color: iconColor);
      case IconType.HEART:
        return Icon(Icons.healing, size: iconSize, color: iconColor);
      case IconType.APPLAUSE:
        return Icon(Icons.add, size: iconSize, color: iconColor);
      case IconType.FUCK:
        return Icon(Icons.fullscreen_exit, size: iconSize, color: iconColor);
      default:
        return Icon(Icons.android, size: iconSize, color: iconColor);
    }
  }

  Widget _buildCardTitle(avatar, title, desc) {
    return Row(
      children: <Widget>[
        Container(
          margin: EdgeInsets.only(right: 10),
          child: Image.network(
            avatar,
            width: 40,
            height: 40,
            fit: BoxFit.fill,
          ),
        ),
        Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text(
              title,
              style: TextStyle(color: Colors.blue, fontSize: 16),
              textAlign: TextAlign.left,
            ),
            Text(
              desc,
              style: TextStyle(color: Colors.grey, fontSize: 14),
              textAlign: TextAlign.left,
            )
          ],
        ),
        Spacer(),
        IconButton(
          icon: Icon(
            Icons.close,
            color: Colors.grey,
          ),
          onPressed: () => _showDialog(),
        )
      ],
    );
  }

  Future<Null> _showDialog() async {
    switch (await showDialog(
        context: context,
        builder: (context) => SimpleDialog(
              children: <Widget>[
                SimpleDialogOption(
                  child: Text("收藏"),
                  onPressed: () => Navigator.pop(context, 0),
                ),
                SimpleDialogOption(
                  child: Text("举报"),
                  onPressed: () => Navigator.pop(context, 1),
                )
              ],
            ))) {
      case 0:
        print("收藏");
        return;
      case 1:
        print("举报");
        return;
    }
  }

  Widget _buildCardContent(String content, List<CardImageInfo> images) {
    List<Widget> _columns = [
      Text(content,
          maxLines: 10,
          overflow: TextOverflow.ellipsis,
          style: TextStyle(fontSize: 16, color: Colors.black54))
    ];
    if (images != null && images.length > 0) {
      _columns.addAll(images.map((image) {
        return Text(image.path);
      }).toList());
    }
    return Column(children: _columns);
  }

  Widget _buildBottom(int iconCount, int forkCount, int commentCount, Icon icon,
      bool analysable) {
    TextStyle style = TextStyle(color: iconColor, fontSize: 14);
    return Row(
      children: <Widget>[
        icon,
        Container(
          margin: EdgeInsets.only(left: 4, right: 20),
          child: Text(iconCount.toString(), style: style),
        ),
        Icon(Icons.message, size: iconSize, color: iconColor),
        Container(
          margin: EdgeInsets.only(left: 4, right: 20),
          child: Text(commentCount.toString(), style: style),
        ),
        Icon(Icons.all_inclusive, size: iconSize, color: iconColor),
        Container(
          margin: EdgeInsets.only(left: 4, right: 20),
          child: Text(forkCount.toString(), style: style),
        ),
      ],
    );
  }
}
