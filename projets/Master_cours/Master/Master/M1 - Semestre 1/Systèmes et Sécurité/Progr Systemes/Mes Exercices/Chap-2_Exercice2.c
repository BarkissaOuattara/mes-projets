#include <stdio.h>
#include <fcntl.h>   // Pour open()
#include <unistd.h>  // Pour write(), read(), close()
#include <errno.h>   // Pour gérer les erreurs
#include <string.h>  // Pour strlen()

int main() {
    int fd;
    char buffer[256];
    ssize_t bytesRead;

    // Écriture dans le fichier
    fd = open("test.txt", O_WRONLY | O_CREAT | O_TRUNC, 0644);
    if (fd == -1) {
        perror("Erreur lors de l'ouverture du fichier en écriture");
        return 1;
    }

    const char *text = "Programmation Système";
    if (write(fd, text, strlen(text)) == -1) {
        perror("Erreur lors de l'écriture");
        close(fd);
        return 1;
    }

    printf("Texte écrit avec succès dans le fichier.\n");

    close(fd); // Fermeture après écriture

    // Lecture du fichier
    fd = open("test.txt", O_RDONLY);
    if (fd == -1) {
        perror("Erreur lors de l'ouverture du fichier en lecture");
        return 1;
    }

    bytesRead = read(fd, buffer, sizeof(buffer) - 1); // Lecture du fichier
    if (bytesRead == -1) {
        perror("Erreur lors de la lecture");
        close(fd);
        return 1;
    }

    buffer[bytesRead] = '\0'; // Ajouter un terminateur de chaîne
    printf("Contenu du fichier : %s\n", buffer);

    close(fd); // Fermeture après lecture
    return 0;
}
