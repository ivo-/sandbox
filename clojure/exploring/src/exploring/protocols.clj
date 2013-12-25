;; The Expression Problem refers to the problem of making a language extensible.
;; Software manipulates data types using operations. Sometimes, we want to add
;; new operations, and have them work on existing data types. Sometimes, we want
;; to add new data types, which will work with existing operations.
;;
;; Object-oriented languages make it easy to add new data types (classes), but
;; adding new operations(methods) require modifying a all the classes.
;;
;; Functional languages tend towards the opposite: it's easy to add new
;; operations (functions), but harder to adapt the operation to various types.

(ns exploring.protocols
  "Clojure's answer to JAVA's interfaces. They define protocol name and methods
   without specifying the implementation. They later can be implemented by new
   and already existing types(not possible with interfaces).")

;;; ============================================================================
;;; Protocols

;;; The first argument is privileged and protocol implementation will be chosen
;;; based on its type. Hence, protocols provide single type-based dispatch. This
;;; is the pragmatic choice because this is what most hosts support and
;;; optimize. This also is what we want in most of the cases.
;;;
;;; Protocols names are written in CamelCase and compiled down to JAVA
;;; interfaces.
;;;
;;; Protocol methods are just functions like any other, but we cannot use
;;; destructing or rest arguments in methods specifications. This is due to the
;;; fact that they compile down to JAVA interfaces.
;;;
;;; TODO: Multi-aritiy protocol.
(defprotocol iProtocolName
  "Protocol documentation."
  (method-1 [slef] "Method documentation.")
  (method-2 [self] [slef arg] "Multi-arity method documentation."))

;;; Implement a protocol for one or many existing types. You are not required to
;;; implement all of the methods, but when called not implemented methods will
;;; throw an exception.
(extend-protocol iProtocolName
  clojure.lang.PersistentVector
  (method-1 [self]
    (prn self)
    ;; (recur nil) -> you cannot recur with other head
    )
  (method 2 [self arg]
    (prn self arg))
  clojure.lang.PersistentArrayMap
  ;; ....
  )

;;; Implement one or many protocols for existing type.
(extend-type nil
  iProtocolName
  (method-1 [self]
    (prn "Yeah ... we can extend protocol on nil."))
  ;; ....
  )

(extends? iProtocolName clojure.lang.PersistentVector)
(satisfies? iProtocolName clojure.lang.PersistentVector) ;; TODO: WTF?

;;; ============================================================================
;;; Types and Records

;;; Clojure types/records just define Java classes with some with public final
;;; fields, listed in the vector. Accessing and updating their values are faster
;;; than the same operations with regular Clojure maps, for example.
;;;
;;; New types/records are not defined in current namespace, but in package
;;; corresponding to the current namespace and it is implicitly imported in it.
;;; Types are host classes not vars! You have to explicitly import defined
;;; types/records when using them in other namespace.
(defrecord Point [x y])
(deftype Point [x y])

(Point. 3 4)      ;; Create type.
(.x (Point. 3 4)) ;; Access values.

(Point/getBasis)            ;;= [x y] -> fileds defined by the constructor.
(map meta (Point/getBasis)) ;;= ({:tag String}...) -> original meta data.

;;; By default each filed is typed with regular java.lang.Object. You can hint
;;; the types using meta data.
(defrecord NamedPoint [^String name ^long x ^long y])

;;; Records are designed to model and represent application-level data. They
;;; provide in addition to types:
;;;   - value semantics
;;;   - associative abstraction implementation
;;;   - metadata support
;;;   - reader support(creating when reading)
(defrecord Record [field filed2])
(Record/create {:field 1 :field2 2})
(prn (Record. 1 2 {:meta 1} {:field3 3}))
;; #exploring.protocols.Record{:field 1, :filed2 2, :field3 3}

(= (Record. 1 2) (Record. 1 2)) ;;= true

(:field (Record. 1 2)) ;; get value using associative properties
(.field (Record. 1 2)) ;; get value using interop form

;;; We can change the associative values using maps functions. New values are
;;; kept in regular hash map and have corresponding lookup and update
;;; performance.
;;; TODO: Are records persistent?
(assoc (Record. 1 2) :field 0)  ;;
(assoc (Record. 1 2) :field2 3) ;;

;; Dissociating a declared field produces a return value that is a simple map,
;; not arecord.
(dissoc (Record. 1 2) :field)

;;; Defining factory functions is always a good idea when exposing public API.
;;; Constructors provided by Records are too low-level and inconvenient. All the
;;; validation logic and other work should be a part of this factory functions.
;;; They also make switching from maps to records very easy. You will introduce
;;; your own factory functions as a part of the work.
(->Record 1 2)          ;; Implicitly defined factory function.
(map->Record {:field  1 ;; Implicitly defined factory function.
              :field1 2})

;;; Starting with maps -> switching to records.
((Point. 3 4) :x) ;; Records are not IFn
;;; Maps and records can never be equal.

;;; Deftype types are intended to define low-level types usually when you build
;;; new language abstraction or data structure relaying on low-level stuff.
;;; The lowest type definition from Clojure. (defrecord) is just a macro over
;;; the deftype. They offer one thing that is unavoidable at low level - mutable
;;; fields.
(deftype Type [x y])
(->Type 1 2)      ;; Implicitly defined factory function.
(.x (->Type 1 2)) ;; Immutable field access.

;;; Declare field as mutable. They are always private(in contrast to immutable
;;; ones) and accessible only by methods bodies provided inline by the type
;;; definition.
(deftype MyType [^:volatile-mutable fld]
  "Reads and writes are atomic and must be executed in program
   order (thread safe)")
(deftype MyType [^:unsynchronized-mutable fld]
  "Reads and writes are open to race conditions.")

;;; Inline protocols implementations. The only difference from using extend is
;;; that here we can access fields names directly with their names which is a
;;; performance boost. Also methods calling will be as fast as calling interface
;;; methods in JAVA. It compiles to class that implements a protocol and
;;; functions often maps to a regular methods calls which JVM is especially good
;;; optimizing.
;;;
;;; This brings some limitations based on Java classes.
;;;
;;; Methods implementations cannot be changed in runtime. All already created
;;; objects will never get updates from reevaluating of the type.
;;;
;;; They are more static than normal implementations and are best kept as
;;; optimization step.
;;;
;;; This is the only way for Clojure type to implement JAVA interface.
;;; Inline implementations cannot refer to the type being defined, but it will
;;; compile!? (instance? MyType other)
(deftype MyType [x y]
  ProtocolName
  (method [self] (prn x y)))

;;; Creates an instance of anonymous type. Here we can access local bindings in
;;; closures. This is useful when creating adapters and one shot listeners.

(reify Protocol-or-Interface-or-Object
  (method1 [this x]
    (implementation))
  Another-Protocol-or-Interface
  (method2 [this x y]
    (implementation))
  (method3 [this x]
    (implementation)))

(defn listener
  "Creates an AWT/Swing `ActionListener` that delegates to the given function."
  [f]
  (reify
    java.awt.event.ActionListener
    (actionPerformed [this e]
      (f e))))
