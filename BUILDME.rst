Polypemon a.k.a. Procrustes
***************************

Audio album builder; copy and edit on the fly.

Development
===========

- `Format Clojure code <https://github.com/weavejester/cljfmt>`__
- `GraalVM Native Image <https://www.graalvm.org/22.0/reference-manual/native-image/>`__
- `Testing with Midje <https://github.com/marick/Midje/wiki/A-tutorial-introduction>`__

Prerequisites
-------------

::

    $ yay -S jdk11-graalvm-bin
    $ sudo gu install native-image

Build
-----

::

    $ lein compile && lein uberjar

or just::

    $ lein run [-- --help]

Standalone build with GraalVM
-----------------------------

::

    $ native-image --report-unsupported-elements-at-runtime -jar target/uberjar/polypemon-0.1.0-SNAPSHOT-standalone.jar target/uberjar/polypemon --no-fallback --initialize-at-build-time

Format
------

::

    $ lein cljfmt check
    $ lein cljfmt fix

Test
----

::

    $ lein test

or::

    $ lein midje

Install
-------

Copy standalone ``polypemon`` target to ``~/.local/bin`` or ``/usr/local/bin``:

Publish
-------

::

    TODO
