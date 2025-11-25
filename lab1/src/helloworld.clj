(ns helloworld)

(defn volume [x, y, z] (* x  y z))

(defn -main []
  (println "Hello, World!")
  (println (* 2 8))
  (println (volume 2 8 6)))

(-main)

(* 2 8)

(volume 4 2 3)