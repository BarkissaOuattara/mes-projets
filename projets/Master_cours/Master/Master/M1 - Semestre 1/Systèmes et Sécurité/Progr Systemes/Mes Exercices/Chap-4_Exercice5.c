#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>

int main() {
    pid_t pid = fork();

    if (pid == 0) {  // Processus fils
        printf("Fils : En attente...\n");
        sleep(10);
        printf("Fils : Terminé !\n");
    } else {  // Processus père
        sleep(2);
        printf("Père : Envoi de SIGTERM au fils !\n");
        kill(pid, SIGTERM);  // Tue le fils
    }

    return 0;
}


/*

#include <stdio.h>
#include <signal.h>
#include <unistd.h>

int main() {
    sigset_t mask;
    sigemptyset(&mask);
    sigaddset(&mask, SIGINT);

    printf("Blocage de SIGINT (Ctrl+C désactivé)...\n");
    sigprocmask(SIG_BLOCK, &mask, NULL);

    sleep(5);  // SIGINT ne sera pas reçu ici

    printf("Déblocage de SIGINT...\n");
    sigprocmask(SIG_UNBLOCK, &mask, NULL);

    while (1) {
        printf("Processus en cours... (Ctrl+C fonctionne à nouveau)\n");
        sleep(2);
    }

    return 0;
}


*/