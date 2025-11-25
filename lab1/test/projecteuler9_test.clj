(ns projecteuler9-test
  (:require [projecteuler9 :refer [solution-tail-recur
                                   solution-recur
                                   solution-modules
                                   solution-with-map
                                   solution-with-for
                                   solution-with-loop
                                   solution-lazy]]
            [clojure.test :refer [deftest is run-all-tests]]))

(def sum-value 1000)

(def answer 31875000)

(deftest test-solution-tail-recur
  (is (=
       (solution-tail-recur sum-value)
       answer)))

(deftest test-solution-recur
  (is (=
       (solution-recur sum-value)
       answer)))

(deftest test-solution-modules
  (is (=
       (solution-modules sum-value)
       answer)))

(deftest test-solution-with-map
  (is (=
       (solution-with-map sum-value)
       answer)))

(deftest test-solution-with-for
  (is (=
       (solution-with-for sum-value)
       answer)))

(deftest test-solution-with-loop
  (is (=
       (solution-with-loop sum-value)
       answer)))

(deftest test-solution-lazy
  (is (=
       (solution-lazy sum-value)
       answer)))

(comment
  (run-all-tests #"solution"))