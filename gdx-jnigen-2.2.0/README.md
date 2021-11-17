## gdx-jnigen

[![Build Status](https://github.com/libgdx/gdx-jnigen/workflows/Build%20and%20deploy/badge.svg)](https://github.com/libgdx/gdx-jnigen/actions?query=workflow%3A"Build+and+deploy")

The gdx-jnigen tool can be used with or without libgdx to allow C/C++ code to be written inline
with Java source code. 

This increases the locality of code that conceptually belongs together (the Java native class methods and the actual implementation) and makes refactoring a lot easier
compared to the usual JNI workflow. Arrays and direct buffers are converted for you, further
reducing boilerplate. Building the natives for Windows, Linux, OS X, and Android and iOS is handled for
you. jnigen also provides a mechanism for loading native libraries from a JAR at runtime, which
avoids "java.library.path" troubles.

See the LibGDX Wiki for usage: https://github.com/libgdx/libgdx/wiki/jnigen