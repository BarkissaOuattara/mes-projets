#include <stdio.h>
#include <stdlib.h>
#include <sys/stat.h>

/* Fonction qui copie le contenu d'un fichier dans un autre */
void copierfich(FILE *fpe, FILE *fps) {
    int c;
    while ((c = getc(fpe)) != EOF) {
        putc(c, fps);
    }
}

/* Fonction pour vérifier si un chemin est un fichier régulier */
int est_fichier(const char *chemin) {
    struct stat statbuf;
    if (stat(chemin, &statbuf) != 0) {
        perror(chemin);
        return 0; // Erreur lors de l'accès au chemin
    }
    return S_ISREG(statbuf.st_mode); // Retourne 1 si c'est un fichier régulier
}

int main(int argc, char *argv[]) {
    FILE *fp;

    if (argc == 1) {
        copierfich(stdin, stdout);  /* Pas d'arguments : copie l'entrée standard */
    } else {
        while (--argc > 0) {
            if (est_fichier(*++argv)) { // Vérifie si l'argument est un fichier
                if ((fp = fopen(*argv, "r")) == NULL) {
                    fprintf(stderr, "cat : impossible d'ouvrir %s\n", *argv);
                    return EXIT_FAILURE;
                } else {
                    copierfich(fp, stdout); // Affiche le contenu du fichier
                    fclose(fp);
                }
            } else {
                // Si ce n'est pas un fichier, affiche une erreur
                fprintf(stderr, "cat : %s n'est pas un fichier valide.\n", *argv);
            }
        }
    }

    return EXIT_SUCCESS;
}
