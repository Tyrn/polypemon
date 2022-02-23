Polypemon a.k.a. Procrustes
***************************

Audio album builder; copy and edit on the fly.

Development
===========

- `Format Clojure code <https://github.com/weavejester/cljfmt>`__
- `GraalVM Native Image <https://www.graalvm.org/22.0/reference-manual/native-image/>`__

Prerequisites
-------------

::

    $ yay -S jdk11-graalvm-bin
    $ sudo gu install native-image

Build
-----

::

    $ lein compile && lein uberjar

Format
------

::

    $ lein cljfmt check
    $ lein cljfmt fix

Test
----

::

    $ lein

Install
-------

Install to ``~/.local/bin``:

::

    $ lein

Publish
-------

::

    TODO
