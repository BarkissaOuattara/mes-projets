# Employee Safety Mobile

Application Flutter employee pour recevoir les alertes FCM, envoyer un SOS, demander un appel base et accuser reception.

## Important

Flutter n'est pas installe sur cette machine actuellement. Pour terminer le projet mobile:

```bash
flutter create .
flutter pub get
```

Puis ajouter la configuration Firebase:

```bash
dart pub global activate flutterfire_cli
flutterfire configure
```

Cela genere `firebase_options.dart` et ajoute les fichiers Firebase Android/iOS necessaires.

## Backend local

Dans `lib/src/safety_api.dart`, l'URL par defaut est:

```dart
http://10.0.2.2:8080/api
```

Cette adresse marche depuis l'emulateur Android vers le backend local. Pour un vrai telephone, remplacer par l'adresse IP du PC sur le meme Wi-Fi.
