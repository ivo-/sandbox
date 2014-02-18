(ns git.core)

;;; ==========================================================================
;;; Objects

(defn branch
  [name comm]
  {:name name
   :commit comm})

(defn commit
  [id parent msg]
  {:id id
   :parent parent
   :message msg})

(defn repository
  [name]
  (let [master (branch :master nil)]
    {:name name
     :head master
     :branches {:master master}
     :_last-commit-id 0}))

;;; ==========================================================================
;;; Actions

(defn do-commit
  [repo msg]
  (let [br (-> repo :head)
        comm (commit (:_last-commit-id repo) (:commit br) msg)
        head (assoc br :commit comm)]
    (-> repo
        (assoc :head head)
        (assoc-in [:branches (:name br)] head)
        (update-in [:_last-commit-id] inc))))

(defn do-log
  [repo n]
  (prn "-------------------------------------------")
  (prn "Log branch: " (-> repo :head :name ))
  (prn "-------------------------------------------")
  (->> (-> repo :head :commit)
       (iterate :parent)
       (take n)
       (remove nil?)
       (map #(dissoc % :parent))
       (map prn)
       (doall))
  (prn "-------------------------------------------")
  repo)

(defn do-checkout [repo name create?]
  (if-let [br (-> repo :branches name)]
    (do
      (prn "Switch to existing branch: " name)
      (assoc repo :head br))
    (let [br (branch name (-> repo :head :commit))]
      (prn "Switch to new branch: " name)
      (-> repo
          (assoc :head br)
          (assoc-in [:branches name] br)))))

(do

  (-> (repository "test")

      (do-commit "first commit")
      (do-commit "second commit")
      (do-commit "third commit")
      (do-checkout :dev true)
      (do-log 10)
      (do-commit "dev commit")
      (do-log 10)
      (do-checkout :master false)
      (do-log 10)
      )

)
