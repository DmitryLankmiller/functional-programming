# Лабораторная работа №1

---

Студент: Курочка Дмитрий Сергеевич
ИСУ: 373305
Группа: P3312

---

## Решения для Задачи Projecteuler 9

Задача 9 заключается в нахождении Пифагоровой тройки, для которой выполняется условие a + b + c = 1000

В ходе решения используется функция для нахождения гипотенузы двух катетов:

```clojure
(defn hypotenuse [a b] (Math/sqrt (+ (* a a) (* b b))))
```

### 1. Хвостовая рекурсия

Решение задачи с использованием хвостовой рекурсии:

```clojure
(defn solution-tail-recur
  ([n]  (solution-tail-recur n 1 2))
  ([sum-value a b]
   (if (== sum-value (+ a b  (hypotenuse a b)))
     (int (* a b (hypotenuse a b)))
     (recur sum-value
            (if (= (inc a) b) 1 (inc a))
            (if (= (inc a) b) (inc b) b)))))
```

### 2. Обычная рекурсия

Решение задачи с применением обычной рекурсии:

```clojure
(defn solution-recur
  ([n] (solution-recur n 2))
  ([sum-value b] (loop [a 1]
                   (if (< a b)
                     (if (== sum-value (+ a b (hypotenuse a b)))
                       (int (* a b (hypotenuse a b)))
                       (recur (inc a)))
                     (solution-recur sum-value (inc b))))))
```

### 3. Модульное решение

Решение задачи с разбиением на модули: создание последовательности и её дальнейшая обработка:

```clojure
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
```

### 4. Решение с использованием `map`

Решением с использованием функции `map` для преобразования последовательности:

```clojure
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
```

### 5. Решение с использованием цикла `for`

Решением с использованием цикла `for` для перебора элементов:

```clojure
(defn solution-with-for [n]
  (int (first (for [a (range 1 n)
                    b (range 1 n)
                    :let [c (hypotenuse a b)]
                    :when (and (< a b)
                               (sum-equal? n a b c))]
                (* a b c)))))
```

### 6. Решение с использованием цикла `loop`

Решением с использованием цикла `loop`:

```clojure
(defn solution-with-loop [n]
  (loop [a 1
         b 2]
    (when (< b n)
      (let [c (hypotenuse a b)]
        (if (and (Pythagorean-triplet? a b c) (sum-equal? n a b c))
          (int (* a b c))
          (recur (if (= (inc a) b) 1 (inc a))
                 (if (= (inc a) b) (inc b) b)))))))
```

### 7. Ленивые коллекции

Решение с использование ленивых коллекций:

```clojure
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
```

## Решения для Задачи 21

Задача 21 заключается в нахождении произведения всех amiable чисел до 10000. Числа a и b являются amiable, если d(a) = b и d(b) = a, где d(n) - определена как сумма собственных делителей числа n.

В ходе решения используется функция для нахождения делителей числа и сама функция d(n):

```clojure
(defn divisors [n]
  (filter (comp zero? (partial rem n)) (range 1 n)))

(defn d [n]
  (apply + (divisors n)))
```

### 1. Хвостовая рекурсия

Решение задачи с использованием хвостовой рекурсии:

```clojure
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
```

### 2. Модульное решение

Решение задачи модульным подходом:

```clojure
(defn amicable? [a]
  (let [b (d a)]
    (and (not= a b)
         (= a (d b)))))

(defn solution-modules [n]
  (->> (range 1 n)
       (filter amicable?)
       (reduce +)))
```

### 3. Решение с использованием `map`

Решение задачи с использованием `map`:

```clojure
(defn solution-with-map [n]
  (->> (range 1 n)
       (map #(into [] [% (d %)]))
       (filter #(not= (first %) (last %)))
       (map #(into [] [(first %) (d (last %))]))
       (filter #(apply = %))
       (map first)
       (reduce +)))
```

### 4. Решением с циклом `for`

Для решения задачи используется `for`:

```clojure
(defn solution-with-for [n]
  (reduce + (for [a (range n)
                  :when (amicable? a)]
              a)))
```

### 5. Решением с циклом `loop`

Для решения задачи используется `loop`:

```clojure
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
```

### 6. Ленивые коллекции

Для решения задачи используется ленивые последовательности:

```clojure
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
```

### 7. Обычная рекурсия

Решение задачи с использованием обычной рекурсии:

```clojure
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
```

## Тестирование

Прохождение тестов:

```
clj -X:test

Running tests in #{"test"}

Testing projecteuler21-test

Testing projecteuler9-test

Ran 14 tests containing 14 assertions.
0 failures, 0 errors.
```

## Заключение

Представленные решения демонстрируют различные подходы к решению задач с использованием функционального программирования на Clojure. Эти примеры показывают, как рекурсия, циклы и функции высшего порядка могут эффективно решать математические задачи.
