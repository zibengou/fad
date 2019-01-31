import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class DynamicPage extends StatelessWidget {
  const DynamicPage({Key key, this.color}) : super(key: key);

  final Color color;

  @override
  Widget build(BuildContext context) {
    return Container(
      color: color,
    );
  }
}
