%% ================
%% Lists in Prolog.
%% ================
%%
%% ----------------
%% Notations
%% ----------------
%%
%% []
%% [a,b,c]
%% [a|[b,c,d]]
%% [a,b,c|[d]]
%% [a,b,c,d|[]]
%% [a|[b|[c|[d]]]]
%%

%% ----------------
%% Unification
%% ----------------

%% Check if there is possible unification between two lists.
%% ?- [[x,1],3,4|[7,9]] = [A,B|[4,C|Q]].

%% [X|Y] unifies with [a,b,c] with the unifier {X = a, Y = [b,c]}.
%% [X|Y] unifies with [a,b,c,d] with the unifier {X = a, Y = [b,c,d]}.
%% [X|Y] unifies with [a] with the unifier {X = a, Y = []}.
%% [X|Y] does not unify with [].

%% -----------------
%% Append and member
%% -----------------

%% member(list,element).
member([X|_],X).
member([_|Q],X) :- member(Q,X).

%%
%%      EXECUTION TREE
%%      --------------
%%
%%          member([1,2,3],X).
%%           /             \        _ = 1
%%       (1)/               \ (2)   Q = [2,3]
%%         /                 \
%%       [*]            member([2,3], X).
%%     X = 1              /       \        _ = 2
%%     _ = [2,3]      (1)/         \ (2)   Q = [3]
%%                      /           \
%%                    [*]       member([3],X).
%%                   X = 2         /      \       _ = 3
%%                   _ = [3]   (1)/        \ (2)  Q = []
%%                               /          \
%%                             [*]       member([],X).
%%                            X = 3        /      \
%%                            _ = []     false   false
%%
%% We can very clear distinguish *unification* and *resolution* phases.
%% Backtracking is easy to track. nExecution engine will possibly try all the
%% solution until resolution fails in all the cases in member([],X) node.
%%

%% append(list,list,list).
append([],M,M).
append([X|Q],M,[X|T]) :- append(Q,M,T).

%%
%%      Call stack for matching variable value
%%      --------------------------------------
%%
%% append([1,3],[3,2,4,5],Y).
%%    Call: (6) append([1, 3], [3, 2, 4, 5], _G174) ?
%%    Call: (7) append([3], [3, 2, 4, 5], _G262) ?
%%    Call: (8) append([], [3, 2, 4, 5], _G265) ?
%%    Exit: (8) append([], [3, 2, 4, 5], [3, 2, 4, 5]) ?
%%    Exit: (7) append([3], [3, 2, 4, 5], [3, 3, 2, 4, 5]) ?
%%    Exit: (6) append([1, 3], [3, 2, 4, 5], [1, 3, 3, 2, 4, 5]) ?
%% Y = [1, 3, 3, 2, 4, 5].
%%
%% Note that in each call new variables are created rather than passing
%% references from call to call. We can clearly see how recursion tree expands
%% and contracts to generate values for variables in first query call using
%% matched values from inner calls.
%%

%% member(L,X) :- append(_,[X|_],L).      % Member with append.
conseq(L,X,Y) :- append(_,[X,Y|_],L).     % X and Y are consequent.
sorted(L) :- not((conseq(L,X,Y), X > Y)). % L is soreted list.
last(L,X) :- append(_,[X],L).             % X is last element in L.

reverse([X],[X]).
reverse([X|L],Z) :- reverse(L,Q), append(Q,[X],Z).

%% -----------------
%% Sublists
%% -----------------

%% bgin(L,A) - L begins with A
begin(_,[]).
begin([X|Q],[X|T]) :- begin(Q,T).

%% sublist(L,A) - A is sublist of L
sublist(L,A) :- begin(L,A).
sublist([_,K],A) :- sublist(K,A).

%% Alternative sublist using append.
%%
%%      sublist(L,A) :- append(_,K,L),append(A,_,K).
%%
%% Look at the following example where sublist will never end its execution. In
%% this case append(_,A,K) will generate endlessly substitutions for K and
%% append will fail for all of them. But in previous example append(_,K,L) is
%% not endless since K may be generated finite number of times.
%%
%%      sublist(L,A) :- append(_,A,K),append(K,_,L).
%%

%% in(L,X,Z) - Z contains all elements from Z + X
in(L,X,Z) :- append(L1,L2,L), append(L1,[X|L2],Z).

%% perm(L,Z) - Z is permutation of L
perm([X|L],Z) :- perm(L,Q), in(Q,X,Z).
perm([],[]).

%% sort(L,Z) - Z is sorted L
%% sort(L,Z) :- perm(L,Z), sorted(Z).

%% subset(L,Z) - Z is subset of L
subset(L,Z) :- perm(L,P), sublist(P,Z).

%% subseq(L,Z) - Z is subsequence of L
subseq([],[]).
subseq([_|L],Z) :- subseq(L,Z).
subseq([X|L],[X|Z]) :- subseq(L,Z).

%% -----------------
%% Simple sort
%% -----------------

%% min(L,X) - X is the smallest element of L
min(L,X) :- member(L,X), not((member(L,Y), Y < X)).

%% Min with linear complexity.
%%
%% min([X],X).
%% min([A,B|L],X) :- A =< B, min([A|L],X).
%% min([A,B|L],X) :- A > B, min([B|L],X).

%% out(L,X,Z) - Z is L without X
out(L,X,Z) :- append(L1,[X|L2],L), append(L1,L2,Z).

%% isort(L,Z) - Z is the sorted X
isort(L,[X|Z]) :- min(L,X), out(L,X,M), isort(M,Z).
isort([],[]).

%% -----------------
%% Quick sort
%% -----------------

%% div(). based on realized predicates. It is like direct port from mathematical
%% definition and is quite inefficient.
comb(P,Q,L) :- append(P,Q,R), perm(L,R).
div(L,Pivot,Left,Right) :- subseq(L,Left),
                           subseq(L,Right),
                           comb(Left,Right,L),
                           not((member(Left,X), X > Pivot)),
                           not((member(Right,X), X =< Pivot)).

%% div([],_,[],[]).
%% div([X|L],Pivot,[X|Left],Right) :- X =< Pivot, div(L,Pivot,Left,Right).
%% div([X|L],Pivot,Left,[X|Right]) :- X > Pivot, div(L,Pivot,Left,Right).

quick([],[]).
quick([A],[A]).
quick([Pivot|L],S) :- out(L, Pivot, E),
                      div(E,X,Left,Right),
                      quick(Left, SLeft),
                      quick(Right, SRight),
                      append(SLeft, [X|SRight], S).

%% -----------------
%% Merge
%% -----------------
