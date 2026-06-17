\# MediShield BF — Brief technique



\## Description

MediShield BF est une plateforme de cybersécurité pour les données de santé numériques au Burkina Faso.



La plateforme ne remplace pas les applications de santé existantes. Elle agit comme une couche de sécurité qui collecte les journaux d’activité, analyse les comportements suspects, anonymise les données sensibles et génère des alertes.



\## Objectif du MVP

Le MVP doit permettre de :

\- recevoir des journaux d’activité ;

\- stocker les logs ;

\- détecter des comportements suspects ;

\- anonymiser des données patients fictives ;

\- afficher un tableau de bord ;

\- générer des alertes de sécurité ;

\- produire un rapport simple.



\## Stack technique

\- Backend : Django REST Framework

\- Frontend : React

\- Base de données : PostgreSQL

\- IA : Python + Scikit-learn



\## Rôles utilisateurs

\- admin : gère les utilisateurs, les rôles et les paramètres ;

\- auditeur : consulte les logs, alertes et rapports ;

\- agent\_sante : simule des actions médicales ;

\- responsable : consulte les alertes et les scores de risque.



\## Actions à journaliser

\- LOGIN

\- VIEW\_PATIENT

\- UPDATE\_PATIENT

\- EXPORT\_DATA

\- DELETE\_DATA

\- ACCESS\_DENIED



\## Règles de détection MVP

Créer une alerte si :

\- un utilisateur consulte plus de 50 dossiers en une heure ;

\- une connexion a lieu entre 22h et 5h ;

\- plus de 5 accès sont refusés en 10 minutes ;

\- un agent simple exporte des données ;

\- un utilisateur consulte des dossiers hors de son centre habituel.



\## Niveaux d’alerte

\- LOW

\- MEDIUM

\- HIGH

\- CRITICAL



\## Données patients

Utiliser uniquement des données fictives.



Avant anonymisation :

\- nom

\- prénom

\- téléphone

\- adresse

\- âge

\- sexe

\- diagnostic

\- centre de santé



Après anonymisation :

\- patient\_id pseudonymisé

\- tranche\_age

\- sexe

\- diagnostic

\- zone générale

\- centre anonymisé



\## Endpoints attendus

\- POST /api/auth/login/

\- GET /api/users/

\- POST /api/logs/

\- GET /api/logs/

\- GET /api/alerts/

\- POST /api/anonymize/

\- GET /api/dashboard/

\- GET /api/reports/



\## Contraintes de sécurité

\- ne jamais utiliser de vraies données médicales ;

\- protéger les endpoints par authentification ;

\- appliquer des permissions selon les rôles ;

\- valider toutes les entrées ;

\- journaliser les actions sensibles ;

\- ne pas exposer inutilement les données sensibles ;

\- MediShield BF n’est pas une application de diagnostic médical.

