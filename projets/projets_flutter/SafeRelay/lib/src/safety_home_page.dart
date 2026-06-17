import 'dart:io';

import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';

import 'safety_api.dart';

class SafetyHomePage extends StatefulWidget {
  const SafetyHomePage({required this.api, required this.firebaseReady, super.key});

  final SafetyApi api;
  final bool firebaseReady;

  @override
  State<SafetyHomePage> createState() => _SafetyHomePageState();
}

class _SafetyHomePageState extends State<SafetyHomePage> {
  final fullNameController = TextEditingController(text: 'Employe terrain');
  final phoneController = TextEditingController(text: '+226 70 00 00 00');
  final zoneController = TextEditingController(text: 'Entrepot principal');
  final sosController = TextEditingController(text: 'Je signale une situation urgente.');

  int? employeeId;
  List<dynamic> alerts = [];
  String status = 'Pret a recevoir les alertes.';

  @override
  void initState() {
    super.initState();
    if (!widget.firebaseReady) {
      status = 'SafeRelay demarre. Configure Firebase pour activer les push.';
      return;
    }
    FirebaseMessaging.onMessage.listen((message) {
      setState(() {
        status = message.notification?.title ?? 'Nouvelle alerte recue';
      });
      loadAlerts();
    });
  }

  @override
  void dispose() {
    fullNameController.dispose();
    phoneController.dispose();
    zoneController.dispose();
    sosController.dispose();
    super.dispose();
  }

  Future<void> registerEmployeeAndDevice() async {
    final employee = await widget.api.createEmployee(
      fullName: fullNameController.text,
      phone: phoneController.text,
      zone: zoneController.text,
    );
    final token = widget.firebaseReady ? await FirebaseMessaging.instance.getToken() : null;
    if (token != null) {
      await widget.api.registerDevice(
        employeeId: employee['id'] as int,
        token: token,
        platform: Platform.isAndroid ? 'android' : 'ios',
      );
    }
    setState(() {
      employeeId = employee['id'] as int;
      status = token == null
          ? 'Employe inscrit. Token FCM indisponible pour le moment.'
          : 'Telephone inscrit aux alertes.';
    });
    await loadAlerts();
  }

  Future<void> loadAlerts() async {
    final items = await widget.api.getAlerts(zoneController.text);
    setState(() => alerts = items);
  }

  Future<void> sendSos() async {
    final id = employeeId;
    if (id == null) {
      setState(() => status = 'Inscris d abord le telephone.');
      return;
    }
    await widget.api.sendSos(
      employeeId: id,
      zone: zoneController.text,
      description: sosController.text,
    );
    setState(() => status = 'SOS envoye a la base.');
  }

  Future<void> requestCall() async {
    final id = employeeId;
    if (id == null) {
      setState(() => status = 'Inscris d abord le telephone.');
      return;
    }
    await widget.api.requestCall(
      employeeId: id,
      phone: phoneController.text,
      zone: zoneController.text,
      urgent: true,
    );
    setState(() => status = 'Demande d appel envoyee.');
  }

  Future<void> acknowledge(int alertId) async {
    final id = employeeId;
    if (id == null) {
      return;
    }
    await widget.api.acknowledgeAlert(alertId: alertId, employeeId: id);
    await loadAlerts();
    setState(() => status = 'Accuse de reception envoye.');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Securite employees')),
      body: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          Text(status, style: Theme.of(context).textTheme.titleMedium),
          const SizedBox(height: 16),
          TextField(controller: fullNameController, decoration: const InputDecoration(labelText: 'Nom')),
          TextField(controller: phoneController, decoration: const InputDecoration(labelText: 'Telephone')),
          TextField(controller: zoneController, decoration: const InputDecoration(labelText: 'Zone')),
          const SizedBox(height: 12),
          FilledButton(onPressed: registerEmployeeAndDevice, child: const Text('Inscrire ce telephone')),
          OutlinedButton(onPressed: loadAlerts, child: const Text('Actualiser les alertes')),
          const SizedBox(height: 24),
          Text('Urgence', style: Theme.of(context).textTheme.titleLarge),
          TextField(
            controller: sosController,
            minLines: 2,
            maxLines: 4,
            decoration: const InputDecoration(labelText: 'Description du probleme'),
          ),
          const SizedBox(height: 12),
          FilledButton.tonal(onPressed: sendSos, child: const Text('Envoyer SOS')),
          OutlinedButton(onPressed: requestCall, child: const Text('Demander un appel base')),
          const SizedBox(height: 24),
          Text('Alertes recues', style: Theme.of(context).textTheme.titleLarge),
          ...alerts.map((alert) => Card(
                child: ListTile(
                  title: Text(alert['title'] as String),
                  subtitle: Text('${alert['message']}\nZone: ${alert['targetZone']}'),
                  isThreeLine: true,
                  trailing: TextButton(
                    onPressed: () => acknowledge(alert['id'] as int),
                    child: const Text('OK'),
                  ),
                ),
              )),
        ],
      ),
    );
  }
}
