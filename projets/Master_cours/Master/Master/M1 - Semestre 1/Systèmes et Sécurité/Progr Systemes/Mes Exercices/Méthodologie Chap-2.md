# **Travaux Pratiques : Programmation Système sous Linux**

## **Objectif du TP**
L'objectif de ce TP est de permettre aux étudiants de maîtriser les concepts de la programmation système sous Linux, en manipulant des fichiers, des processus et des signaux.

---

## **Partie 1 : Manipulation des fichiers**

### **Exercice 1 : Ouverture et fermeture de fichiers**
- Écrire un programme en C qui :
  - Ouvre un fichier nommé `test.txt` en mode lecture/écriture (`O_RDWR`)
  - Affiche un message si l'ouverture réussit.
  - Ferme le fichier et affiche un message indiquant la fermeture.
  
- Tester l'ouverture avec différents modes :
  - `O_RDONLY`, `O_WRONLY | O_CREAT | O_TRUNC`
  
### **Exercice 2 : Lecture et écriture**
- Modifier le programme pour :
  - Écrire la chaîne "Programmation Système" dans le fichier.
  - Lire le contenu et l'afficher.
  
### **Exercice 3 : Déplacement du curseur et lecture partielle**
- Utiliser `lseek()` pour se déplacer dans le fichier.
- Lire et afficher une portion spécifique du fichier.

### **Exercice 4 : Duplication et redirection des descripteurs**
- Ouvrir un fichier en mode écriture.
- Dupliquer le descripteur avec `dup2()`.
- Utiliser `write()` sur le descripteur dupliqué.

### **Exercice 5 : Gestion des répertoires**
- Ouvrir et parcourir un répertoire avec `opendir()`, `readdir()`.
- Afficher la liste des fichiers.

---

## **Partie 2 : Gestion des processus**

### **Exercice 6 : Création de processus**
- Utiliser `fork()` pour créer un processus enfant.
- Afficher un message différent dans le parent et l'enfant.

### **Exercice 7 : Communication entre processus**
- Utiliser `pipe()` pour permettre au processus parent d'envoyer un message à l'enfant.

### **Exercice 8 : Exécution d'un programme externe**
- Utiliser `exec()` pour exécuter une commande Linux.

---

## **Partie 3 : Gestion des signaux**

### **Exercice 9 : Capture de signaux**
- Utiliser `signal()` pour capturer `SIGINT`.
- Afficher un message lorsque l'utilisateur tape `Ctrl+C`.

### **Exercice 10 : Envoi de signaux**
- Créer un programme qui envoie `SIGUSR1` à un autre processus avec `kill()`.

---

## **Partie 4 : Synchronisation et verrous**

### **Exercice 11 : Verrouillage de fichiers**
- Utiliser `fcntl()` pour empêcher deux processus d'écrire en même temps.

### **Exercice 12 : Synchronisation avec un tube nommé**
- Utiliser `mkfifo()` pour échanger des messages entre processus.

---

## **Partie 5 : Gestion avancée**

### **Exercice 13 : Utilisation de mémoire partagée**
- Créer une zone de mémoire partagée avec `shmget()` et `shmat()`.

### **Exercice 14 : Gestion des threads**
- Créer et exécuter des threads avec `pthread_create()`.
- Synchroniser les threads avec un mutex.

---

## **Conclusion**
Ce TP couvre les bases essentielles et avancées de la programmation système sous Linux. Il permet de comprendre comment manipuler les fichiers, gérer les processus, capter les signaux et synchroniser l'accès aux ressources partagées.

