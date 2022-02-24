(ns polypemon.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [midje.sweet :refer [fact =>]]
            [polypemon.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 0))))

(fact
 "Returns a zero padded string representation of integer"
 (zero-pad 1 4) => "0001"
 (zero-pad 15111 4) => "15111"
 (zero-pad 2 5) => "00002")
