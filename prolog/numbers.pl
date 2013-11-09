%% =================
%% Numbers in Prolog
%% =================
%%
%% NOTE: We will play mostly with generators.
%%

%% ----------------
%% Int generator
%% ----------------

%% The most basic and powerful numbers generator. It will generate endless
%% sequence of integers.
%%
%%                  int(X).
%%                  /    \
%%              (1)/      \(2)
%%                /        \
%%              [*]    int(Y), X is Y + 1
%%             X = 0     /          \
%%                   (1)/            \(2)
%%                     /              \
%%                   y = 0      int(Y), X1 is Y + 1, X is X1 + 1
%%                   X = 1          /           \
%%                              (1)/             \(2)
%%                                /               \
%%                              y  = 0       ..............
%%                              X1 = 1       ..............
%%                              X  = 2       ..............
%%
int(0).
int(X) :- int(Y), X is Y + 1.

%% ----------------
%% Fibonacci
%% ----------------

%% fibN(N,X) - Nth fibonacci number.
fibN(0,0).
fibN(1,1).
fibN(N,X) :- N > 1,
             P  is N-1, fibN(P ,X1),
             PP is N-2, fibN(PP,X2),
             X  is X1 + X2.

%% Fibbonaci with exponential complexity.
fibe(X) :- int(N), fibN(N,X).

%% Fibbonaci with better complexity.
fib(0,1).
fib(X,Y) :- fib(Z,X), Y is Z + X.

%% ----------------
%% Intermediate
%% ----------------

between(X,Y,X) :- X =< Y.
between(X,Y,C) :- X < Y, X1 is X+1, between(X1,Y,C).

%% ----------------
%% Advanced
%% ----------------

%% Generate pair of natural numbers.
%%
%% Here is one wrong solution. In this case second generator will iterate
%% endlessly and first will stay at 0.
%%
%%   pair(X,Y) :- int(X),int(Y).
%%
%% Here is the correct solution. It uses only one generator to produce the sum
%% ot the pair and then transforms this sum into X and Y.
pair(X,Y) :- int(S),between(0,S,X),Y is S-X.
