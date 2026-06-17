#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>

// Déclaration du gestionnaire de signaux
// Cette fonction sera appelée lorsque le processus reçoit un signal
void signal_handler(int signum) {
    // Vérifie quel signal a été reçu
    if (signum == SIGINT) {
        // Si SIGINT est reçu (généralement via Ctrl+C)
        printf("SIGINT reçu. Nettoyage...\n");
        // Effectuer des opérations de nettoyage ici
        // Par exemple, fermer des fichiers ou libérer de la mémoire
        exit(0); // Termine le programme proprement
    } else if (signum == SIGUSR1) {
        // Si SIGUSR1 est reçu (signal utilisateur 1)
        printf("SIGUSR1 reçu. Continuer l'exécution...\n");
        // Continuer l'exécution ou effectuer une action spécifique
    }
}

int main() {
    struct sigaction sa; // Structure pour définir l'action du signal
    sigset_t mask;      // Ensemble de signaux pour le masquage

    // Initialiser le gestionnaire de signaux
    sa.sa_handler = signal_handler; // Associe la fonction signal_handler
    sigemptyset(&sa.sa_mask);      // Initialise le masque de signaux à vide
    sa.sa_flags = 0;                // Pas de flags spéciaux

    // Associer SIGINT et SIGUSR1 au gestionnaire
    sigaction(SIGINT, &sa, NULL);  // Associe SIGINT à signal_handler
    sigaction(SIGUSR1, &sa, NULL); // Associe SIGUSR1 à signal_handler

    // Bloquer SIGUSR1
    sigemptyset(&mask);             // Initialise le masque
    sigaddset(&mask, SIGUSR1);      // Ajoute SIGUSR1 au masque
    sigprocmask(SIG_SETMASK, &mask, NULL); // Applique le masque au processus

    printf("SIGINT et SIGUSR1 sont configurés. SIGUSR1 est bloqué.\n");
    printf("Appuyez sur Ctrl+C pour envoyer SIGINT ou utilisez 'kill -USR1 %d' pour envoyer SIGUSR1.\n", getpid());

    while (1) {
        pause(); // Met le processus en pause jusqu'à la réception d'un signal
    }

    return 0;
}
