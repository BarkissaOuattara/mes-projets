#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>

// Gestionnaire de signaux
void signal_handler(int signum) {
    switch (signum) {
        case SIGHUP:
            printf("SIGHUP reçu. Terminal de contrôle fermé.\n");
            break;
        case SIGINT:
            printf("SIGINT reçu. Nettoyage...\n");
            exit(0);
        case SIGQUIT:
            printf("SIGQUIT reçu. Terminaison et core dump.\n");
            exit(0);
        case SIGILL:
            printf("SIGILL reçu. Instruction illégale.\n");
            exit(0);
        case SIGTRAP:
            printf("SIGTRAP reçu. Point d'arrêt ou exception.\n");
            break;
        case SIGABRT:
            printf("SIGABRT reçu. Abandon.\n");
            exit(0);
        case SIGBUS:
            printf("SIGBUS reçu. Erreur de bus.\n");
            exit(0);
        case SIGFPE:
            printf("SIGFPE reçu. Erreur de calcul en virgule flottante.\n");
            exit(0);
        case SIGKILL:
            printf("SIGKILL reçu. Terminaison forcée.\n");
            exit(0);
        case SIGUSR1:
            printf("SIGUSR1 reçu. Continuer l'exécution...\n");
            break;
        case SIGSEGV:
            printf("SIGSEGV reçu. Violation de segmentation.\n");
            exit(0);
        case SIGUSR2:
            printf("SIGUSR2 reçu. Signal utilisateur 2.\n");
            break;
        case SIGPIPE:
            printf("SIGPIPE reçu. Écriture dans un pipe sans lecteur.\n");
            exit(0);
        case SIGALRM:
            printf("SIGALRM reçu. Alarme expirée...\n");
            break;
        case SIGTERM:
            printf("SIGTERM reçu. Terminaison propre...\n");
            exit(0);
        case SIGCHLD:
            printf("SIGCHLD reçu. Changement d'état d'un processus enfant.\n");
            break;
        case SIGCONT:
            printf("SIGCONT reçu. Reprise de l'exécution.\n");
            break;
        case SIGSTOP:
            printf("SIGSTOP reçu. Arrêt de l'exécution.\n");
            break;
        case SIGTSTP:
            printf("SIGTSTP reçu. Arrêt depuis le terminal.\n");
            break;
        case SIGTTIN:
            printf("SIGTTIN reçu. Lecture depuis le terminal en arrière-plan.\n");
            break;
        case SIGTTOU:
            printf("SIGTTOU reçu. Écriture vers le terminal en arrière-plan.\n");
            break;
        default:
            printf("Signal inconnu reçu : %d\n", signum);
    }
}

int main() {
    struct sigaction sa;

    // Initialiser le gestionnaire de signaux
    sa.sa_handler = signal_handler;
    sigemptyset(&sa.sa_mask);
    sa.sa_flags = 0;

    // Associer plusieurs signaux au gestionnaire
    sigaction(SIGHUP, &sa, NULL);
    sigaction(SIGINT, &sa, NULL);
    sigaction(SIGQUIT, &sa, NULL);
    sigaction(SIGILL, &sa, NULL);
    sigaction(SIGTRAP, &sa, NULL);
    sigaction(SIGABRT, &sa, NULL);
    sigaction(SIGBUS, &sa, NULL);
    sigaction(SIGFPE, &sa, NULL);
    sigaction(SIGUSR1, &sa, NULL);
    sigaction(SIGSEGV, &sa, NULL);
    sigaction(SIGUSR2, &sa, NULL);
    sigaction(SIGPIPE, &sa, NULL);
    sigaction(SIGALRM, &sa, NULL);
    sigaction(SIGTERM, &sa, NULL);
    sigaction(SIGCHLD, &sa, NULL);
    sigaction(SIGCONT, &sa, NULL);
    sigaction(SIGTSTP, &sa, NULL);
    sigaction(SIGTTIN, &sa, NULL);
    sigaction(SIGTTOU, &sa, NULL);

    printf("Plusieurs signaux sont configurés.\n");
    printf("Appuyez sur Ctrl+C pour envoyer SIGINT, utilisez 'kill -TERM %d' pour SIGTERM, etc.\n", getpid());

    // Déclencher SIGALRM après 5 secondes
    alarm(5);

    while (1) {
        pause(); // Attend un signal
    }

    return 0;
}
