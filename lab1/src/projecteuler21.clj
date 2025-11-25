(ns projecteuler21)

(defn divisors [n]
  (filter (comp zero? (partial rem n)) (range 1 n)))

(comment
  (divisors 220)
  (divisors 284))

(defn d [n]
  (apply + (divisors n)))

(comment
  (d 220)
  (d 284))

(defn solution-tail-recur
  ([n] (solution-tail-recur n #{} 1))
  ([max-value amicable-numbers a]
   (if (> a max-value) (apply + amicable-numbers)
       (let [b (d a)]
         (if (and (not= a b) (= (d b) a))
           (recur max-value
                  (conj amicable-numbers a)
                  (inc a))
           (recur max-value amicable-numbers (inc a)))))))

;; StackOverFlow :(
;; (defn solution-recur
;;   ([n] (solution-recur n #{} 1))
;;   ([max-value amicable-numbers a]
;;    (if (> a max-value) (apply + amicable-numbers)
;;        (let [b (d a)]
;;          (if (and (not= a b) (= (d b) a))
;;            (solution-recur max-value
;;                            (conj amicable-numbers a b)
;;                            (inc a))
;;            (solution-recur max-value amicable-numbers (inc a)))))))

(defn amicable? [a]
  (let [b (d a)]
    (and (not= a b)
         (= a (d b)))))

(comment
  (amicable? 5)
  (amicable? 6)
  (amicable? 220)
  (amicable? 284))

(defn solution-modules [n]
  (->> (range 1 n)
       (filter amicable?)
       (reduce +)))

(defn solution-with-map [n]
  (->> (range 1 n)
       (map #(into [] [% (d %)]))
       (filter #(not= (first %) (last %)))
       (map #(into [] [(first %) (d (last %))]))
       (filter #(apply = %))
       (map first)
       (reduce +)))

(defn solution-with-for [n]
  (reduce + (for [a (range n)
                  :when (amicable? a)]
              a)))

(defn solution-with-loop [n]
  (loop [amicable-numbers (loop [a 1
                                 amicable-numbers []]
                            (if (> a n) amicable-numbers
                                (recur (inc a)
                                       (if (amicable? a)
                                         (conj amicable-numbers a)
                                         amicable-numbers))))
         acc 0]
    (if (empty? amicable-numbers) acc
        (recur (rest amicable-numbers) (+ acc (first amicable-numbers))))))

(defn amicable-numbers ([] (amicable-numbers (iterate inc 1)))
  ([nums]
   (lazy-seq (cons
              (first (filter amicable? nums))
              (rest (filter amicable? nums))))))

(defn solution-lazy [n]
  (reduce +
          (for [x (amicable-numbers)
                :while (<= x n)]
            x)))

(defn solution-recur
  ([n] (solution-recur (for [x (amicable-numbers)
                             :while (<= x n)]
                         x)
                       0))
  ([amicable-numbers acc]
   (if (empty? amicable-numbers) acc
       (solution-recur
        (rest amicable-numbers)
        (+ acc (first amicable-numbers))))))

(comment

  (def max-value 10000)

  (time (solution-tail-recur max-value))

  (time (solution-modules max-value))

  (time (solution-with-map max-value))

  (time (solution-with-for max-value))

  (time (solution-with-loop max-value))

  (time (solution-lazy max-value))

  (time (solution-recur max-value)))