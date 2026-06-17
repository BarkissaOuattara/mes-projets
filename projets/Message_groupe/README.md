# SafeRelay

Ce dossier contient maintenant le backend Spring Boot de SafeRelay.

- `employee-safety-backend`: API Spring Boot + PostgreSQL + Firebase Cloud Messaging.
- Application Flutter SafeRelay: `C:\Users\ASUS\OneDrive\Documentos\Desktop\projets_flutter\SafeRelay`

## Ou travailler

Backend Spring Boot:

```bash
cd employee-safety-backend
```

Application Flutter, dans l'autre dossier:

```bash
cd C:\Users\ASUS\OneDrive\Documentos\Desktop\projets_flutter\SafeRelay
```

## Pourquoi cette architecture corrige le probleme de Trigger

L'alerte n'attend pas que chaque telephone vienne verifier les messages. La base cree une alerte dans Spring Boot, Spring Boot recupere les tokens des telephones cibles, puis Firebase Cloud Messaging pousse la notification vers tous les appareils concernes.

Pour diffuser a toute l'entreprise, envoyer `targetZone: "TOUS"`.

## Lancement prevu

Backend:

```bash
cd employee-safety-backend
docker compose up -d
mvn spring-boot:run
```

Mobile, depuis le dossier Flutter:

```bash
cd C:\Users\ASUS\OneDrive\Documentos\Desktop\projets_flutter\SafeRelay
flutter pub get
flutter run
```

Note: sur cette machine, Java est reconnu. Maven et Docker doivent etre installes ou ajoutes au PATH pour lancer le backend.

## Prochaines briques utiles

- une application admin web ou mobile pour la base securite;
- authentification des employes;
- historique detaille des accuses de reception;
- geolocalisation optionnelle pour les SOS;
- escalade SMS/appel si un employe ne confirme pas.
