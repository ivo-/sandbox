;;; ============================================================================
;;; Component-Entity-System(CES)
;;; ============================================================================

(ns exploring.ces)

;;; Entities
;;; ----------------------------------------------------------------------------
;;; Just unique IDs used to link all the state related to something. The actual
;;; state is held in components.

:entity

;;; Components
;;; ----------------------------------------------------------------------------
;;; "Little single purpose bits of state." Instead of having a big object with a
;;; lot of state we divide it in multiple components each holding related state.
;;; You get composition for free with no overriding or deep hierarchies.

(component position [x y]
           :x x
           :y y)

(component life [value regen]
           :value value
           :regen regen)

(position 10 20) ;; It just a function returning map.

;;; Systems
;;; ----------------------------------------------------------------------------
;;; Systems are single purpose functions that take in a set of entities which
;;; have a specific component (or set of components) and update them.

;; Then all we need to do is to iterate over all the reandables and call render
;; on rendering phase.
(component renderable [func] ;; NOTE: *able names convention
           :fn func)

;;; All together
;;; ----------------------------------------------------------------------------
;;; It is just data and simple functions. We are turning our whole game into a
;;; single data structure.

{:player [(position 10 20)
          (life 100 12)
          (renderable render-player)]}
