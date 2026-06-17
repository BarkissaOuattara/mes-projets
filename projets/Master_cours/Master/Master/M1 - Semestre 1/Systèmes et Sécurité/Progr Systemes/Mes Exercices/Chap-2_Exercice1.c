//Chap-2_Exercice1.c

#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include <unistd.h> // pour les appels systèmes de lecture/écriture.
#include <errno.h>  // pour les codes d'erreur.
#include <ctype.h>  // pour les fonctions de manipulation de caractères.
#include <limits.h> // pour les constantes de taille.
#include <dirent.h> // pour les fonctions de répertoire.
#include <time.h>   // pour les fonctions de temps.

#include <sys/stat.h> // pour le mode d’ouverture.
#include <fcntl.h>

// Chap-2_Exercice1.txt

int main(int argc, char *argv[]) {
    int fd;

    // Ouverture du fichier en lecture seule
    //fd = open("Chap-2_Exercice1.txt", O_RDWR | O_CREAT, 0644); // Lecture mais cree le fichier si ca n'existe pas 
    //fd = open("Chap-2_Exercice1.txt", O_RDONLY, 0644); // Lecture seule 
    fd = open("Chap-2_Exercice1.txt", O_WRONLY | O_CREAT | O_TRUNC, 0644); //Écriture seule avec suppression du contenu existant

    if(fd == -1){
        perror("Erreur d'ouverture du fichier");
        return 1;
    } else {
        printf("Fichier ouvert avec succès (descripteur : %d)\n", fd);
    }

    // On ferme le fichier
    if(close(fd) == -1){
        perror("Erreur de fermeture du fichier");
        return 1;
    } else {
        printf("Fichier fermé avec succès\n voici la valeur de close(fd) : %d",close(fd));
    }

    return 0;
}
