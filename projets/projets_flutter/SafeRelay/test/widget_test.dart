import 'package:flutter_test/flutter_test.dart';
import 'package:saferelay/main.dart';

void main() {
  testWidgets('SafeRelay home renders', (WidgetTester tester) async {
    await tester.pumpWidget(const EmployeeSafetyApp(firebaseReady: false));

    expect(find.text('Securite employees'), findsOneWidget);
    expect(find.text('Inscrire ce telephone'), findsOneWidget);
  });
}
