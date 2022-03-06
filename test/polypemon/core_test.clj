(ns polypemon.core-test
  (:require [midje.sweet :refer [fact =>]]
            [polypemon.core :refer [zero-pad
                                    str-strip-numbers
                                    initials]]))

(fact
 "Returns a zero padded string representation of integer"
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
 (initials "") => ""
 (initials " ") => ""
 (initials ".. , .. ") => ""
 (initials " ,, .,") => ""
 (initials ", a. g, ") => "A.G."
 (initials "- , -I.V.-A,E.C.N-, .") => "I.V-A.,E.C.N."
 (initials "John ronald reuel Tolkien") => "J.R.R.T."
 (initials "  e.B.Sledge ") => "E.B.S."
 (initials "Apsley Cherry-Garrard") => "A.C-G."
 (initials "Windsor Saxe-\tCoburg - Gotha") => "W.S-C-G."
 (initials "Elisabeth Kubler-- - Ross") => "E.K-R."
 (initials "  Fitz-Simmons Ashton-Burke Leigh") => "F-S.A-B.L."
 (initials "Arleigh \"31-knot\"Burke ") => "A.B."
 (initials "Harry \"Bing\" Crosby, Kris \"Tanto\" Paronto") => "H.C.,K.P."
 (initials "William J. \"Wild Bill\" Donovan, Marta \"Cinta Gonzalez") => "W.J.D.,M.C.G."
 (initials "A. Strugatsky, B...Strugatsky.") => "A.S.,B.S."
 (initials "Иржи Кропачек,, йозеф Новотный") => "И.К.,Й.Н."
 ;(initials "язон динАльт, шарль д'Артаньян") => "Я.динА.,Ш.д'А."
 ;(initials "шарль д'артаньян") => "Ш.Д."
 ;(initials "Charles de Batz de Castelmore d'Artagnan") => "C.d.B.d.C.d'A."
 ;(initials "Mario Del Monaco, Hutchinson of London") => "M.D.M.,H.o.L."
 ;(initials "Anselm haut Rodric") => "A.h.R."
 ;(initials "Ансельм от Родрик") => "А.о.Р."
 ;(initials "Leonardo Wilhelm DiCaprio") => "L.W.DiC."
 ;(initials "De Beers, Guido van Rossum") => "D.B.,G.v.R."
 ;(initials "Манфред фон Рихтгофен") => "М.ф.Р."
 ;(initials "Armand Jean du Plessis") => "A.J.d.P."
 (initials "Rory O'Connor") => "R.O'C."
 ;(initials "Öwyn Do'Üwr") => "Ö.Do'Ü."
 ;(initials "öwyn Do'üwr") => "Ö.D."
 ;(initials "Jason dinAlt") => "J.dinA."
 ;(initials "Jackie McGee") => "J.McG."
 ;(initials "DAMadar") => "DA."
 ;(initials "johannes diderik van der waals") => "J.D.v.d.W."
 (initials "Ross Macdonald") => "R.M."
 (initials "'") => "'."
 (initials "a.s.,b.s.") => "A.S.,B.S.")
