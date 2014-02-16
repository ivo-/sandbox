%% Fibonacci

%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Solution
%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%% fibN(N,X) - X is Nth Fibonacci number
fibN(0,0).
fibN(1,1).
fibN(N,X) :- P is N-1, fibN(P, PX),
             PP is N-2, fibN(PP, PPX),
             X is PX + PPX.

%% fib(X,Y) - X and Y are 2 sequential Fibonacci numbers
fib(0,1).
fib(X,Y) :- fib(Z,X), Y is Z + X.

%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Tests
%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

?- fibN(1,1).
?- fibN(2,1).
?- fibN(3,2).
?- fibN(7,13).
?- fibN(9,34).
?- fibN(9,X), writeln(X).

?- fib(0,1).
?- fib(1,1).
?- fib(1,2).
?- fib(2,3).
?- fib(3,5).
?- fib(34,X), writeln(X).
