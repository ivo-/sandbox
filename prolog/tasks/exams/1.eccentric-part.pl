%% A list of lists is normal if it is longer than any of his elements. A list of
%% lists is eccentric if it is normal, but none of its elements is normal.
%%
%% Define Prolog predicate q(L) that checks if list L has eccentric
%% part (sublist).

%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Solution
%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

append([],Z,Z).
append([X|L],Z,[X|R]) :- append(L,Z,R).

member(L,X) :- append(_,[X|_],L).
sublist(L,Z) :- append(_,K,L), append(Z,_,K).

longer([_|_],[]).
longer([_|L],[_|Z]) :- longer(L,Z).

normal(L) :- not((member(L,X), not(longer(L,X)))).
eccentric(L) :- normal(L), not((member(L,X),normal(X))).

q(L) :- sublist(L,Z), longer(Z,[]), eccentric(Z).
qq(L,Z) :- sublist(L,Z), longer(Z,[]), eccentric(Z).

%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Tests
%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

?- normal([[1],[2,3],[4,5,6],[7,8,9,10],[0]]).

?- eccentric([[[1]],
              [[2,2],[3]],
              [[4,4,4],[5],[6]],
              [[7,7,7,7],[8],[9],[10]],
              [[0]]]).

?- q([[[1],[1],[1]],
      [[2,2],[3]],
      [[4,4,4],[5],[6]],
      [[7,7,7],[8],[9]],
      [[0,0],[0]]]).
