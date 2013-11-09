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
%% ?- likes(sam, X).               % Makes query. We can only query in the repl.
%% X = dahl ;                      %
%% X = kurma ;                     % ; or space  - requests next answer
%% X = tandoori .                  % . or return - terminates request
%%                                 %
%%                                 % If Prolog cannot find more answers, it writes
%%                                 % false. Prolog can also answer with error message.
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

%% Query our rule and see if tuple of provided constants satisfy it. If we use
%% constants that are not in the facts it will just return false not error.
?- tedge(a,b). %= true
?- tedge(a,d). %= true
%% ?- tedge(a,e). %= false
%% ?- tedge(a,k). %= false

%% "," interprets as logical "AND".
?- edge(a,b), edge(a,e), tedge(a,b). %= ture

%% Wen we provide capitalized identifier it will denote a variable. Variables
%% are used in queries to get the system to find all values which can be
%% substituted to make the resulting predicate true.
%% ?- tedge(a,X). %= X = d ;
%%                %  X = c ;
%%                %  X = b.
%% ?- tedge(X,Y). %= X = a, Y = d.

%% If a predicate is defined only by a group of facts, this doesn't mean it can
%% be the head of a non-fact rule. For example we can make our graph a lot more
%% complex by adding following rule.
%%
%%  edge(X,Y) :- tedge(X,Y).
%%
%% But this will cause infinite recursion for tedge predicate queries, because
%% it will call itself recursively for edge.
%%

%% This is a recursive rule to define path in graph. First thing to note is when
%% using two rules with the same head to define a predicate, they are in logical
%% OR relation.
%%
%% This solution will give results endlessly if there are cycles in the graph.
%%
path(Node1, Node1).             % We add a fact to predicate definition to allow
                                % paths of length 0.
path(Node1, Node2) :-
    edge(Node1, Node2).
path(Node1, Node2) :-
    edge(Node1, SomeNode),
    path(SomeNode, Node2).

%% ----------------
%% Build-in predicates
%% ----------------

%% Unification:
%% ?- [X|[2,3]] = [1,2,3].

?- 9 >  4. %= true
?- 9 >= 4. %= true
?- 4 <  9. %= true
?- 4 =< 9. %= true
?- 4 == 4. %= true

%% Arithmetic:
?- X is 3.       % Set or check X's value.
?- 3*2 =:= 3+3.  % Evaluate and compare results.

?- X is 3  +  2. %= 5
?- X is 3  -  2. %= 1
?- X is 3  *  2. %= 6
?- X is 3  /  2. %= 1.5
?- X is 3 **  2. %= 9
?- X is 3 //  2. %= 1
?- X is 3 mod 2. %= 1

%% Debug:
%% ?- writeln(K)
%% ?- writeln('|')

%% All values should be generated outside of not!
?- not((1 < 3, 5 > 2)).

%% ----------------
%% Program[2]
%% ----------------

%% Rock-paper-scissors
%%
wins(paper,rock).
wins(rock,scissors).
wins(scissors,paper).
%%
%% ?- wins(X,Y).

%% ----------------
%% Program[3]
%% ----------------

likes(sam,Food) :-
    indian(Food),
    mild(Food).
likes(sam,Food) :-
    chinese(Food).
likes(sam,Food) :-
    italian(Food).
likes(sam,chips).

indian(curry).
indian(dahl).
indian(tandoori).
indian(kurma).

mild(dahl).
mild(tandoori).
mild(kurma).

chinese(chow_mein).
chinese(chop_suey).
chinese(sweet_and_sour).

italian(pizza).
italian(spaghetti).

?- likes(sam,X).
