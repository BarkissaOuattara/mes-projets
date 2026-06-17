#include <stdio.h>
#include <signal.h>
#include <unistd.h>

void handle_sigint(int sig) {
    printf("\nSignal SIGINT reçu (Ctrl+C ignoré) !\n");
}

int main() {
    signal(SIGINT, handle_sigint);  // Capture SIGINT (Ctrl+C)
    
    while (1) {
        printf("Processus en cours... (Appuyez sur Ctrl+C)\n");
        sleep(2);
    }
    
    return 0;
}
