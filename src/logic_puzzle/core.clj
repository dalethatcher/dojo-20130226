(ns logic-puzzle.core
  (:refer-clojure :exclude [==])
  (:use clojure.core.logic))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn distincto [l]
  (conde
    [(== l ())]
    [(fresh [a] (== l [a]))]
    [(fresh [a ad dd]
            (== l (llist a ad dd))
            (!= a ad)
            (distincto (llist a dd))
            (distincto (llist ad dd)))]))



(defn clue-5o [thingy]
  (fresh [name pm time grade]
         (== thingy [name pm time grade])
         ;; Edith didn't get the D
         (== name :Edith)
         (!= grade :D)))

(defn clue-2o [thingy]
  (fresh [name pm time grade]
         (== thingy [name pm time grade])
         ;; The student North pres spoke 6 mins more than Sheryl
         (conde
          [
           (== name :Sheryl)
           (conde
            [ (== time :6)]
            [ (== time :8)]
            )

           (!= pm :North)]

          [(conde
            [(== time :12)]
            [(== time :14)]
            )
           (== pm :North)]
          )
         ))

(defn clue-8o [thingy]
  (fresh [name pm time grade]
         (== thingy [name pm time grade])
         ;; Edith didn't get the B+
         (== name :Edith)
         (!= grade :B+)))

(defn clue-4o [thingy]
  (fresh [name pm time grade]
         (== thingy [name pm time grade])
         (conde [(== name :Edith) (!= grade :C-)]
                [(== pm :Wilson) (!= grade :C-)])
         ))

(defn validthingyo [thingy]
  (fresh [name pm time grade]
         (== thingy [name pm time grade])
         (membero name '(:Colleen :Edith :Krista :Roberta :Sheryl))
         (membero pm '(:Blair :Eden :Grey :North :Wilson))
         (membero time '(6 8 10 12 14))
         (membero grade '(:A- :B+ :C+ :C- :D))

         (clue-5o thingy)
         (clue-8o thingy)
         (clue-4o thingy)
         ))

(defn usedonceo [q]
  (distincto (flatten q)))

(defn solveo [q]
  (fresh [a b c d e]
         (validthingyo a)
         (validthingyo b)
         (validthingyo c)
         (validthingyo d)
         (validthingyo e)
         (== q [a b c d e])
         (usedonceo q)
         (distincto [a b c d e])))

