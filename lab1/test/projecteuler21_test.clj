(ns projecteuler21-test
  (:require [projecteuler21 :refer [solution-tail-recur
                                    solution-recur
                                    solution-modules
                                    solution-with-map
                                    solution-with-for
                                    solution-with-loop
                                    solution-lazy]]
            [clojure.test :refer [deftest is run-all-tests]]))

(def max-value 10000)

(def answer 31626)

(deftest test-solution-tail-recur
  (is (=
       (solution-tail-recur max-value)
       answer)))

(deftest test-solution-recur
  (is (=
       (solution-recur max-value)
       answer)))

(deftest test-solution-modules
  (is (=
       (solution-modules max-value)
       answer)))

(deftest test-solution-map
  (is (=
       (solution-with-map max-value)
       answer)))

(deftest test-solution-for
  (is (=
       (solution-with-for max-value)
       answer)))

(deftest test-solution-loop
  (is (=
       (solution-with-loop max-value)
       answer)))

(deftest test-solution-lazy
  (is (=
       (solution-lazy max-value)
       answer)))

(comment
  (run-all-tests #"solution"))