
:- use_module(library(clpfd)).

/* ============================================================
   PARTIE 1 - Base de connaissances : faits et règles
   ============================================================ */

humain(socrate).
humain(platon).
humain(aristote).

philosophe(socrate).
philosophe(platon).
philosophe(aristote).

maitre(socrate, platon).
maitre(platon, aristote).

mortel(X) :-
    humain(X).

disciple_indirect(X, Z) :-
    maitre(Y, Z),
    maitre(X, Y).


/* ============================================================
   PARTIE 2 - Mini système expert médical
   ============================================================ */

symptome(patient1, fievre).
symptome(patient1, toux).
symptome(patient1, courbatures).

symptome(patient2, fievre).
symptome(patient2, eruption_cutanee).

diagnostic(Patient, grippe) :-
    symptome(Patient, fievre),
    symptome(Patient, toux),
    symptome(Patient, courbatures).

diagnostic(Patient, rougeole) :-
    symptome(Patient, fievre),
    symptome(Patient, eruption_cutanee).

diagnostic(Patient, inconnu) :-
    symptome(Patient, _),
    \+ diagnostic(Patient, grippe),
    \+ diagnostic(Patient, rougeole).


/* ============================================================
   PARTIE 3 - Ontologie simple en Prolog
   ============================================================ */

sous_classe(animal, etre_vivant).
sous_classe(mammifere, animal).
sous_classe(primate, mammifere).
sous_classe(humain, primate).
sous_classe(chien, mammifere).

instance_de(socrate, humain).
instance_de(rex, chien).

est_un(X, Y) :-
    instance_de(X, Y).

est_un(X, Y) :-
    instance_de(X, Z),
    est_un(Z, Y).

est_un(X, Y) :-
    sous_classe(X, Y).

est_un(X, Y) :-
    sous_classe(X, Z),
    est_un(Z, Y).


/* ============================================================
   PARTIE 4 - CLP(FD) : problème des N reines
   ============================================================ */

n_reines(N, Reines) :-
    length(Reines, N),
    Reines ins 1..N,
    all_different(Reines),
    no_attaque(Reines),
    label(Reines).

no_attaque([]).
no_attaque([R|Rs]) :-
    no_attaque(R, Rs, 1),
    no_attaque(Rs).

no_attaque(_, [], _).
no_attaque(R, [R1|Rs], D) :-
    R1 #\= R + D,
    R1 #\= R - D,
    D1 is D + 1,
    no_attaque(R, Rs, D1).


/* ============================================================
   PARTIE 5 - Exercice pratique 1 : graphe et chemins
   ============================================================ */

arete(a, b).
arete(b, c).
arete(c, d).
arete(a, c).
arete(b, d).

chemin(X, Y, [X, Y]) :-
    arete(X, Y).

chemin(X, Y, [X|Chemin]) :-
    arete(X, Z),
    Z \= Y,
    chemin(Z, Y, Chemin).


/* ============================================================
   PARTIE 6 - Exercice pratique 2 : coloration de graphe avec CLP(FD)
   ============================================================ */

colorier(Carte) :-
    Carte = [France, Belgique, Allemagne, Espagne, Italie, PaysBas],
    Carte ins 1..3,
    France   #\= Belgique,
    France   #\= Allemagne,
    France   #\= Espagne,
    France   #\= Italie,
    Belgique #\= PaysBas,
    Belgique #\= Allemagne,
    label(Carte).


/* ============================================================
   PARTIE 7 - Exercice pratique 3 : mini-ILP, apprendre pair
   =========================================================== */

zero(0).

suivant(0, 1).
suivant(1, 2).
suivant(2, 3).
suivant(3, 4).
suivant(4, 5).
suivant(5, 6).
suivant(6, 7).
suivant(7, 8).
suivant(8, 9).
suivant(9, 10).

pair(0).
pair(X) :-
    suivant(X1, X),
    suivant(X2, X1),
    pair(X2).

/* ============================================================
   PARTIE 8 - Exemple neurosymbolique simplifié : scènes visuelles
   ============================================================ */

objet(o1).
objet(o2).
objet(o3).

couleur(o1, rouge).
couleur(o2, bleu).
couleur(o3, rouge).

forme(o1, cube).
forme(o2, sphere).
forme(o3, sphere).

taille(o1, grand).
taille(o2, petit).
taille(o3, grand).

a_gauche(o1, o2).
a_gauche(o2, o3).

rouge_et_grand(X) :-
    couleur(X, rouge),
    taille(X, grand).

cube_gauche_sphere_bleue :-
    forme(C, cube),
    forme(S, sphere),
    couleur(S, bleu),
    a_gauche(C, S).
