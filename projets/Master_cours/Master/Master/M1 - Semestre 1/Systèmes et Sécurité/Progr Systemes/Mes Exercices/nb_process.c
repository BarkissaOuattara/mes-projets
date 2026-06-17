#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main() {
    int i, status;
    int process_count = 1;  // Commence avec le processus parent

    for (i = 0; i < 3; i++) {
        if (fork() == 0) {  // Processus fils
            printf("Enfant %d: Hello World!\n", i);
            exit(1);  // Terminaison immédiate du fils, c'est comme si il est mort...
        } else {  // Processus parent
            process_count++;  // Incrémente le compteur à chaque fork
            printf("Parent %d: Hello World!\n", i);
        }
    }

    // Affichage du nombre total de processus créés
    printf("Nombre total de processus créés : %d\n", process_count);

    // Appel à pstree pour visualiser la hiérarchie des processus
    //printf("\nAffichage de la hiérarchie des processus avec pstree:\n");
    //system("pstree -p");  // Exécute la commande pstree pour afficher les processus
    //system("pstree");  // Exécute la commande pstree pour afficher les processus

    return 0;
}
