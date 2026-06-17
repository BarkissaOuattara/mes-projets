# Projet de Base de Données : Application de Portfolio Multimédia 
Conception, Normalisation et Implémentation sous Oracle et Access

1. Introduction

Objectif : Créer une base de données pour une application de portfolio permettant aux utilisateurs (artistes, photographes, designers) de stocker, organiser et partager leurs projets multimédias (images, vidéos, PDF).

Contexte : Les artistes et créateurs ont besoin d'une plateforme centralisée pour gérer leurs œuvres multimédias. Cette base de données permettra de structurer les informations liées aux projets, aux utilisateurs et aux fichiers multimédias, tout en facilitant leur partage.


2. Conception : Modélisation Conceptuelle

L’objectif : définir les entités, leurs attributs, les relations (avec cardinalités) et quelques règles métier.


a. Entités principales et attributs
    Utilisateur
        ID_Utilisateur (PK)
        Nom
        Prénom
        Email (unique, non nul)
        MotDePasse
        DateInscription
        TypeUtilisateur (ENUM : artiste, photographe, designer, admin)
        VisibilitéProfil (ENUM : public, privé)
        StatutCompte (ENUM : actif, suspendu, banni)

    Projet
        ID_Projet (PK)
        Titre
        Description
        DateCréation
        DateDernièreModification
        StatutProjet (ENUM : brouillon, publié, archivé)
        ID_Utilisateur (FK → Utilisateur)

    Catégorie
        ID_Catégorie (PK)
        NomCatégorie (ex. photographie, design graphique, vidéo)
        Description

    Projet_Catégorie (entité d’association pour M–N)
        ID_Projet (FK → Projet)
        ID_Catégorie (FK → Catégorie)

    FichierMultimédia
        ID_Fichier (PK)
        NomFichier
        TypeFichier (ENUM : image, vidéo, PDF)
        TailleFichier
        CheminFichier
        DateUpload
        ID_Projet (FK → Projet)

    MediaMetadata (optionnel, 1–1 ou 1–N selon le type)
        ID_Metadata (PK)
        ID_Fichier (FK → FichierMultimédia)
        Résolution (pour images)
        Durée (pour vidéos)
        Format (ex. JPG, PNG, MP4)
        Codec (pour vidéos)

    Commentaire
        ID_Commentaire (PK)
        Contenu
        DateCommentaire
        Modéré (booléen)
        ID_Utilisateur (FK → Utilisateur)
        ID_Projet (FK → Projet)

    Partage
        ID_Partage (PK)
        DatePartage
        NiveauAccès (ENUM : lecture, commentaire, modification)
        ID_Utilisateur_Source (FK → Utilisateur)
        ID_Utilisateur_Cible (FK → Utilisateur)
        ID_Projet (FK → Projet)

    Collaboration (si plusieurs utilisateurs par projet)
        ID_Collab (PK)
        ID_Projet (FK → Projet)
        ID_Utilisateur (FK → Utilisateur)
        RôleCollaborateur (ENUM : auteur, éditeur, spectateur)


b. Relations et cardinalités
    Utilisateur–Projet :
    1 Utilisateur crée 0..* Projets.

    Projet–Catégorie (via Projet_Catégorie) :
    1 Projet est dans 1..* Catégories.
    1 Catégorie regroupe 0..* Projets.

    Projet–FichierMultimédia :
    1 Projet contient 0..* FichiersMultimédias.
    1 FichierMultimédia appartient à 1 Projet.

    FichierMultimédia–MediaMetadata :
    1 FichierMultimédia peut avoir 0..1 ou 0..* Metadonnées selon implémentation.

    Projet–Commentaire :
    1 Projet reçoit 0..* Commentaires.
    1 Commentaire est fait par 1 Utilisateur sur 1 Projet.

    Projet–Partage–Utilisateur :
    1 Projet peut être partagé 0..* fois vers différents Utilisateurs.
    1 Partage relie exactement 1 Projet, 1 Utilisateur source et 1 Utilisateur cible.

    Projet–Collaboration–Utilisateur :
    1 Projet peut avoir 0..* Collaborateurs.
    1 Utilisateur peut collaborer à 0..* Projets.


c. Règles métier et contraintes
    Email : doit être unique dans Utilisateur.
    MotDePasse : stocké haché (sécurité).
    StatutProjet : seules les ressources en statut « publié » sont visibles publiquement.
    VisibilitéProfil : si « privé », seuls les partages explicites (table Partage) donnent accès.
    Modération : les commentaires marqués Modéré = false sont en attente de validation.
    Intégrité référentielle : cascade delete sur les FichiersMultimédia et Commentaires si le Projet est supprimé.


d. Justification de la normalisation
    1FN (atomique)
        Toutes les colonnes sont atomiques (ex. : on ne stocke pas plusieurs e‑mails dans une même cellule).
        Pas de groupes répétés.

    2FN (dépendance à la clé entière)
        Dans chaque table, tout attribut non‑clé dépend de la clé primaire entière.
        Les tables associatives (Projet_Catégorie) ont un PK composite, et tous leurs attributs (ici aucun attribut supplémentaire) dépendent de la paire (ID_Projet, ID_Catégorie).

    3FN (pas de dépendance transitive)
        Aucune table ne contient d’attribut qui dépende d’un autre attribut non‑clé.
        Exemple : on ne stocke pas NomUtilisateur dans Projet pour éviter une dépendance via ID_Utilisateur.


e. Contraintes d’intégrité
    Unique / Not Null
        Utilisateur.Email est UNIQUE et NOT NULL.
        Toutes les PK sont NOT NULL par définition.

    Intégrité référentielle
        Suppression en cascade des FichierMultimédia, Commentaire, Projet_Catégorie, Collaboration et Partage si un Projet est supprimé.
        Suppression en cascade des Commentaire, Partage, Collaboration si un Utilisateur est supprimé (ou plutôt, passer son StatutCompte à « banni »).

    Contraintes métier
        MotDePasse stocké haché.
        Seuls les projets avec StatutProjet = 'publié' sont visibles publiquement.
        Les commentaires Modéré = FALSE sont en attente de validation.
        Les accès via Partage.NiveauAccès (lecture, commentaire, modification) déterminent les droits sur un projet privé.


4. Réalisation : Implémentation sous Oracle et Access
Scripts DDL pour créer l’ensemble des tables et contraintes en Oracle puis en Access.

A. Implémentation Oracle:
    -- Connexion en tant qu'admin Oracle
    CONNECT sys AS SYSDBA;

    -- 1. Création d'un tablespace (facultatif mais recommandé)
    CREATE TABLESPACE ts_portfolio
    DATAFILE 'ts_portfolio01.dbf' SIZE 100M AUTOEXTEND ON NEXT 10M MAXSIZE UNLIMITED;

    -- 2. Création de l'utilisateur (schéma)
    CREATE USER PORTFOLIO_MM
    IDENTIFIED BY MonM0tDeP@ss
    DEFAULT TABLESPACE ts_portfolio
    TEMPORARY TABLESPACE temp
    QUOTA UNLIMITED ON ts_portfolio;

    -- 3. Attribution des droits
    GRANT CREATE SESSION TO PORTFOLIO_MM;
    GRANT CREATE TABLE, CREATE VIEW, CREATE SEQUENCE, CREATE TRIGGER TO PORTFOLIO_MM;

""""
    Récapitulatif
        Nom de la « base » : on parle plutôt du schéma PORTFOLIO_MM.
        Vous n’avez pas à créer une nouvelle base de données au sens Oracle ; vous créez un tablespace (optionnel) et un utilisateur qui sera le conteneur de vos tables.
        Ensuite, exécutez les scripts DDL (tables, contraintes, etc.) sous ce schéma.

    Avec cela, votre environnement Oracle est prêt : toutes vos tables (Utilisateur, Projet, etc.) seront créées dans le schéma PORTFOLIO_MM.
""""


```
    -- Connexion au schéma
    CONNECT PORTFOLIO_MM/MonM0tDeP@ss;
    
    -- 1. UTILISATEUR
    CREATE TABLE Utilisateur (
    ID_Utilisateur          NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    Nom                      VARCHAR2(100)    NOT NULL,
    Prenom                   VARCHAR2(100)    NOT NULL,
    Email                    VARCHAR2(200)    NOT NULL UNIQUE,
    MotDePasse               VARCHAR2(200)    NOT NULL,
    DateInscription          DATE             DEFAULT SYSDATE NOT NULL,
    TypeUtilisateur          VARCHAR2(20)     DEFAULT 'artiste' NOT NULL
                                CHECK (TypeUtilisateur IN ('artiste','photographe','designer','admin')),
    VisibiliteProfil         VARCHAR2(10)     DEFAULT 'public' NOT NULL
                                CHECK (VisibiliteProfil IN ('public','prive')),
    StatutCompte             VARCHAR2(10)     DEFAULT 'actif' NOT NULL
                                CHECK (StatutCompte IN ('actif','suspendu','banni'))
    );

    -- 2. PROJET
    CREATE TABLE Projet (
    ID_Projet                NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    Titre                    VARCHAR2(200)    NOT NULL,
    Description              CLOB,
    DateCreation             DATE             DEFAULT SYSDATE NOT NULL,
    DateDerniereModification DATE             DEFAULT SYSDATE NOT NULL,
    StatutProjet             VARCHAR2(10)     DEFAULT 'brouillon' NOT NULL
                                CHECK (StatutProjet IN ('brouillon','publie','archive')),
    ID_Utilisateur           NUMBER           NOT NULL,
    CONSTRAINT fk_projet_user
        FOREIGN KEY (ID_Utilisateur)
        REFERENCES Utilisateur(ID_Utilisateur)
        ON DELETE CASCADE
    );

    -- 3. CATEGORIE
    CREATE TABLE Categorie (
    ID_Categorie             NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    NomCategorie             VARCHAR2(100)    NOT NULL,
    Description              VARCHAR2(500)
    );

    -- 4. PROJET_CATEGORIE (M–N)
    CREATE TABLE Projet_Categorie (
    ID_Projet                NUMBER NOT NULL,
    ID_Categorie             NUMBER NOT NULL,
    CONSTRAINT pk_proj_cat PRIMARY KEY (ID_Projet, ID_Categorie),
    CONSTRAINT fk_pc_projet FOREIGN KEY (ID_Projet)
        REFERENCES Projet(ID_Projet) ON DELETE CASCADE,
    CONSTRAINT fk_pc_cat    FOREIGN KEY (ID_Categorie)
        REFERENCES Categorie(ID_Categorie) ON DELETE CASCADE
    );

    -- 5. FICHIERMULTIMEDIA
    CREATE TABLE FichierMultimedia (
    ID_Fichier               NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    NomFichier               VARCHAR2(200)    NOT NULL,
    TypeFichier              VARCHAR2(10)     NOT NULL
                                CHECK (TypeFichier IN ('image','video','PDF')),
    TailleFichier            NUMBER           NOT NULL,
    CheminFichier            VARCHAR2(400)    NOT NULL,
    DateUpload               DATE             DEFAULT SYSDATE NOT NULL,
    ID_Projet                NUMBER           NOT NULL,
    CONSTRAINT fk_file_proj
        FOREIGN KEY (ID_Projet)
        REFERENCES Projet(ID_Projet)
        ON DELETE CASCADE
    );

    -- 6. MEDIAMETADATA
    CREATE TABLE MediaMetadata (
    ID_Metadata              NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    ID_Fichier               NUMBER           NOT NULL,
    Resolution               VARCHAR2(50),
    Duree                    VARCHAR2(20),
    Format                   VARCHAR2(20),
    Codec                    VARCHAR2(50),
    CONSTRAINT fk_meta_file
        FOREIGN KEY (ID_Fichier)
        REFERENCES FichierMultimedia(ID_Fichier)
        ON DELETE CASCADE
    );

    -- 7. COMMENTAIRE
    CREATE TABLE Commentaire (
    ID_Commentaire           NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    Contenu                  CLOB             NOT NULL,
    DateCommentaire          DATE             DEFAULT SYSDATE NOT NULL,
    Modere                   CHAR(1)          DEFAULT 'N' NOT NULL
                                CHECK (Modere IN ('Y','N')),
    ID_Utilisateur           NUMBER           NOT NULL,
    ID_Projet                NUMBER           NOT NULL,
    CONSTRAINT fk_com_user
        FOREIGN KEY (ID_Utilisateur)
        REFERENCES Utilisateur(ID_Utilisateur)
        ON DELETE CASCADE,
    CONSTRAINT fk_com_proj
        FOREIGN KEY (ID_Projet)
        REFERENCES Projet(ID_Projet)
        ON DELETE CASCADE
    );

    -- 8. PARTAGE
    CREATE TABLE Partage (
    ID_Partage               NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    DatePartage              DATE             DEFAULT SYSDATE NOT NULL,
    NiveauAcces              VARCHAR2(15)     DEFAULT 'lecture' NOT NULL
                                CHECK (NiveauAcces IN ('lecture','commentaire','modification')),
    ID_Utilisateur_Source    NUMBER           NOT NULL,
    ID_Utilisateur_Cible     NUMBER           NOT NULL,
    ID_Projet                NUMBER           NOT NULL,
    CONSTRAINT fk_part_src
        FOREIGN KEY (ID_Utilisateur_Source)
        REFERENCES Utilisateur(ID_Utilisateur)
        ON DELETE CASCADE,
    CONSTRAINT fk_part_cible
        FOREIGN KEY (ID_Utilisateur_Cible)
        REFERENCES Utilisateur(ID_Utilisateur)
        ON DELETE CASCADE,
    CONSTRAINT fk_part_proj
        FOREIGN KEY (ID_Projet)
        REFERENCES Projet(ID_Projet)
        ON DELETE CASCADE
    );

    -- 9. COLLABORATION
    CREATE TABLE Collaboration (
    ID_Collab                NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    ID_Projet                NUMBER           NOT NULL,
    ID_Utilisateur           NUMBER           NOT NULL,
    RoleCollaborateur        VARCHAR2(20)     DEFAULT 'spectateur' NOT NULL
                                CHECK (RoleCollaborateur IN ('auteur','editeur','spectateur')),
    CONSTRAINT fk_collab_proj
        FOREIGN KEY (ID_Projet)
        REFERENCES Projet(ID_Projet)
        ON DELETE CASCADE,
    CONSTRAINT fk_collab_user
        FOREIGN KEY (ID_Utilisateur)
        REFERENCES Utilisateur(ID_Utilisateur)
        ON DELETE CASCADE
    );

```


B. Implémentation Microsoft Access

```
    -- 1. UTILISATEUR
    CREATE TABLE Utilisateur (
    ID_Utilisateur    AUTOINCREMENT PRIMARY KEY,
    Nom                TEXT(100)      NOT NULL,
    Prenom             TEXT(100)      NOT NULL,
    Email              TEXT(200)      NOT NULL,
    MotDePasse         TEXT(200)      NOT NULL,
    DateInscription    DATETIME       DEFAULT Now(),
    TypeUtilisateur    TEXT(20)       DEFAULT 'artiste',
    VisibiliteProfil   TEXT(10)       DEFAULT 'public',
    StatutCompte       TEXT(10)       DEFAULT 'actif'
    );
    CREATE UNIQUE INDEX UX_Utilisateur_Email ON Utilisateur(Email);

    -- 2. PROJET
    CREATE TABLE Projet (
    ID_Projet               AUTOINCREMENT PRIMARY KEY,
    Titre                   TEXT(200)      NOT NULL,
    Description             MEMO,
    DateCreation            DATETIME       DEFAULT Now(),
    DateDerniereModification DATETIME      DEFAULT Now(),
    StatutProjet            TEXT(10)       DEFAULT 'brouillon',
    ID_Utilisateur          LONG           NOT NULL
    );
    ALTER TABLE Projet
    ADD CONSTRAINT FK_Projet_Utilisateur
    FOREIGN KEY (ID_Utilisateur)
    REFERENCES Utilisateur(ID_Utilisateur)
    ON DELETE CASCADE;

    -- 3. CATEGORIE
    CREATE TABLE Categorie (
    ID_Categorie    AUTOINCREMENT PRIMARY KEY,
    NomCategorie    TEXT(100)      NOT NULL,
    Description     MEMO
    );

    -- 4. PROJET_CATEGORIE (M–N)
    CREATE TABLE Projet_Categorie (
    ID_Projet       LONG NOT NULL,
    ID_Categorie    LONG NOT NULL,
    PRIMARY KEY (ID_Projet, ID_Categorie),
    FOREIGN KEY (ID_Projet) REFERENCES Projet(ID_Projet) ON DELETE CASCADE,
    FOREIGN KEY (ID_Categorie) REFERENCES Categorie(ID_Categorie) ON DELETE CASCADE
    );

    -- 5. FICHIERMULTIMEDIA
    CREATE TABLE FichierMultimedia (
    ID_Fichier      AUTOINCREMENT PRIMARY KEY,
    NomFichier      TEXT(200)      NOT NULL,
    TypeFichier     TEXT(10)       NOT NULL,
    TailleFichier   DOUBLE         NOT NULL,
    CheminFichier   TEXT(400)      NOT NULL,
    DateUpload      DATETIME       DEFAULT Now(),
    ID_Projet       LONG           NOT NULL,
    FOREIGN KEY (ID_Projet) REFERENCES Projet(ID_Projet) ON DELETE CASCADE
    );

    -- 6. MEDIAMETADATA
    CREATE TABLE MediaMetadata (
    ID_Metadata     AUTOINCREMENT PRIMARY KEY,
    ID_Fichier      LONG           NOT NULL,
    Resolution      TEXT(50),
    Duree           TEXT(20),
    Format          TEXT(20),
    Codec           TEXT(50),
    FOREIGN KEY (ID_Fichier) REFERENCES FichierMultimedia(ID_Fichier) ON DELETE CASCADE
    );

    -- 7. COMMENTAIRE
    CREATE TABLE Commentaire (
    ID_Commentaire  AUTOINCREMENT PRIMARY KEY,
    Contenu         MEMO           NOT NULL,
    DateCommentaire DATETIME       DEFAULT Now(),
    Modere          YESNO          DEFAULT No,
    ID_Utilisateur  LONG           NOT NULL,
    ID_Projet       LONG           NOT NULL,
    FOREIGN KEY (ID_Utilisateur) REFERENCES Utilisateur(ID_Utilisateur) ON DELETE CASCADE,
    FOREIGN KEY (ID_Projet) REFERENCES Projet(ID_Projet) ON DELETE CASCADE
    );

    -- 8. PARTAGE
    CREATE TABLE Partage (
    ID_Partage              AUTOINCREMENT PRIMARY KEY,
    DatePartage             DATETIME       DEFAULT Now(),
    NiveauAcces             TEXT(15)       DEFAULT 'lecture',
    ID_Utilisateur_Source   LONG           NOT NULL,
    ID_Utilisateur_Cible    LONG           NOT NULL,
    ID_Projet               LONG           NOT NULL,
    FOREIGN KEY (ID_Utilisateur_Source) REFERENCES Utilisateur(ID_Utilisateur) ON DELETE CASCADE,
    FOREIGN KEY (ID_Utilisateur_Cible) REFERENCES Utilisateur(ID_Utilisateur) ON DELETE CASCADE,
    FOREIGN KEY (ID_Projet) REFERENCES Projet(ID_Projet) ON DELETE CASCADE
    );

    -- 9. COLLABORATION
    CREATE TABLE Collaboration (
    ID_Collab        AUTOINCREMENT PRIMARY KEY,
    ID_Projet        LONG           NOT NULL,
    ID_Utilisateur   LONG           NOT NULL,
    RoleCollaborateur TEXT(20)      DEFAULT 'spectateur',
    FOREIGN KEY (ID_Projet) REFERENCES Projet(ID_Projet) ON DELETE CASCADE,
    FOREIGN KEY (ID_Utilisateur) REFERENCES Utilisateur(ID_Utilisateur) ON DELETE CASCADE
    );

```

# À noter pour Access :
    Les contraintes CHECK ne sont pas gérées par SQL DDL, mais vous pouvez définir des règles de validation dans la vue « Conception » de chaque table.

    Les relations (intégrité référentielle) sont souvent créées via l’onglet « Outils de base de données » → « Relations ».

