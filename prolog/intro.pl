%% ================
%% Intro to Prolog.
%% ================
%%
%% ----------------
%% Workflow
%% ----------------
%%
%% Load program:
%%    swipl -s ./dir/program.pl
%%
%% Edit program.pl:
%%    emacsclient -t program.pl
%%
%% Realod all loaded and modified files:
%%    ?- make.
%%
%% Load file named 'first.pl':
%%    [’first’].
%%
%% ----------------
%% Queries
%% ----------------
%%
%% ?- likes(sam, X).               % Makes query. We can only query in repl.
%% X = dahl ;                      %
%% X = kurma ;                     % ; or space  - requests next answer
%% X = tandoori .                  % . or return - terminates request
%%                                 %
%%                                 % If Prolog cannot find more answers, it writes
%%                                 % false.Prolog can also answer with error message.
%%
%%
%% ----------------
%% Program[1]
%% ----------------
%%

%% A group of facts describing directed graph. Identifier "edge" is predicate
%% and idntifiers a,b,c,d,e are abstract constant values representing name of
%% the graph nodes.
edge(a,b).
edge(a,e).
edge(b,d).
edge(b,c).
edge(c,a).
edge(e,b).

%% Defies a new predicate tedge. The left side of the rule looks just like fact,
%% but parameters are capitalized indicating they are variables, not constants.
%% The right side of the fact consists of two terms separated by a comma which
%% is read as "AND". Rule is satisfied only if two terms hold.
tedge(Node1,Node2) :-
    edge(Node1,SomeNode),
    edge(SomeNode,Node2).

%% Query our rule and see if constants "a" and "b" satisfy it.
tedge(a,b).

%% "," interprets as logical "AND".
edge(a, b), edge(a, a), tedge(a,b).

%% ----------------
%% Program[2]
%% ----------------

%% likes(sam,Food) :-
%%     indian(Food),
%%     mild(Food).
%% likes(sam,Food) :-
%%     chinese(Food).
%% likes(sam,Food) :-
%%     italian(Food).
%% likes(sam,chips).

%% indian(curry).
%% indian(dahl).
%% indian(tandoori).
%% indian(kurma).

%% mild(dahl).
%% mild(tandoori).
%% mild(kurma).

%% chinese(chow_mein).
%% chinese(chop_suey).
%% chinese(sweet_and_sour).

%% italian(pizza).
%% italian(spaghetti).
