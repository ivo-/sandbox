%% Да се дефинира на Пролог едноместен предикат p,който при преудовлетворяване
%% генерира всички тройки от естествени числа (a, b, c), чието произведение при
%% делениена 3 дава остатък 1 и уравнението a*x^2+b*x+c= 0 има два различни
%% реални корена.

%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Solution
%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

int(1).
int(X) :- int(Y), X is Y + 1.

pp([A,B,C]) :- P is A * B * C,
               D is B ** 2 - 4 * A * C,
               %% writeln(P), writeln(D),
               mod(P, 3) =:= 1,
               D > 0.

p([A,B,C]) :- int(S),
              between(0,S,A), S1 is S - A,
              between(0,S1,B),
              C is S1 - B,
              pp([A,B,C]).


%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Tests
%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

?- p([1,4,1]).
?- p([2,8,4]).
