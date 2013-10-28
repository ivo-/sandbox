%% Intro to Prolog.
%%
%%

?- [swi('demo/likes')].

?- likes(sam, X).               % Makes query.
X = dahl ;
X = kurma ;                     % ; or space  - requests next answer
X = tandoori .                  % . or return - terminates request
                                %
                                % If Prolog cannot find more answers, it writes
                                % false.
                                % Prolog can also answer with error message.
                                %
