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

%% Check if there is possible unification between two lists.
%% ?- [[x,1],3,4|[7,9]] = [A,B|[4,C|Q]].

%% -----------------
%% Append and member
%% -----------------

member(list,element).
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

%% member(L,X) :- append(_,[X|_],L).      % member with append.
conseq(L,X,Y) :- append(_,[X,Y|_],L).     % X and Y are consequent.
sorted(L) :- not((conseq(L,X,Y), X > Y)). % L is soreted list.
last(L,X) :- append(_,[X],L).             % X is last element in L.

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
sort(L,Z) :- perm(L,Z), sorted(Z).

%% subset(L,Z) - Z is subset of L
subset(L,Z) :- perm(L,Z), sublist(L,Z).

%% subseq(L,Z) - Z is subsequence of L
subseq([],[]).
subseq([X|L],Z) :- :- subseq(L,Z).
subseq([X|L],[X|Z]) :- subseq(L,Z).

%% -----------------
%% Sorts
%% -----------------
