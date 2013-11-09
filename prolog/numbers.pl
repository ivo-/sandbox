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

%% Fibbonaci with linear complexity.
fib(0,1).
fib(X,Y) :- fib(Z,X), Y is Z + X.
