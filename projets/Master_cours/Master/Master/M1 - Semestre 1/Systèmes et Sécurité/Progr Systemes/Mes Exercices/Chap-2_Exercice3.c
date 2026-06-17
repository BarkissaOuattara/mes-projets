#include <stdio.h>
#include <fcntl.h>   // Pour open()
#include <unistd.h>  // Pour lseek(), read(), close()
#include <errno.h>   // Pour gérer les erreurs
#include <string.h>  // Pour strerror()

int main() {
    int fd;
    char buffer[11]; // On réserve 10 caractères + 1 pour le terminateur '\0'
    ssize_t bytesRead;

    // Ouvrir le fichier en lecture seule
    fd = open("test.txt", O_RDONLY);
    if (fd == -1) {
        perror("Erreur lors de l'ouverture du fichier");
        return 1;
    }

    // Déplacement du curseur de 10 octets après le début
    if (lseek(fd, 10, SEEK_SET) == -1) {
        perror("Erreur lors du déplacement du curseur");
        close(fd);
        return 1;
    }

    // Lecture de 10 octets après la position courante
    bytesRead = read(fd, buffer, 10);
    if (bytesRead == -1) {
        perror("Erreur lors de la lecture");
        close(fd);
        return 1;
    }

    buffer[bytesRead] = '\0'; // Ajouter un terminateur de chaîne
    printf("Les 10 octets lus après le déplacement : %s\n", buffer);

    close(fd); // Fermeture du fichier
    return 0;
}
