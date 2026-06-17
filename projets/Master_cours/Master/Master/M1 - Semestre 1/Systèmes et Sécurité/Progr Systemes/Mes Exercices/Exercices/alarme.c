#include <stdio.h>
#include <unistd.h>  // Pour la fonction sleep()
#include <stdlib.h>  // Pour exit()

int main() {
    // Compte à rebours de 10 à 0
    for (int i = 10; i >= 0; i--) {
        printf("%d\n", i);
        fflush(stdout);  // Force l'affichage immédiat sur la sortie standard
        if (i > 0) sleep(1);  // Pause de 1 seconde, mais pas après 0
    }

    // Affiche "TOP DEPART"
    printf("TOP DEPART\n");
    return 0;
}
