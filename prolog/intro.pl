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
%% ----------------
%% Program
%% ----------------
%%

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


%% ----------------
%% Queries
%% ----------------
%%
%% ?- likes(sam, X).               % Makes query.
%% X = dahl ;
%% X = kurma ;                     % ; or space  - requests next answer
%% X = tandoori .                  % . or return - terminates request
%%                                 %
%%                                 % If Prolog cannot find more answers, it writes
%%                                 % false.
%%                                 % Prolog can also answer with error message.
%%                                 %
