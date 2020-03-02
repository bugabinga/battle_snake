# A custom build system

Since we are constrained to not use any third party dependencies, we have to roll our own build system.
Luckily for us, the JDK comes with 2 features, that aid in that endeavour:

- the `java` command can now [compile and run source files in one step]: `java MyScript.java`
- there is an interface `JavaCompiler` in the module [jdk.compiler] that allows us to write our own `javac`-frontend

> Another solution could have been to write the build system as a shell script.
> However, that would have restricted the build system to a unix environment.
> Also, let's be honest, there is something cool about building java with java :)

The build system can be run with `java build.java`.

> Notice that we are ignoring Java naming conventions here in order to differentiate this script from "real code".

Furthermore, we are experimenting with a feature of `java` called [command line argument files].
This allows us to put all command line arguments to `java` into a file, and then reference it:

```sh
$ #instead of
$ java -ea --source 13 build.java
$ cat build
$ -ea
$ --source 13
$ build.java
$ # reference the file by prefixing it with @
$ java "@build"
```

> Note: the "@" character is quoted because it has special meaning in some shells (e.g powershell)

Unfortunately, there seems to be a bug that leads to a segfault, sometimes, when argument files are used.

## Using the build system

Change into the directory `build` and run `java build.java`.
The script assumes that the current working directory is `build`.

By default the task "compile" is run.

## Tasks

The build can execute the following use cases:

- compile (default): Compile the source code to `*.class` files into `build/output/mods`.
                     This compile is tuned for debugging in favor of runtime performance.
- watch            : Execute `compile` every time a file in `sources` changes.
- debug            : Execute the main module for the purposes of debugging.
                     Attaches a [jdb] instance to the application.
- unit_test        : Executes all Java files in `sources`, that are named according to the "*.test.java" pattern.
- watch_test       : Executes `unit_test` every time a unit test file changes.
- integration_test : Executes all Java files in `integration_tests`.
- clean            : Deletes `output`, which contains all files generated by the build.
- run              : Run the production version of the application.
- deploy           : Deploy the production version of the application to the cloud.

### compile
TODO
### watch
TODO
### debug
TODO
### unit_test
### integration_test
TODO
### clean
TODO
### run
TODO
### deploy
TODO

[jdb]: https://docs.oracle.com/en/java/javase/13/docs/specs/man/jdb.html
[jdk.compiler]: https://download.java.net/java/early_access/jdk14/docs/api/jdk.compiler/module-summary.html
[compile and run source files in one step]: http://openjdk.java.net/jeps/330
[command line argument files]: https://docs.oracle.com/en/java/javase/13/docs/specs/man/java.html#java-command-line-argument-files