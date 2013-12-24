;;; TODO: edn-format.org
;;; TODO: clojure.edn api docs

(ns exploring.edn
  (require [clojure.edn :as edn]))

;;; Extensible Data Notation(EDN) is a subset of Clojure. It is a idiomatic JSON
;;; replacement for communication format between Clojure and ClojureScript. It
;;; is actually a very good JSON replacement for any case as it is by far more
;;; powerful and expressive format.
[1 2 3 4 "five" true]

;;; Clojure reader understands EDN. (pr) and (prn) print by default in format
;;; that can be read by the reader.
(read-string "{:a 1 :b 2}")
(pr-str {:a 1 :b 2})
(prn-str {:a 1 :b 2})

(defrecord Person [name age])
(let [ivo (->Person "Ivo" "21")]
  ;; (pr-str) provides us with default EDN tag for our defrecord. This lest the
  ;; EDN reader to know that this is not a standard Clojure type and it needs to
  ;; be handled differently.
  (pr-str ivo)                  ;= #exploring.edn.Person{:name "Ivo", :age "21"}

  ;; No reader function for tag exploring.edn.Person.
  (comment
    (edn/read-string (pr-str (->Person "Ivo" "21")))
    )

  ;; We can set instructions for the EDN reader how to handle new tags. It
  ;; matches the tag with a function and passes the deserialized map with
  ;; attributes and values to it.
  (edn/read-string {:readers {'exploring.edn.Person map->Person}}
                   (pr-str ivo)))

;;; (edn/read-string) is the most common function for working with EDN.
;;; (clojure.core/read-siring) will also do the job in some cases since
;;; Clojure's reader can read EDN, but clojure.edn functions support fully the
;;; standard and it is safer to use. Clojure.core's read-string will just read
;;; the string in *ns* and possibly eval the code.
(edn/read-string (prn-str {:a 1 :b 2}))
(edn/read-string {:readers {'tag (fn [val] result)
                            ;; Default reader for any tags that don't math any
                            ;; particular reader.
                            :default (fn [val] result)}}
                 (prn-str {:a 1 :b 2}))

;;; EDN doesn't support eval reader (#=).
(read-string "#=(+ 1 3)")
(edn/read-string "#=(+ 1 3)")
