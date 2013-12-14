%% ================
%% Graphs in Prolog
%% ================

%% [V,E]
%% [[a,b,c][a,b][a,c][b,b]].

%% -----------------
%% Helpers
%% -----------------

last([X],X).
last([_|L],X) :- last(L,X).

append([],Z,Z).
append([X|L],Z,[X|R]) :- append(L,Z,R).

member(L,X) :- append(_,[X|_],L).
sublist(L,Z) :- append(_,K,L), append(Z,_,K).

in(L,X,Z) :- append(L1,L2,L), append(L1,[X|L2],Z).
perm([],[]).
perm([X],[X]).
perm([X|L],Z) :- perm(L,P), in(P,X,Z).

subset(L,Z) :- perm(L,P), sublist(P,Z).

%% -----------------
%% Paths
%% -----------------

ispath([_,E],X,Y,P) :- P = [X|_], last(P,Y),
                       not((append(_,[A,B|_],P), not(member(E,[A,B])))).

path([V,E],X,Y) :- subset(V,P), ispath([V,E],X,Y,P).

iscylce([V,E],P) :- member(V,X), member(V,Y),
                    ispath([V,E],X,Y,P), member(E,[Y,X]).
