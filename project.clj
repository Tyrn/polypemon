(defproject polypemon "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [midje "1.10.5"]
                 [cli-matic "0.4.3"]]
  :plugins [[lein-cljfmt "0.8.0"]
            [lein-midje "3.2.2"]]
  ;; :main polypemon.core
  ;; :aot :all
  :main ^:skip-aot polypemon.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}}
  :repl-options {:init-ns polypemon.core})
