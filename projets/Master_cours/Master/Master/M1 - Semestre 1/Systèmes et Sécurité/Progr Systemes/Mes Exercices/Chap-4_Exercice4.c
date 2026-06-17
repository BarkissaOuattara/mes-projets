#include <stdio.h>
#include <signal.h>

void handler(int sig) {
    printf("Signal %d reçu par raise() !\n", sig);
}

int main() {
    signal(SIGUSR1, handler);  
    printf("Envoi de SIGUSR1 à moi-même...\n");
    raise(SIGUSR1);  // Envoie SIGUSR1 au processus actuel
    return 0;
}
