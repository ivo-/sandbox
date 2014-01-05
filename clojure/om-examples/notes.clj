;;; TODO: Gist front END app with core.async and om.
;;; TODO: DOM events core.async lib for producing channels.
;;; TODO: user in profiles.clj and dev in project

;;; ------------------------------------------------------------------
;;; Concepts(over React)

;;; 1. Using only immutable data. (props and state)
;;; 2. Every update starts from the root. This is cheap becase we do
;;;   shouldComponentUpdate based on reference equality checks.
;;; 3. We are keeping one state for the whole application and every component
;;;   may have its own local state for its transient data, not required for the
;;;   whole application.
;;; 3. All re-render operations are scheduled on the next requestAnimationFrame,
;;;   when the actual app state will be passed to React and synced with the UI.
;;; 4. Application state is guaranteed to be consistent with the UI only on
;;;   render phase, event handlers must ask for up to date views of the
;;;   application state.
;;; 5. Owner is for local state and for accessing refs.

;;; ------------------------------------------------------------------
;;; API

;;; Creating component(Pure.)
(defn component [app node]
  ;; TODO: Cursor and path information?

  app  ;; app state wrapped in a root cursor
  node ;; owning pure node(created using React.CreateClass)

  ;; Anonymous type instance that implements React's
  ;; component lify cycle protocols.
  (reify
    om/IWillMount
    (will-mount [_]
      )
    om/IRender
    (render [_]
      )))

;;; Sugar for creating just IRender implementing components.
(defn [app node]
  (om/component
    "(render) code"))

;;; Builds an om component(Pure.).
;;;     - f      -> returns IRender instance and takes (cursor node opts?)
;;;     - cursor -> app cursor
;;;     - {:key       "keyword (key in the cursor) used as react's key for
;;;                    rendering sequential things"
;;;        :react-key "explicit react-key"
;;;        :fn        "mapping function for relative data before passing it to f"
;;;        :opts      "options to pass to the component"}
;;;
(om.core/build f cursor opts?)

;; Build a sequence of components. f is the component constructor function, xs a
;; sequence of cursors, and m a map of options the same as provided to
;; om.core/build.
(build-all f xs m?)

;;; Transition the application state. It should not rely on information not
;;; obtained by om.core/read, om.core/get-state. With korks we can specify the
;;; path.
(om.core/transact! cursor korks? f)
(om.core/update! cursor  f) ;; the same as om.core/transact! but no korks

;; Used to request a consistent snapshot about a particular peace of data in the
;; application state outside of the rendering phase.
(om.core/read cursor korks? f)

;;; Get react refs.
(om.core/get-node owner name)

;;; Analogue to React's setState. Here we have to set korks because component's
;;; state is required to be object.
(om.core/set-state! owner korks v)

;;; Get the owning pure node's state.
(om.core/set-state! owner korks?)

;;; Event handlers constructor. f will be invoked with event object, cursor and
;;; any additional arguments passed to bind.
;;;
;;; (f e cursor a?)
(om.core/bind f cursor a?)
