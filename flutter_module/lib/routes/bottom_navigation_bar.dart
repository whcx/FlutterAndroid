import 'package:flutter/material.dart';
import 'package:animations/animations.dart';

import 'package:flutter_gen/gen_l10n/my_localizations.dart';

class BottomNavigationMain extends StatefulWidget {
  const BottomNavigationMain({
    Key key,
    @required this.restorationId,
  }) : super(key: key);

  final String restorationId;

  @override
  _BottomNavigationMainState createState() => _BottomNavigationMainState();
}

class _BottomNavigationMainState extends State<BottomNavigationMain>
    with RestorationMixin {
  final RestorableInt _currentIndex = RestorableInt(0);

  @override
  String get restorationId => widget.restorationId;

  @override
  void restoreState(RestorationBucket oldBucket, bool initialRestore) {
    registerForRestoration(_currentIndex, 'bottom_navigation_tab_index');
  }

  @override
  void dispose() {
    _currentIndex.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final colorScheme = Theme.of(context).colorScheme;
    final textTheme = Theme.of(context).textTheme;

    var bottomNavigationBarItems = <BottomNavigationBarItem>[

      BottomNavigationBarItem(
        icon: const Icon(Icons.calendar_today),
        label: MyLocalizations.of(context).bottomNavigationCalendarTab,
      ),
      BottomNavigationBarItem(
        icon: const Icon(Icons.camera_enhance),
        label: MyLocalizations.of(context).bottomNavigationCameraTab,
      ),
      BottomNavigationBarItem(
        icon: const Icon(Icons.account_circle),
        label: MyLocalizations.of(context).bottomNavigationAccountTab,
      ),
    ];

    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        title: Text("FlutterUI"),
      ),
      body: Center(
        child: PageTransitionSwitcher(
          child: _NavigationDestinationView(
            // Adding [UniqueKey] to make sure the widget rebuilds when transitioning.
            key: UniqueKey(),
            item: bottomNavigationBarItems[_currentIndex.value],
          ),
          transitionBuilder: (child, animation, secondaryAnimation) {
            return FadeThroughTransition(
              child: child,
              animation: animation,
              secondaryAnimation: secondaryAnimation,
            );
          },
        ),
      ),
      bottomNavigationBar: BottomNavigationBar(
        items: bottomNavigationBarItems,
        currentIndex: _currentIndex.value,
        type: BottomNavigationBarType.fixed,
        selectedFontSize: textTheme.caption.fontSize,
        unselectedFontSize: textTheme.caption.fontSize,
        onTap: (index) {
          setState(() {
            _currentIndex.value = index;
          });
        },
        selectedItemColor: colorScheme.onPrimary,
        unselectedItemColor: colorScheme.onPrimary.withOpacity(0.38),
        backgroundColor: colorScheme.primary,
      ),
    );
  }
}

class _NavigationDestinationView extends StatelessWidget {
  _NavigationDestinationView({Key key, this.item}) : super(key: key);

  final BottomNavigationBarItem item;

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        ExcludeSemantics(
          child: Center(
            child: Padding(
              padding: const EdgeInsets.all(16),
              child: ClipRRect(
                borderRadius: BorderRadius.circular(8),
                child: Image.asset(
                  'assets/flutter-mark-square-64.png',
                ),
              ),
            ),
          ),
        ),
        Center(
          child: IconTheme(
            data: const IconThemeData(
              color: Colors.white,
              size: 80,
            ),
            child: Semantics(
              label: MyLocalizations.of(context)                  .bottomNavigationContentPlaceholder(                item.label,            ),
              child: item.icon,
            ),
          ),
        ),
      ],
    );
  }
}
