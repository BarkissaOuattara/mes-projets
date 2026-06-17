#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/sendfile.h>
#include <errno.h>
#include <string.h>
#include <dirent.h>

#define BUFFER_SIZE 4096

void copier_fichier(const char *source, const char *destination);
void copier_repertoire(const char *source, const char *destination);

void copier(const char *source, const char *destination) {
    struct stat src_stat;
    if (lstat(source, &src_stat) == -1) {
        perror("Erreur lors de l'obtention des informations sur la source");
        exit(EXIT_FAILURE);
    }

    if (S_ISDIR(src_stat.st_mode)) {
        copier_repertoire(source, destination);
    } else {
        copier_fichier(source, destination);
    }
}

void copier_repertoire(const char *source, const char *destination) {
    DIR *dir = opendir(source);
    if (!dir) {
        perror("Erreur ouverture répertoire source");
        exit(EXIT_FAILURE);
    }

    if (mkdir(destination, 0777) == -1 && errno != EEXIST) {
        perror("Erreur création répertoire destination");
        closedir(dir);
        exit(EXIT_FAILURE);
    }

    struct dirent *entry;
    char src_path[BUFFER_SIZE], dest_path[BUFFER_SIZE];
    while ((entry = readdir(dir)) != NULL) {
        if (strcmp(entry->d_name, ".") == 0 || strcmp(entry->d_name, "..") == 0) {
            continue;
        }
        snprintf(src_path, BUFFER_SIZE, "%s/%s", source, entry->d_name);
        snprintf(dest_path, BUFFER_SIZE, "%s/%s", destination, entry->d_name);
        copier(src_path, dest_path);
    }
    closedir(dir);
}

void copier_fichier(const char *source, const char *destination) {
    struct stat src_stat;
    if (lstat(source, &src_stat) == -1) {
        perror("Erreur lors de l'obtention des informations sur la source");
        exit(EXIT_FAILURE);
    }

    if (S_ISLNK(src_stat.st_mode)) {
        char lien_cible[BUFFER_SIZE];
        ssize_t len = readlink(source, lien_cible, sizeof(lien_cible) - 1);
        if (len == -1) {
            perror("Erreur lors de la lecture du lien symbolique");
            exit(EXIT_FAILURE);
        }
        lien_cible[len] = '\0';
        if (symlink(lien_cible, destination) == -1) {
            perror("Erreur lors de la création du lien symbolique");
            exit(EXIT_FAILURE);
        }
        return;
    }

    int src_fd = open(source, O_RDONLY);
    if (src_fd == -1) {
        perror("Erreur ouverture source");
        exit(EXIT_FAILURE);
    }

    int dest_fd = open(destination, O_WRONLY | O_CREAT | O_TRUNC, src_stat.st_mode & 0777);
    if (dest_fd == -1) {
        perror("Erreur ouverture destination");
        close(src_fd);
        exit(EXIT_FAILURE);
    }

#ifdef __linux__
    off_t offset = 0;
    ssize_t bytes_copied;
    while ((bytes_copied = sendfile(dest_fd, src_fd, &offset, src_stat.st_size)) > 0);
    if (bytes_copied == -1) {
        perror("Erreur lors de la copie avec sendfile");
        close(src_fd);
        close(dest_fd);
        exit(EXIT_FAILURE);
    }
#else
    char buffer[BUFFER_SIZE];
    ssize_t bytes_read, bytes_written;
    while ((bytes_read = read(src_fd, buffer, BUFFER_SIZE)) > 0) {
        char *ptr = buffer;
        while (bytes_read > 0) {
            bytes_written = write(dest_fd, ptr, bytes_read);
            if (bytes_written <= 0) {
                perror("Erreur écriture");
                close(src_fd);
                close(dest_fd);
                exit(EXIT_FAILURE);
            }
            bytes_read -= bytes_written;
            ptr += bytes_written;
        }
    }
    if (bytes_read == -1) {
        perror("Erreur lecture");
    }
#endif
    
    if (fchmod(dest_fd, src_stat.st_mode) == -1) {
        perror("Erreur lors de la modification des permissions");
    }

    close(src_fd);
    close(dest_fd);
}

int main(int argc, char *argv[]) {
    if (argc != 3) {
        fprintf(stderr, "Usage : %s <source> <destination>\n", argv[0]);
        exit(EXIT_FAILURE);
    }
    copier(argv[1], argv[2]);
    return EXIT_SUCCESS;
}
