#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h> // Pour la fonction sleep

#define NUM_THREADS 5

// Structure pour passer des données aux threads
typedef struct {
    int thread_id;
    int delai;
} thread_data;

// Mutex pour la synchronisation
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

// Fonction simple pour calculer la somme des entiers de 1 à n
int somme(int n) {
    return (n * (n + 1)) / 2;
}

// Variable de condition pour la synchronisation
pthread_cond_t cond = PTHREAD_COND_INITIALIZER;

// Compteur partagé entre les threads
int compteur = 0;

void *fonction_thread(void *arg) {
    thread_data *data = (thread_data *)arg;
    int thread_id = data->thread_id;
    int delai = data->delai;

    // Verrouillage du mutex avant d'accéder à la section critique
    pthread_mutex_lock(&mutex);

    // Incrémentation du compteur partagé
    compteur++;
    printf("Thread %d : Démarrage. Délai = %d secondes. Compteur = %d\n", thread_id, delai, compteur);

    // Déverrouillage du mutex après avoir accédé à la section critique
    pthread_mutex_unlock(&mutex);

    // Simulation d'un travail avec un délai
    sleep(delai);

    // Verrouillage du mutex avant d'accéder à la section critique
    pthread_mutex_lock(&mutex);

    // Décrémentation du compteur partagé
    compteur--;
    printf("Thread %d : Terminé. Compteur = %d\n", thread_id, compteur);

    // Signaler aux autres threads que le compteur a changé
    pthread_cond_signal(&cond);

    // Déverrouillage du mutex après avoir accédé à la section critique
    pthread_mutex_unlock(&mutex);

    pthread_exit(NULL);
}

int main() {
    pthread_t threads[NUM_THREADS];
    thread_data data[NUM_THREADS];
    int rc, i;

    // Création des threads
    for (i = 0; i < NUM_THREADS; i++) {
        data[i].thread_id = i;
        data[i].delai = i + 1; // Chaque thread a un délai différent

        rc = pthread_create(&threads[i], NULL, fonction_thread, (void *)&data[i]);
        if (rc) {
            printf("Erreur lors de la création du thread %d\n", i);
            exit(-1);
        }
    }

    // Attente de la fin des threads
    for (i = 0; i < NUM_THREADS; i++) {
        pthread_join(threads[i], NULL);
    }

    // Destruction du mutex et de la variable de condition
    pthread_mutex_destroy(&mutex);
    pthread_cond_destroy(&cond);

    printf("Tous les threads ont terminé\n");
    return 0;
}
