import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class PictureWidget extends StatelessWidget {
  final String assetName;
  final double height;
  final double width;

  const PictureWidget(this.assetName,
      {Key key, this.height = 25, this.width = 25})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      height: this.height,
      width: this.width,
      child: Image.asset(
        assetName,
        width: this.width,
        height: this.height,
        color: Colors.black54,
      ),
    );
//        child: DecoratedBox(
//            decoration: BoxDecoration(
//                image: DecorationImage(image: AssetImage(assetName)))));
  }
}
