(ns orcpub.common
  (:require [clojure.string :as s]))

(defn name-to-kw [name]
  (-> name
      clojure.string/lower-case
      (clojure.string/replace #"'" "")
      (clojure.string/replace #"\W" "-")
      keyword))

(defn map-by-key [values]
  (zipmap (map :key values) values))

(defmacro ptime [message body]
  `(do (prn ~message)
       (time ~body)))

(defn bonus-str [val]
  (str (if (pos? val) "+") val))

(defn map-vals [val-fn m]
  (reduce-kv
   (fn [m2 k v]
     (assoc m2 k (val-fn k v)))
   {}
   m))
