# Employee Safety Backend

Backend Spring Boot pour une application mobile de securite employees:

- diffusion d'alertes par zone ou a tous les employes;
- stockage PostgreSQL;
- enregistrement des tokens Firebase Cloud Messaging;
- reception des SOS;
- demandes d'appel vers la base;
- accuses de reception des alertes.

## Prerequis

- Java 21
- Maven
- Docker Desktop pour PostgreSQL
- Un projet Firebase avec Firebase Cloud Messaging

## Lancer PostgreSQL

```bash
docker compose up -d
```

## Lancer Spring Boot

```bash
mvn spring-boot:run
```

Pour activer Firebase, telecharger le fichier JSON de compte de service Firebase, puis definir:

```bash
set FIREBASE_SERVICE_ACCOUNT_PATH=C:\chemin\vers\firebase-service-account.json
mvn spring-boot:run
```

Sans cette variable, l'API fonctionne et sauvegarde les donnees, mais n'envoie pas encore de push FCM.

## Endpoints principaux

- `POST /api/employees`
- `POST /api/devices`
- `GET /api/alerts?zone=Entrepot principal`
- `POST /api/alerts`
- `PATCH /api/alerts/{id}/acknowledge`
- `POST /api/incidents`
- `POST /api/call-requests`

## Exemple de diffusion vers tout le monde

```bash
curl -X POST http://localhost:8080/api/alerts ^
  -H "Content-Type: application/json" ^
  -d "{\"title\":\"Alerte securite\",\"message\":\"Evacuez calmement vers le point de rassemblement.\",\"level\":\"CRITICAL\",\"targetZone\":\"TOUS\",\"sender\":\"Base securite\"}"
```
