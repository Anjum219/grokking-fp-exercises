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

## Run code depending on imported code

To run the exercises of chapter 8 and onwards (which depends on another scala project), I have a project `grokkingfp-exercises` which I pulled from https://github.com/miciek/grokkingfp-examples

e.g., Using `ch09_CurrencyExchange` from `grokkingfp-examples` in `grokking-fp-exercises`

### 1. Ensure JDK 17 is installed

Check your Java version:

```bash
java -version
```

If it’s not 17, install it (macOS + Homebrew example):

```bash
brew install openjdk@17
sudo ln -sfn /opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk
```

Set environment variables:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH=$JAVA_HOME/bin:$PATH
```

Confirm sbt uses Java 17:

```bash
sbt about
```

---

### 2. Prepare `grokkingfp-examples` for local publishing

In `grokkingfp-examples/build.sbt`, set a **simple organization and version**:

```scala
organization := "pl.manning"
version      := "1.0"
```

Publish locally:

```bash
cd /path/to/grokkingfp-examples
sbt +publishLocal
```

This installs the artifact in your local Ivy repository (`~/.ivy2/local`).

---

### 3. Add dependency in `grokking-fp-exercises`

In `grokking-fp-exercises/build.sbt`, add:

```scala
libraryDependencies += "pl.manning" %% "grokkingfp-examples" % "1.0"
```

Now your project can see all public objects from `grokkingfp-examples`.

---

### 4. Import and use the code

Example in `chap-09-streams-as-values.scala`:

```scala
import ch09_CurrencyExchange.exchangeRatesTableApiCall

object Chap09 extends App {
  println(exchangeRatesTableApiCall("USD"))
}
```

Run:

```bash
sbt "runMain Chap09"
```

---

### 5. Notes / Best Practices

* Avoid spaces, special characters, and parentheses in `organization` and `artifactId`.
* Every time you modify `grokkingfp-examples`, run `sbt publishLocal` to update the local artifact.
* This approach avoids the complexity of a superbuild or multi-project setup.


