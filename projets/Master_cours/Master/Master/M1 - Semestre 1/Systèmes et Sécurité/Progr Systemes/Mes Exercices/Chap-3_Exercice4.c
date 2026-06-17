#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>

int main() {
    for (int i = 0; i < 100; i++) {  // Crée 100 zombies
        pid_t pid = fork();
        if (pid == 0) {  // Enfant
            printf("Enfant %d (PID: %d) terminé\n", i, getpid());
            exit(0);  // Terminaison immédiate → devient zombie
        }
        // Parent ne récupère pas le statut (pas de wait())
    }
    printf("Parent (PID: %d) ne récupère aucun statut...\n", getpid());
    sleep(30);  // Laisse le temps d'observer les zombies avec ps aux | grep Z
    return 0;
}