#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <time.h>

// Fonction pour générer des nombres aléatoires
int generate_random(int i, int n) {
    return rand() % 100 + 1;  // Génère un nombre aléatoire entre 1 et 100
}

// Fonction pour additionner deux nombres
int add(int a, int b) {
    return a + b;
}

// Fonction pour afficher un résultat
void afficher(int a) {
    printf("Le résultat de l'addition est : %d\n", a);
}

int main() {
    // Initialisation de rand() avec un seed basé sur l'heure actuelle
    srand(time(NULL));

    // Création des pipes
    int pipe1[2]; // Pipe pour envoyer les nombres aléatoires
    int pipe2[2]; // Pipe pour envoyer le résultat
    if (pipe(pipe1) == -1 || pipe(pipe2) == -1) {
        perror("pipe");
        exit(1);
    }

    // Création du processus fils
    pid_t pid = fork();
    if (pid == -1) {
        perror("fork");
        exit(1);
    }

    if (pid == 0) { // Processus fils
        close(pipe1[1]); // Fermeture de l'écriture dans le pipe1
        close(pipe2[0]); // Fermeture de la lecture dans le pipe2

        int a, b;

        // Lecture des deux nombres envoyés par le processus parent
        read(pipe1[0], &a, sizeof(int));
        read(pipe1[0], &b, sizeof(int));

        // Calcul de l'addition
        int result = add(a, b);

        // Envoi du résultat au parent
        write(pipe2[1], &result, sizeof(int));

        close(pipe1[0]); // Fermeture de la lecture dans le pipe1
        close(pipe2[1]); // Fermeture de l'écriture dans le pipe2

        exit(0);
    } else { // Processus parent
        close(pipe1[0]); // Fermeture de la lecture dans le pipe1
        close(pipe2[1]); // Fermeture de l'écriture dans le pipe2

        // Génération de deux nombres aléatoires
        int a = generate_random(0, 100);
        int b = generate_random(0, 100);

        // Envoi des deux nombres au processus fils
        write(pipe1[1], &a, sizeof(int));
        write(pipe1[1], &b, sizeof(int));

        // Attente du résultat
        int result;
        read(pipe2[0], &result, sizeof(int));

        // Affichage du résultat reçu
        afficher(result);

        close(pipe1[1]); // Fermeture de l'écriture dans le pipe1
        close(pipe2[0]); // Fermeture de la lecture dans le pipe2

        // Attente de la fin du processus fils
        wait(NULL);
    }

    return 0;
}
