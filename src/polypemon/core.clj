(ns polypemon.core (:gen-class)
    (:require [cli-matic.core :refer [run-cmd]]
              [clojure.string :as cljstr]))

;; To run this, try from the project root:
;; clj -i examples/polypemon.clj -m polypemon add -a 1 -b 80

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

(defn zero-pad
  "Returns i zero-padded to n"
  [n i]
  (format (str "%0" n "d") i))

(defn str-strip-numbers
  "Returns a vector of integer numbers
  embedded in a string argument"
  [s]
  (let [matcher (re-matcher #"\d+" s)]
    (loop [match (re-find matcher) result []]
      (if-not match
        result
        (recur (re-find matcher) (conj result (Integer/parseInt match)))))))

(defn initials
  "Reduces authors to initials"
  [authors]
  (->> (-> (cljstr/replace authors #"\"(?:\\.|[^\"\\])*\"" " ")
           (cljstr/replace #"\"" " ")
           (cljstr/split #","))
       (filter (fn [author]
                 (-> (cljstr/replace author #"[-.]+" "")
                     (cljstr/blank?)
                     (not))))
       (map (fn [author] (as-> (cljstr/split author #"-") b
                           (filter (fn [barrel]
                                     (-> (cljstr/replace barrel #"[.]+" "")
                                         (cljstr/blank?)
                                         (not))) b)
                           (map (fn [barrel] (->> (cljstr/split barrel #"[\s.]+")
                                                  (filter (fn [name]
                                                            (not (cljstr/blank? name))))
                                                  (map (fn [name]
                                                         (cljstr/upper-case (str (first name)))))
                                                  (cljstr/join "."))) b)
                           (cljstr/join "-" b)
                           (str b "."))))
       (cljstr/join ",")))

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
