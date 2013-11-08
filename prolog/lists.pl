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

%% member(list,element).
member([X|_],X).
member([K|Q],X) :- member(Q,X).

%% append(list,list,list).
append([],M,M).
append([X|Q],M,[X|T]) :- append(Q,M,T).
