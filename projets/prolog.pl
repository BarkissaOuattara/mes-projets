:- initialization(main).
main :- write('Hello, World!').
mortel(X) :- humain(X).
disciple_indirect(X, Z) :-
    maitre(Y, Z),
    maitre(X, Z).
% -- Faits : assertions inconditionnelles --
humain(socrate).
humain(platon).
humain(aristote).
philosophe(socrate).
philosophe(platon).
philosophe(aristote).
maitre(socrate,platon).
maitre(platon, aristote).
