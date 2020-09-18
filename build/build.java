import static java.lang.System.*;
import static javax.tools.StandardLocation.*;
import static java.util.stream.Collectors.*;
import static java.util.Collections.*;
import static java.util.Arrays.*;
import static java.nio.file.Files.*;
import static java.nio.charset.StandardCharsets.*;

import java.util.*;
import java.io.*;
import java.util.stream.*;
import java.nio.charset.*;
import java.nio.file.*;

import javax.tools.*;

class build {
  public static void main(String[] command_line_arguments) throws Exception {
    out.println("BUILDING BATTLESNAKE");
    var module_names = new String[] { "main", "battlesnake_api", "game", "json", "log", "runtime", "server",
        "snake_ai" };
    // yields the CWD:
    var current_working_path = Paths.get("").toAbsolutePath();
    // Assumption: the build gets executed inside the build directory
    var root_path = current_working_path.getParent();
    out.println("root path is: " + root_path);
    var source_path = root_path.resolve("sources");
    var output_path = current_working_path.resolve("output");
    var module_output_path = output_path.resolve("mods");

    createDirectories(module_output_path);

    var compiler = ToolProvider.getSystemJavaCompiler();
    var file_manager = compiler.getStandardFileManager(
        // use default diagnostics for now
        null,
        // format for diagnostics. this compiler will never be localized, so we settle
        // on the lowest common denominator.
        Locale.US,
        // always force utf8, so that a random platform encoding cannot surprise us
        UTF_8);

    for (String module_name : module_names) {
      var path = singletonList(source_path.resolve(module_name));
      file_manager.setLocationForModule(MODULE_SOURCE_PATH, module_name, path);
    }
    file_manager.setLocationFromPaths(CLASS_OUTPUT, singletonList(module_output_path));

    // recursively find all java source files:
    var all_source_files = walk(source_path).filter(Files::isReadable).filter(Files::isRegularFile)
        .filter(build::hasJavaExtension).filter(build::isJavaFile).collect(toList());

    var compilation_units = file_manager.getJavaFileObjectsFromPaths(all_source_files);

    compiler.getTask(
        // print fatal errors to System.err
        null,
        // file manager that knows about input source files and output directories for
        // the compiler
        file_manager,
        // custom diagnostics; leave default for now
        null,
        // compiler options. do these have to be in command line argument form?
        List.of(
            // force universal encoding no matter the host platform
            "-encoding", UTF_8.toString(),
            // compile with debug symbols
            "-g",
            // extra output
            // "-verbose",
            // allow the compiler to make assumptions about the version of the java runtime
            "--release=13",
            // enable all linting
            "-Xlint:all",
            // we want to use preview features
            "-Xlint:-preview",
            // treat warnings as errors
            "-Werror",
            // print more information when printing errors
            "-Xdiags:verbose",
            // enable all linting regarding javadoc
            "-Xdoclint:all",
            // use modern javadoc format
            "--doclint-format", "html5",
            // we are exploring so might as well live on the bleeding edge.
            // also. JDK developers complained about not enough bug reports about preview
            // features
            // features of interest: switch expressions and text blocks
            "--enable-preview",
            // only allow these modules, so that we have to double check a decision to
            // import new ones
            "--limit-modules=java.base,java.logging,jdk.httpserver"),
        // compiler plugins (annotation processing)
        null,
        // the files, that need compiling
        compilation_units).call();
  }

  private static boolean hasJavaExtension(Path path) {
    return path.toString().endsWith(".java");
  }

  private static boolean isJavaFile(Path path) {
    try {
      return "text/plain".equals(probeContentType(path));
    } catch (IOException exception) {
      return false;
    }
  }

  static enum tasks {
    compile, unit_test, integration_test, deploy, run, debug, clean, watch_test, watch, javadoc, deploy_javadoc;
  }
}