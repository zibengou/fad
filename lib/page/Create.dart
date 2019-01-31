import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class CreatePage extends StatelessWidget {
  const CreatePage({Key key, this.color = Colors.red}) : super(key: key);

  final Color color;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        leading: IconButton(
          icon: Icon(
            Icons.arrow_back,
            color: Colors.black54,
          ),
          onPressed: () => Navigator.pop(context),
        ),
        title: Text(
          "啥让你不爽",
          style: TextStyle(color: Colors.black54),
        ),
      ),
      body: Container(
        color: Colors.lightBlue,
      ),
    );
  }
}
