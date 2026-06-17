// Inclusion des bibliothèques nécessaires
#include <stdio.h>  // Pour les fonctions d'entrée/sortie standard comme printf
#include <stdlib.h> // Pour les fonctions de gestion de la mémoire et de contrôle des processus comme exit
#include <unistd.h> // Pour les appels système comme fork, pipe, read, write
#include <sys/types.h> // Pour les types de données utilisés par les appels système
#include <sys/wait.h>  // Pour la fonction wait qui permet d'attendre la fin d'un processus enfant
#include <string.h>  // Pour les fonctions de manipulation de chaînes de caractères comme strlen

int main() {
    // Déclaration des variables pour le pipe
    int pipe_fd[2]; // Tableau pour stocker les descripteurs de fichier du pipe
    pid_t pid;      // Variable pour stocker l'identifiant du processus
    char buffer[100]; // Tampon pour stocker les données lues depuis le pipe
    const char message[] = "Bonjour depuis le processus parent!"; // Message à envoyer via le pipe

    // Création d'un pipe
    // pipe_fd[0] est utilisé pour la lecture, pipe_fd[1] pour l'écriture
    if (pipe(pipe_fd) == -1) {
        perror("pipe failed"); // Affiche un message d'erreur si la création du pipe échoue
        exit(1); // Termine le programme avec un statut d'erreur
    }

    // Création d'un nouveau processus avec fork()
    // fork() crée un nouveau processus en dupliquant le processus appelant
    pid = fork();

    if (pid < 0) {
        // Erreur lors de la création du processus
        perror("fork failed"); // Affiche un message d'erreur si fork échoue
        exit(1); // Termine le programme avec un statut d'erreur

    } else if (pid == 0) { // Processus enfant

        // Fermeture de l'extrémité d'écriture du pipe dans le processus enfant
        close(pipe_fd[1]);

        // Lecture du message depuis le pipe
        read(pipe_fd[0], buffer, sizeof(buffer));
        printf("Processus enfant a lu : %s\n", buffer); // Affiche le message lu

        // Affichage des identifiants du processus enfant
        printf("Processus enfant - PID: %d, PPID: %d\n", getpid(), getppid());
        printf("Processus enfant - UID réel: %d, UID effectif: %d\n", getuid(), geteuid());

        // Fermeture de l'extrémité de lecture du pipe dans le processus enfant
        close(pipe_fd[0]);

        // Remplacement de l'image du processus enfant par un nouveau programme
        // execl exécute la commande /bin/ls
        execl("/bin/ls", "ls", NULL);

        // Si execl échoue, affiche un message d'erreur et termine le processus
        perror("execl failed");
        exit(1);
        
    } else { // Processus parent

        // Fermeture de l'extrémité de lecture du pipe dans le processus parent
        close(pipe_fd[0]);

        // Écriture du message dans le pipe
        write(pipe_fd[1], message, strlen(message) + 1);

        // Fermeture de l'extrémité d'écriture du pipe dans le processus parent
        close(pipe_fd[1]);

        // Affichage des identifiants du processus parent
        printf("Processus parent - PID: %d, PPID: %d\n", getpid(), getppid());
        printf("Processus parent - UID réel: %d, UID effectif: %d\n", getuid(), geteuid());

        // Attente de la fin du processus enfant
        int status;
        wait(&status); // Suspend l'exécution du processus parent jusqu'à ce que le processus enfant se termine
        printf("Processus enfant terminé avec le statut %d\n", status); // Affiche le statut de terminaison du processus enfant
    }

    return 0; // Termine le programme avec succès
}
