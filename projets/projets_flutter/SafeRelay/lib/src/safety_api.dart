import 'dart:convert';

import 'package:http/http.dart' as http;

class SafetyApi {
  SafetyApi({this.baseUrl = 'http://10.0.2.2:8080/api'});

  final String baseUrl;

  Future<Map<String, dynamic>> createEmployee({
    required String fullName,
    required String phone,
    required String zone,
  }) async {
    return _post('/employees', {
      'fullName': fullName,
      'phone': phone,
      'zone': zone,
    });
  }

  Future<void> registerDevice({
    required int employeeId,
    required String token,
    required String platform,
  }) async {
    await _post('/devices', {
      'employeeId': employeeId,
      'token': token,
      'platform': platform,
    });
  }

  Future<List<dynamic>> getAlerts(String zone) async {
    final response = await http.get(Uri.parse('$baseUrl/alerts?zone=$zone'));
    _ensureSuccess(response);
    return jsonDecode(response.body) as List<dynamic>;
  }

  Future<void> acknowledgeAlert({
    required int alertId,
    required int employeeId,
  }) async {
    final response = await http.patch(
      Uri.parse('$baseUrl/alerts/$alertId/acknowledge'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'employeeId': employeeId}),
    );
    _ensureSuccess(response);
  }

  Future<void> sendSos({
    required int employeeId,
    required String zone,
    required String description,
  }) async {
    await _post('/incidents', {
      'employeeId': employeeId,
      'zone': zone,
      'description': description,
    });
  }

  Future<void> requestCall({
    required int employeeId,
    required String phone,
    required String zone,
    required bool urgent,
  }) async {
    await _post('/call-requests', {
      'employeeId': employeeId,
      'phone': phone,
      'zone': zone,
      'urgent': urgent,
    });
  }

  Future<Map<String, dynamic>> _post(String path, Map<String, dynamic> body) async {
    final response = await http.post(
      Uri.parse('$baseUrl$path'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(body),
    );
    _ensureSuccess(response);
    return jsonDecode(response.body) as Map<String, dynamic>;
  }

  void _ensureSuccess(http.Response response) {
    if (response.statusCode < 200 || response.statusCode >= 300) {
      throw Exception('API error ${response.statusCode}: ${response.body}');
    }
  }
}
