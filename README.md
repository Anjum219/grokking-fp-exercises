This repository contains some basic Scala exercises. These exercises are references in [Grokking Functional Programming book](https://www.manning.com/books/grokking-functional-programming).

Each chapter's exercise problems and solutions are grouped in a scala file inside `src/main/scala` directory.
For example, `chap-03-*.scala` file holds the exercises of Chapter 3 of the book mentioned.

The book gives you the concept of functional paradigm.
You can use these exercises to learn how to write functional programs and practice those concepts.

## Set up

Clone this repository and inside the repository, use `sbt` command to initialize the project.
_Notice, we are using Scala 3 (build.sbt defines the version `3.3.1`). If you want to use Scala 2 or a specific version, please define is in vuild.sbt
We are using sbt `1.9.0` For other specific version, please define inside project/build.properties_

Inside `sbt` console, use `run` to run the project.

To run a specific class, you can also exec in `sbt` then run `runMain chap04`. This will run the `chap04` class in file `src/main/scala/chap-04-functions-as-values.scala`
