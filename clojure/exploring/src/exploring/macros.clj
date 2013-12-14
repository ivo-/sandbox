<<<<<<< HEAD
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
=======
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

;;; Macros operate at compile time and are not a first class citizens of a
;;; running program. Macro has no access of a runtime information. So we cannot
;;; use them as first class functions that can be called at runtime. Once
;;; compiled, code can no longer access or use macros.
(defn fn-hello [x] (str "Hello, " x "!"))
(defmacro macro-hello [x] `(str "Hello, " ~x "!"))
fn-hello                ;= #<macros$fn_hello exploring.macros$fn_hello@6ba22acb>
macro-hello             ;= Can't take value of a macro: #'exploring...

;;; It's a fix but where all that macro power go?
(def fix-macro-hello #(macro-hello %))

;;; Macro is convenient and should be used on compilation, not in runtime where
;;; it can make your life difficult. Realize core functionality in functions and
;;; use macros for syntax sugar that uses this functions.

;;; Macros should be used only when you need your own language constructions aka
;;; when you need them and functions can't do the job. Always prefer function
;;; based solutions when you are not sure. Nothing complex should be inside
;;; macro. Clojure prevents us from using macros as functions to eliminate a
;;; large class of errors.

(defmacro unhygienic
  "Error when trying to le qualified name."
  [& body]
  `(let [x 10]
     ~@body))

(defmacro unhygienic
  "No errors by producing buggy code."
  [& body]
  `(let [~'x 10]
     ~@body))

(gensym)          ;= G__2894
(gensym "prefix") ;= prefix2900

(defmacro hygienic
  "No errors by producing buggy code."
  [& body]
  (let [sym (gensym)]
    `(let [~sym 10]
       (prn ~sym)
       ~@body)))

(defmacro auto-gensym
  "Auto gensym reader macro. It is valid only in single syntax quote. If we need
  to use gensym in multiple syntax-quote forms we should let it manually."
  [& body]
  `(let [x# 10
         y# 20]
     (+ (+ x# y#) ~@body)))

;;; Macro that deliberately lacks a name is called anaphoric(like this). This
;;; should be avoided. A much better solution is to explicitly require a user to
;;; set names for his bindings(like for macro).


;;; Double evaluation is when an argument of a macro appears twice or more in
;;; its expansion. It is code smell even worked around. You probably should
;;; extract some code into external function when this arises.
(defmacro spy [x]
  `(do (println "spied" '~x ~x) ~x))
(defmacro safe-spy [x]
  `(let [sym# ~x]
     (do (println "spied" '~x sym#) sym#)))


;;; Patterns:
;;;
;;;     1. Require that new local bindings are specified in vector.
;;;     2. Macros that define a vars:
;;;         - Start with 'def.
;;;         - Accepts name of the var as first argument.
;;;         - Define one var per macro form.
;;;     3. No complex behavior should be locked inside macros.

(defmacro implicit-local-bindings []
  ;; Contains map with keys->names-of-current-locals and values->unspecified.
  ;; Values shouldn't be relied upon.
  &env
  (keys &env)

  ;; List of the whole form that is currently macroexpanded. It's basically the
  ;; same form read by the reader. It has all the metadata defined by the user
  ;; (such as type hints).
  &form
  )

(defmacro simplify
  "Example how &env can be used to optimize code at compile time."
  [expr]
  (let [locals (set (keys &env))]
    (if (some locals (flatten expr))
      expr
      (do
        (println "Precomputing: " expr)
        (list `quote (eval expr))))))

;;; Getting macro implementation function is generally useful when debugging. We
;;; cannot use macroexpand when debugging &env, because there is no way to set
;;; value for it.
;;; (macro &form &env & args)
(#'simplify nil &env)
(@#'simplify nil &env)

(defmacro throw-correct-errors
  "Shows example how correct errors can be thrown at compile time. If we just
  throw an exception it will show only line in macro not line in code where
  macro is called."
  [& triples]
  (every? #(or (== 3 (count %))
               (throw (IllegalArgumentException.
                       (format "`%s` provided to `%s` on line %s has < 3 elements"
                               %
                               (first &form) ;; ... in case macro is renamed
                               ;; when imported from other ns
                               (-> &form meta :line)))))
          triples)
  ;; ...
  )

;;; Most of the macros doesn't preserve meta data attached to them(like type
;;; hints). But we can correct that. In most cases we can simply wrap our code
;;; with (with-meta) function, but in this example we cannot due to the fact
;;; that special forms like (let) cannot have meta data.
(defmacro OR
  ([] nil)
  ([x]
     (let [result (with-meta (gensym "res") (meta &form))]
       `(let [~result ~x]
          ~result))))

(defn macroexpand1-env [env form]
  (if-let [[x & xs] (and (seq? form) (seq form))]
    (if-let [v (and (symbol? x) (resolve x))]
      (if (-> v meta :macro)
        (apply @v form env xs)
        form)
      form)
    form))

(defmacro if-all-let [bindings then else]
  (reduce (fn [subform binding]
            `(if-let [~@binding] ~subform ~else))
          then (reverse (partition 2 bindings))))

(defn insert-second
  "Insert x as the second item in seq y."
  [x ys]
  (let [ys (ensure-seq ys)]
    `(~(first ys) ~x ~@(rest ys))))

;; Functional programming and data modeling already yield tremendous expressive
;; power and allow us to abstract away most repeating patterns in our code.
;; Macros are the final step, simplifying patterns of control flow and adding
;; syntactic sugar to minimize or eliminate code awkwardness.
>>>>>>> a4bd9a736efb823ac33baab205ad64976b1d7515
