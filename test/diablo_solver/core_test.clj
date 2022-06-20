(ns diablo-solver.core-test
  (:require [clojure.test :refer :all]
            [diablo-solver.core :refer :all]))

(deftest apply-transition-test
  (is (= {:state [true true true
                  true true true
                  true true true]
          :steps [8]}
         (apply-transition
          {:state [true true true
                   true true false
                   true false false]
           :steps []}
          8)))
  (is (= {:state [true false true
                  false false false
                  true false true]
          :steps [8 4]}
         (apply-transition
          {:state [true true true
                   true true true
                   true true true]
           :steps [8]}
          4)))
  (is (= {:state [true false true
                  false false false
                  true false true]
          :steps [8 4 0]}
         (apply-transition
          {:state [false true true
                   true false false
                   true false true]
           :steps [8 4]}
          0))))

(deftest solved?-test
  (is (not (solved? {:state [true true true true true false true false false]})))
  (is (solved? {:state [true true true true true true true true true]})))

(deftest solve-test
  (is (= :no-solution-found
         (solve [{:state [true true true
                          true true false
                          true false false]
                  :steps []}]
                0)))
  (is (= []
         (solve [true true true
                 true true true
                 true true true])))
  (is (= [8]
         (solve [true true true
                 true true false
                 true false false])))
  (is (= [0 8]
         (solve [false false true
                 false true false
                 true false false])))
  (is (= [0 4 8]
         (solve [false true true
                 true false true
                 true true false]))))
