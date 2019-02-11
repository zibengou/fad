import 'package:flutter/material.dart';

typedef List<T> MoreData<T>(T last);
typedef List<T> InitData<T>();

typedef Widget BuildItem<T>(T info, int index);

class NormalList<T> extends StatefulWidget {
  final List<T> _messages = [];
  final ScrollController _controller = ScrollController();
  final MoreData<T> moreData;
  final InitData<T> initData;
  final BuildItem<T> buildItem;
  final String listKey;

  NormalList(
      {Key key,
      @required this.moreData,
      @required this.initData,
      @required this.listKey,
      @required this.buildItem})
      : super(key: key);

  @override
  createState() {
    return NormalListState<T>();
  }
}

class NormalListState<T> extends State<NormalList<T>> {
  @override
  void initState() {
    super.initState();
    if (widget._messages.length < 1) {
      setState(() {
        widget._messages.addAll(widget.initData());
      });
      widget._controller.addListener(() {
        var maxScroll = widget._controller.position.maxScrollExtent;
        var pixels = widget._controller.position.pixels;
        if (maxScroll - pixels < 500 || maxScroll < 500) {
          // todo debounce
          if (mounted) {
            setState(() {
              print("more data");
              widget._messages.addAll(widget.moreData(widget._messages.last));
            });
          }
        }
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    if (widget._messages.length < 1) {
      return Container(
        color: Colors.green,
      );
    } else {
      return Container(
        color: Colors.grey[200],
        child: RefreshIndicator(
          child: ListView.builder(
            key: new PageStorageKey(widget.listKey),
            itemCount: widget._messages.length,
            itemBuilder: (context, i) =>
                widget.buildItem(widget._messages[i], i),
            controller: widget._controller,
          ),
          onRefresh: _refreshData,
        ),
      );
    }
  }

  Future<Null> _refreshData() async {
    setState(() {
      widget._messages.clear();
      widget._messages.addAll(widget.initData());
    });
    return null;
  }
}
