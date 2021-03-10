import 'package:flutter/material.dart';
import 'package:flutter_module/common/custom_theme_data.dart';
import 'package:flutter_module/common/platform_log.dart';

typedef bottomBtnPressed = void Function();
typedef bottomBtnPressedParam = void Function(Key btn);
final Key homeBtnKey = GlobalKey(debugLabel:"homePage");
final Key accountBtnKey = GlobalKey(debugLabel: "accountPage");

class BottomAppBarMain extends StatefulWidget {
  const BottomAppBarMain();
  @override
  _BottomAppBarMainState createState() => _BottomAppBarMainState();
}

class _BottomAppBarMainState extends State<BottomAppBarMain> {
  static const List<FloatingActionButtonLocation> _fabLocations = [
    FloatingActionButtonLocation.centerDocked,
  ];

  bool bg_transparent = false;

  _centerButtonPressed() {
    PlatformLog.i(tag: "BottomAppBarMain", message:"_centerButtonPressed");
    setState(() {
      bg_transparent = true;
    });
  }

  bottomButtonPressed(Key btn) {
    PlatformLog.i(tag: "BottomAppBarMain", message:" bottom button pressed.${btn}");
    setState(() {
      bg_transparent = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: bg_transparent ? Color.fromARGB(0, 0, 0, 0) : Theme.of(context).colorScheme.onBackground,
      // appBar: AppBar(
      //   automaticallyImplyLeading: false,
      //   title: Text("Test Bottom"),
      // ),

      floatingActionButton: FloatingActionButton(
        onPressed: _centerButtonPressed,
        child:const Icon(Icons.camera),
        tooltip: "bottom button pressed!",
      ),

    floatingActionButtonLocation: _fabLocations[0],
    bottomNavigationBar: _BottomAppBar(
      fabLocations: _fabLocations[0],
      shape: const CircularNotchedRectangle(),
      btnPressedCallback: bottomButtonPressed,
    ),
    );
  }
}

class _BottomAppBar extends StatelessWidget{
  const _BottomAppBar({
    this.fabLocations,
    this.shape,
    this.btnPressedCallback,
  });

  final FloatingActionButtonLocation fabLocations;
  final NotchedShape shape;
  final bottomBtnPressedParam btnPressedCallback;

  static final centerLocations = <FloatingActionButtonLocation>[
    FloatingActionButtonLocation.centerDocked,
    FloatingActionButtonLocation.centerFloat,
    FloatingActionButtonLocation.miniCenterDocked,
  ];

  @override
  Widget build(BuildContext context) {
    return BottomAppBar(
      color: Theme.of(context).colorScheme.primary,
      shape: shape,
      child: IconTheme(
        data: IconThemeData(color: Theme.of(context).colorScheme.onPrimary),
        child: Container(
          margin: const EdgeInsets.only(left: CustomThemeData.bottom_bar_margin, right: CustomThemeData.bottom_bar_margin),
          child: Row(
            children: [
              IconButton(
                key: homeBtnKey,
                tooltip: "主页按钮",
                icon: const Icon(Icons.apps),
                onPressed: () {
                  debugPrint("主页按钮按下");
                  btnPressedCallback(homeBtnKey);
                },
              ),

              if(centerLocations.contains(fabLocations)) const Spacer(),

              IconButton(
                  key: accountBtnKey,
                  tooltip: "账户",
                  icon: const Icon(Icons.account_circle),
                  onPressed: () {
                    debugPrint("账户按钮按下");
                    btnPressedCallback(accountBtnKey);
              })
            ],
          ),
        ),
      ),
    );
  }
}
