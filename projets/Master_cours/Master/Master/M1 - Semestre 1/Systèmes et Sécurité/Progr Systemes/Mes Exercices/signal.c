#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <stdlib.h>

int tick = 0;  // Compteur global de ticks

void handler(int signum) {  // Gestionnaire de signal
    tick++;
    printf("tick: %d\n", tick);

    if(tick < 10) {
        alarm(1);  // Reprogramme une nouvelle alarme après 1 seconde
    } else {
        raise(9);  // MARK1 : Envoie le signal SIGKILL (kill -9)
    }
}

int main() {
    signal(SIGALRM, handler);  // MARK2 : Lie SIGALRM au gestionnaire
    alarm(1);                 // Première alarme après 1 seconde
    
    // MARK3
    while(1) {
        pause();  // Met le processus en veille jusqu'à un signal
    }
    
    return 0;
}