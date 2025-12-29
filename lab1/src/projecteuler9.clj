(ns projecteuler9)

(defn hypotenuse [a b] (Math/sqrt (+ (* a a) (* b b))))

(comment
  (hypotenuse 3 4)
  (hypotenuse 6 8)
  (hypotenuse 1 2))

(defn solution-tail-recur
  ([n]  (solution-tail-recur n 1 2))
  ([sum-value a b]
   (if (== sum-value (+ a b  (hypotenuse a b)))
     (int (* a b (hypotenuse a b)))
     (recur sum-value
            (if (= (inc a) b) 1 (inc a))
            (if (= (inc a) b) (inc b) b)))))

;;               |
;; StackOverFlow |
;;               v
;;
;; (defn solution-recur
;;   ([]    (solution-recur 1000))
;;   ([n] (solution-recur n 1 2))
;;   ([sum-value a b]
;;    (if (== sum-value (+ a b (hypotenuse a b)))
;;      (* a b (hypotenuse a b))
;;      (solution-recur sum-value
;;                      (if (= (inc a) b) 1 (inc a))
;;                      (if (= (inc a) b) (inc b) b)))))

(defn solution-recur
  ([n] (solution-recur n 2))
  ([sum-value b] (loop [a 1]
                   (if (< a b)
                     (if (== sum-value (+ a b (hypotenuse a b)))
                       (int (* a b (hypotenuse a b)))
                       (recur (inc a)))
                     (solution-recur sum-value (inc b))))))

(defn get-natural-pairs [n]
  (for [x (range 1 n)
        y (range 1 n)]
    [x y]))

(defn Pythagorean-triplet? [a b c]
  (== (+ (* a a) (* b b)) (* c c)))

(defn sum-equal? [sum-value & args]
  (== sum-value (apply + args)))

(defn solution-modules [n]
  (->> (get-natural-pairs n)
       (filter #(apply < %))
       (map #(conj % (apply hypotenuse %)))
       (filter #(apply sum-equal? n %))
       flatten
       (reduce *)
       int))

(defn get-ordered-natural-pairs [n]
  (for [x (range 1 n)
        y (range 1 n)
        :when (< x y)]
    [x y]))

(defn solution-with-map [n]
  (->> (get-ordered-natural-pairs n)
       (map #(conj % (apply hypotenuse %)))
       (map (fn [triplet] [(apply + triplet) (apply * triplet)]))
       (filter #(== n (first %)))
       (map last)
       first
       int))

(defn solution-with-for [n]
  (int (first (for [a (range 1 n)
                    b (range 1 n)
                    :let [c (hypotenuse a b)]
                    :when (and (< a b)
                               (sum-equal? n a b c))]
                (* a b c)))))

(defn solution-with-loop [n]
  (loop [a 1
         b 2]
    (when (< b n)
      (let [c (hypotenuse a b)]
        (if (and (Pythagorean-triplet? a b c) (sum-equal? n a b c))
          (int (* a b c))
          (recur (if (= (inc a) b) 1 (inc a))
                 (if (= (inc a) b) (inc b) b)))))))

(defn get-Pythagorean-triplet-lazy
  ([] (get-Pythagorean-triplet-lazy 1 2))
  ([a b]
   (lazy-seq
    (cons [a b (hypotenuse a b)] (get-Pythagorean-triplet-lazy
                                  (if (= (inc a) b) 1 (inc a))
                                  (if (= (inc a) b) (inc b) b))))))

(defn solution-lazy [n]
  (int (reduce *
               (flatten
                (take 1
                      (filter
                       (partial apply sum-equal? n)
                       (get-Pythagorean-triplet-lazy)))))))

(comment

  (def sum-value 1000)

  (time (solution-tail-recur sum-value))

  (time (solution-recur sum-value))

  (time (solution-modules sum-value))

  (time (solution-with-map sum-value))

  (time (solution-with-for sum-value))

  (time (solution-with-loop sum-value))

  (time (solution-lazy sum-value)))