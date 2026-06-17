#include <stdio.h>
#include <stdlib.h>

#define MAX 100

typedef struct {
    int id, debut, fin;
} Intervalle;

int duree(Intervalle x) {
    return x.fin - x.debut;
}

int cmpDuree(const void *a, const void *b) {
    Intervalle x = *(Intervalle *)a;
    Intervalle y = *(Intervalle *)b;

    if (duree(x) != duree(y))
        return duree(x) - duree(y);

    return x.id - y.id; // garde l'ordre initial si durees egales
}

int compatibles(Intervalle a, Intervalle b) {
    return a.fin <= b.debut || b.fin <= a.debut;
}

int main() {
    Intervalle E[MAX], theta[MAX];
    int n, nb = 0;

    printf("Nombre d'intervalles : ");
    scanf("%d", &n);

    for (int i = 0; i < n; i++) {
        E[i].id = i + 1;
        printf("Intervalle %d (debut fin) : ", i + 1);
        scanf("%d %d", &E[i].debut, &E[i].fin);
    }

    qsort(E, n, sizeof(Intervalle), cmpDuree);

    printf("\nIntervalles tries par duree croissante :\n");
    for (int i = 0; i < n; i++) {
        printf("I%d = [%d,%d[ duree=%d\n",
               E[i].id, E[i].debut, E[i].fin, duree(E[i]));
    }

    for (int i = 0; i < n; i++) {
        int ok = 1;

        for (int j = 0; j < nb; j++) {
            if (!compatibles(E[i], theta[j])) {
                ok = 0;
                break;
            }
        }

        if (ok) {
            theta[nb++] = E[i];
        }
    }

    printf("\nTheta = { ");
    for (int i = 0; i < nb; i++) {
        printf("[%d,%d[ ", theta[i].debut, theta[i].fin);
    }
    printf("}\n");

    printf("Nombre choisi = %d\n", nb);

    return 0;
}