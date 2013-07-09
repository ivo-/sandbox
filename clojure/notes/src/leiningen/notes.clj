(ns leiningen.notes
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

;;; `lein notes`

(defn project-files [project-root]
  (file-seq (io/file project-root)
            (filter #(.endsWith (.getAbsolutePath %) ".clj"))))

(defn process-file [project-root files]
  (doseq [file files]
    (let [lines-with-numbers (map-indexed vector (line-seq (io/reader file)))]
      (doseq [[lnum line] lines-with-numbers]
        (when (re-find #";+\s*(TODO|FIXME|REFACTOR)" line)
          (print (format "%s: %d: %s"
                         (relative-path project-root
                                        (.getAbsolutePath file))
                         (inc lnum)
                         line)))))))

(defn relative-path [dir filename]
  (str/replace-first filename (str dir java.io.File/separator) ""))

(defn notes
  "I don't do a lot."
  [project & args]
  (let [root (:root project)]
    (process-file root (project-files root))))
