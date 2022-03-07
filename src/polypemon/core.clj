(ns polypemon.core (:gen-class)
    (:require [cli-matic.core :refer [run-cmd]]
              [clojure.string :as cs]))

;; To run this, try from the project root:
;; clj -i examples/polypemon.clj -m polypemon add -a 1 -b 80

(defn zero-pad
  "Returns i zero-padded to n."
  [n i]
  (format (str "%0" n "d") i))

(defn str-strip-numbers
  "Returns a vector of integer numbers
  embedded in a string argument."
  [s]
  (let [matcher (re-matcher #"\d+" s)]
    (loop [match (re-find matcher) result []]
      (if-not match
        result
        (recur (re-find matcher) (conj result (Integer/parseInt match)))))))

(defn vcmp-int
  "Compares vectors of integers using 'string semantics'."
  [vx vy]
  (or (first (drop-while zero? (map compare vx vy))) (- (count vx) (count vy))))

(defn strcmp-naturally
  "If both strings contain digits, returns numerical comparison based on the numeric
  values embedded in the strings, otherwise returns the standard string comparison.
  The idea of the natural sort as opposed to the standard lexicographic sort is one of coping
  with the possible absence of the leading zeros in 'numbers' of files or directories."
  [str-x str-y]
  (let [num-x (str-strip-numbers str-x)  ;; building vectors of integers,
        num-y (str-strip-numbers str-y)] ;; possibly empty
    (if (and (not-empty num-x) (not-empty num-y))
      (vcmp-int num-x num-y)
      (compare str-x str-y))))

(defn form-initial
  "Makes an initial out of name,
  handling special cases like von, Mc, O', etc."
  [name]
  (let [cut (cs/split name #"'")]
    (cond
      ;; Deal with O'Connor and d'Artagnan.
      (and (> (count cut) 1) (not (cs/blank? (second cut))))
      (cond (and (Character/isLowerCase (first (second cut))) (not (cs/blank? (first cut))))
            (cs/upper-case (str (first (first cut))))
            :else (str (first cut) "'" (first (second cut))))

      ;; Deal with Leonardo DiCaprio and Jackie McGee.
      (and (> (count name) 1) (some #(Character/isUpperCase %) (rest name)))
      (loop [tail (rest name) prefix (first name)]
        (if (Character/isUpperCase (first tail))
          (str prefix (first tail))
          (recur (rest tail) (str prefix (first tail)))))

      (contains? #{"von" "фон" "van" "ван" "der" "дер" "til" "тиль" "zu" "цу" "af" "аф" "of"
                   "из" "de" "де" "des" "дез" "del" "дель" "dos" "душ" "дос" "du" "дю" "la"
                   "ла" "ля" "le" "ле" "haut" "от"} name) (str (first name))

      :else (cs/upper-case (str (first name))))))

(defn initials
  "Reduces comma separated list of
  authors to initials."
  [authors]
  (->> (-> (cs/replace authors #"\"(?:\\.|[^\"\\])*\"" " ")  ;; Remove quoted substrings.
           (cs/replace #"\"" " ")  ;; Remove odd quotes.
           (cs/split #","))
       (filter (fn [author]
                 (-> (cs/replace author #"[-.]+" "")
                     (cs/blank?)
                     (not))))
       (map (fn [author] (as-> (cs/split author #"-") b
                           (filter (fn [barrel]
                                     (-> (cs/replace barrel #"[.]+" "")
                                         (cs/blank?)
                                         (not))) b)
                           (map (fn [barrel] (->> (cs/split barrel #"[\s.]+")
                                                  (filter (fn [name]
                                                            (not (cs/blank? name))))
                                                  (map form-initial)
                                                  (cs/join "."))) b)
                           (cs/join "-" b)
                           (str b "."))))
       (cs/join ",")))

(defn add_numbers
  "Sums A and B together, and prints it in base `base`"
  [{:keys [a1 a2 base]}]
  (println
   (Integer/toString (+ a1 a2) base)))

(defn subtract_numbers
  "Subtracts B from A, and prints it in base `base` "
  [{:keys [pa pb base]}]
  (println
   (Integer/toString (- pa pb) base)))

(def CONFIGURATION
  {:app         {:command     "polypemon"
                 :description "A command-line toy calculator"
                 :version     "0.0.1"}
   :global-opts [{:option  "base"
                  :as      "The number base for output"
                  :type    :int
                  :default 10}]
   :commands    [{:command     "add" :short "a"
                  :description ["Adds two numbers together"
                                ""
                                "Looks great, doesn't it?"]
                  :opts        [{:option "a1" :short "a" :env "AA" :as "First addendum" :type :int :default 0}
                                {:option "a2" :short "b" :as "Second addendum" :type :int :default 0}]
                  :runs        add_numbers}
                 {:command     "sub"  :short "s"
                  :description "Subtracts parameter B from A"
                  :opts        [{:option "pa" :short "a" :as "Parameter A" :type :int :default 0}
                                {:option "pb" :short "b" :as "Parameter B" :type :int :default 0}]
                  :runs        subtract_numbers}]})

(defn -main
  "This is our entry point.
  Just pass parameters and configuration.
  Commands (functions) will be invoked as appropriate."
  [& args]
  (run-cmd args CONFIGURATION))
