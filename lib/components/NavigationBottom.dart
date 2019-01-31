import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class BottomNavigation extends StatefulWidget {
  final ValueChanged<String> onTap;
  final Map<String, IconData> data;
  final String defaultSelectKey;

  const BottomNavigation(
      {Key key, this.onTap, @required this.data, this.defaultSelectKey})
      : super(key: key);

  @override
  createState() => BottomNavigationState();
}

class BottomNavigationState extends State<BottomNavigation> {
  String _currentIndex;

  @override
  void initState() {
    _currentIndex = widget.defaultSelectKey == null
        ? widget.data.entries.first.key
        : widget.defaultSelectKey;
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return BottomAppBar(
      color: Colors.white,
      child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          children: widget.data.keys
              .map((d) => NavigationIcon(
                    onTap: () => onTap(d),
                    icon: widget.data[d],
                    text: d,
                    selected: _currentIndex == d,
                  ))
              .toList()),
    );
  }

  onTap(String key) {
    _currentIndex = key;
    widget.onTap(key);
  }
}

class NavigationIcon extends StatelessWidget {
  final VoidCallback onTap;
  final IconData icon;
  final String text;
  final double height;
  final bool selected;

  const NavigationIcon(
      {Key key,
      this.onTap,
      this.icon,
      this.text,
      this.height = 50,
      this.selected = false})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return InkResponse(
        borderRadius: BorderRadius.circular(this.height / 2),
        onTap: () => onTap(),
        child: Container(
          height: this.height,
          padding: EdgeInsets.only(top: 5),
          child: Column(
            children: <Widget>[
              Icon(icon, color: selected ? Colors.lightBlue : Colors.grey[400]),
              Text(
                text,
                style: TextStyle(
                    color: selected ? Colors.lightBlue : Colors.grey[400]),
              )
            ],
          ),
        ));
  }
}
