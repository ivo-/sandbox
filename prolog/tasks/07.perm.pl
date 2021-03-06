%% Permutations

%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Dependencies
%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%% ?- ['03.append'].

%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Solution
%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%% in(L,X,W) - W contains all elements from L + X
in(L,X,W) :- append(L1,L2,L), append(L1,[X|L2],W).

%% perm(L,P) - P is permutation of L
perm([],[]).
perm([X|L],P) :- perm(L,Q), in(Q,X,P).

%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Tests
%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

?- in([], 5, [5]).
?- in([1,2], 5, [1,2,5]).
?- in([1,2], 5, [1,5,2]).
?- in([1,2], 5, [5,1,2]).
?- not(in([2,1], 5, [5,1,2])).

?- perm([1,2,3,4], [1,2,3,4]).
?- perm([1,2,3,4], [4,3,2,1]).
?- perm([1,2,3,4], [1,3,2,4]).
