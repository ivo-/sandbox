%% Rock-paper-scissors-lizard-Spock

%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Solution
%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

wins(scissors, cut, paper).
wins(paper, covers, rock).
wins(rock, crushes, lizard).
wins(lizard, poisons, spock).
wins(spock, smashes, scissors).
wins(scissors, decapitate, lizard).
wins(lizard, eats, paper).
wins(paper, disproves, spock).
wins(spock, vaporizes, rock).
wins(rock, crushes, scissors).

%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Tests
%% %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

?- wins(rock, A, scissors), writeln(A).
?- wins(A, B, spock), writeln(A), writeln(B).
?- wins(spock, A, B), writeln(A), writeln(B).
