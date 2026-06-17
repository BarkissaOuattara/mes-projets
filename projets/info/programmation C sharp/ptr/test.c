#include<stdio.h>
#include<stdlib.h>

typedef struct cell
{
    int data;
    struct cell *next;
}cell;

int main()
{
 cell *p;
 int var;
 printf("entrer var: ");
 scanf("%d", &var);
 p->data=var;
 p -> next=(cell*)malloc(sizeof(cell));
 while (p->next == NULL)
 {
    printf("data = %d\n", p->data);
    printf("next = %d\n", p->next);
    p = p->next;
    printf("fin");
 }
 printf("fin");

}
