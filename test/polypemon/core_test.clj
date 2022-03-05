(ns polypemon.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [midje.sweet :refer [fact =>]]
            [polypemon.core :refer [zero-pad
                                    str-strip-numbers
                                    initials]]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 0))))

(def three-pad (partial zero-pad 3))

(fact
 "Returns a zero padded string representation of integer"
 (three-pad 3) => "003"
 (zero-pad 4 1) => "0001"
 (zero-pad 4 15111) => "15111"
 (zero-pad 5 2) => "00002")

(fact
 "Returns a vector of integer numbers
  embedded in a string argument"
 (str-strip-numbers "ab11cdd2k.144") => [11, 2, 144]
 (str-strip-numbers "Ignacio Vazquez-Abrams") => [])

(fact
 "Reduces authors to initials"
 (initials "Ignacio Vasquez-Adams") => "I.V-A."
 (initials "John Keegan") => "J.K.")
