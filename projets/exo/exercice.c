#include <stdio.h>
#include <stdlib.h>

#define MAX 100

typedef struct {
    int id, debut, fin;
} Activite;

int comparerFin(const void *a, const void *b) {
    Activite x = *(Activite *)a;
    Activite y = *(Activite *)b;
    return x.fin - y.fin;
}

int main() {
    Activite A[MAX], solution[MAX];
    int n, nb = 0;
    int derniereFin;

    printf("Donner le nombre d'activites : ");
    scanf("%d", &n);

    for (int i = 0; i < n; i++) {
        A[i].id = i + 1;
        printf("Activite %d (debut fin) : ", i + 1);
        scanf("%d %d", &A[i].debut, &A[i].fin);
    }

    qsort(A, n, sizeof(Activite), comparerFin);

    solution[nb++] = A[0];
    derniereFin = A[0].fin;

    for (int i = 1; i < n; i++) {
        if (A[i].debut >= derniereFin) {
            solution[nb++] = A[i];
            derniereFin = A[i].fin;
        }
    }

    printf("\nActivites choisies : ");
    for (int i = 0; i < nb; i++) {
        printf("I%d=[%d,%d[ ",
               solution[i].id,
               solution[i].debut,
               solution[i].fin);
    }

    printf("\nNombre d'activites choisies : %d\n", nb);

    return 0;
}