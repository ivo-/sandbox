(ns exploring.macros "
  In short, when a language lacks proper means of abstraction, the result is
  boilerplate and repetition, both signs of fundamental weaknesses in that
  language. Macros are powerful because they give you a way to define entirely
  new levels of abstraction within the language itself. Macros are the ultimate
  tool for eliminating boilerplate and growing a programming language up to meet
  your needs.

  Macros allow you to control the Clojure compiler. They can be used to
  completely transform the syntax of the language.

  The key to understanding macros is to keep clear in your mind the distinction
  between *runtime* and *compile time*. Clojure source code is read by the
  Clojure reader, which produces Clojure data structures from textual Clojure
  code. This property, where a language’s code is represented using its own data
  structures, is called homoiconicity, and is critical in enabling macros.

  Normally these data structures are then evaluated. Each type of data has
  particular rules that govern its evaluation:
  • Many literals evaluate to themselves (e.g., integers, strings, keywords, vectors).
  • Symbols resolve to a value in a var from some namespace.
  • Lists denote calls, either to functions, special forms, or macros.

  It is between the read and evaluation steps where compilation happens and
  macros occupy a privileged status compared to functions.

  Macros are called by the compiler with their unevaluated data structures as
  arguments and must return a Clojure data structure that can itself be
  evaluated.

  If foo is a function, following code compiles down to runtime invocation of
  the foo function with arguments values hold by variables a and b, but if foo
  is a macro it will be called during compilation with symbols 'a and 'b and
  will produce code that will be called and runtime.

  (foo a b)

  Macros are not limited to a subset of the language. In any case, bar must
  return to the compiler a Clojure data structure that will be used in its
  place. This is recursive, as a macro can return Clojure data structure that
  includes other macro calls as well; this continues for each expression until
  it is no longer a macro call.

  ")

;;; Its implementation detail that macros are just a functions with
;;; {:macro ture} meta data. This tells compiler to treat their calls
;;; differently.
(meta #'or) ;= {:macro true, :ns ... }

;;; The process of replacing macro calls with the code they produce is called
;;; macroexpansion. All Clojure code is always compiled and macroexpansion is a
;;; critical and inseparable part of this process.

;;; The compilation process ensures that any macro calls are replaced wholesale
;;; with their expansions long before a program’s runtime; thus, macros are only
;;; ever evaluated at compile time.

;;; Macros can readily be used in conjunction with each other.
;;; Clojure macros are compiled at compile time. Syntax errors at compile time.
;;; Any attempt to use improper sytax will fail in the reader.

(require '[clojure.walk :as walk])
(require '[clojure.string :as str])

;;; (reverse-it (nltirp "foo")) -> (println "foo")
(defmacro reverse-it
  [form]
  (walk/postwalk #(if (symbol? %)
                    (symbol (str/reverse (name %)))
                    %)
                 form))
(reverse-it (nrp "foo"))


;;; Macro will not produce error for not defined symbols until it is used, in
;;; contrast with functions. Macros are executed at compile time and it
;;; impossible for Clojure to know if symbol is bound to value or not. The macro
;;; sees and returns only symbols, keywords and data structures. Whether those
;;; symbols are when code produced by the macro is executed is not for the macro
;;; to decide. This makes debugging tricky but we have some good tools for that.
#_(defn oops [arg] (frobnicate arg))       ;= #<CompilerException ..
#_(defmacro oops [arg] `(frobnicate ~arg)) ;= #'user/oops
#_(oops 123)                               ;= #<CompilerException ..

;;; Macroexpand family functions are the key tools used in testing and debugging
;;; macros. They takes a data structure(in debugging context, often quoted macro
;;; form) and calls Clojure compiler to handle it and return the code.
;;; Macroexpansion can happen many times as returned code contains macros.
;;;
;;; We can see that symbol returned from macros are namespaced.
;;;
;;; Does single macroexpand of the top level form. Often this is what we want
;;; since most of the time full expansion will be lengthy.
(macroexpand-1 '(reverse-it (nrp "foo"))) ;= (prn "foo")
;;;
;;; Macroexpands until top level form is not macro anymore.
(macroexpand '(doseq [x (range 10)] (prn 1))) ;= ...
;;; Neither macroexpand or macroexpand-1 expand nested forms. macroexpand-all is
;;; hack for this that works in most of the cases, but it only just tries to
;;; emulate job done by the compiler. It shouldn't expand quoted forms for
;;; example.
(clojure.walk/macroexpand-all '(cond a b c d))

;;; Wen using macros we often want to return list to represent future calls to
;;; other macros, functions or special forms, therefore we need tools to build
;;; these lists.
(defmacro puts [& args]
  (list* 'println args))

(defmacro while
  [test & body]
  (list 'loop [] (concat
                  (list 'when test)
                  body
                  '((recur)))))

(defmacro while
  [test & body]
  `(loop []                             ; syntax-quote
     (when ~test                        ; unquote
       ~@body                           ; splicing-unquote
       (recur))))

(macroexpand '(while true (print 1 2 3)))

;;; Syntax quote fully qualifies unqualified symbols with the current namespace.
;;; This is very important behavior when used in macros, ensuring that macro
;;; will not have context based behavior. It also allows unquoting.
`(print 1 2 clojure.core/map) ;= (clojure.core/print 1 2 clojure.core/map)
(let [a 10
      b [1 2 3 4]]
  `(print
    ~a  ;;= unquoting
    ~@b ;;= unquote-splicing
    )) ;= (clojure.core/print 10 1 2 3 4)
