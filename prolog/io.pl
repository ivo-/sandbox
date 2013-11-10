%% ================
%% I/O in Prolog
%% ================
%%
%% ----------------
%% Build-in
%% ----------------

%% Matches the next character and moves a file pointer. Each time it unifies a
%% hidden file pointer is moved along. When end of the file is reached it will
%% be unified with -1.
%% get0/2
%% get0(In, C)
%%   In - variable - internal designator of the file
%%   C  - variable - next character's ASCII code
%%
%% open/4
%% open(File, read, In,[eof_action(eof_code)])
%%
%%   File - variable - filename
%%   read - constant - how file will be used
%%   In   - variable - internal designator of the file
%%
%%   eof_action(eof_code) list of options.
%%
%% close/1
%% close(In)
%%
%% :- consult(filename). %% load filename.pl for using current program
%%
%% ----------------
%% Custom
%% ----------------

member([X],X).
member([_|Q],X) :- member(Q,X).

legal(C) :- C >= 32.
legal(C) :- C =:= 10.
eof_char(C) :- C is -1.

file(File,Contents) :-
    open(File,read,In,[eof_action(eof_code)]),
    readAll(In,Contents),
    close(In).

readValid(_,[],C)         :- eof_char(C).
readValid(In,Chars,C)     :- not(legal(C)), get0(In,N), readValid(In,Chars,N).
readValid(In,[C|Chars],C) :- legal(C), get0(In,N), readValid(In,Chars,N).

readAll(In,Contents)    :- get0(In,C),readValid(In,Contents,C).
