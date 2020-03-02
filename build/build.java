/**



 */

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
  public static void main(final String[] command_line_arguments)
  throws Exception
  {
    out.println("BUILDING BATTLESNAKE");
    var module_names = new String[]{"main", "battlesnake_api", "game", "json", "log", "runtime", "server", "snake_ai"};
    // yields the CWD:
    var current_working_path = Paths.get("").toAbsolutePath();
    // Assumption: the build gets executed inside the build directory
    var root_path = current_working_path.getParent();
    out.println("root path is: "+ root_path);
    var source_path = root_path.resolve("sources");
    var output_path = current_working_path.resolve("output");
    var module_output_path = output_path.resolve("mods");

    createDirectories(module_output_path);


    var compiler = ToolProvider.getSystemJavaCompiler();
    var file_manager = compiler.getStandardFileManager(
      // use default diagnostics for now
      null,
      // format for diagnostics. this compiler will never be localized, so we settle on the lowest common denominator.
      Locale.US,
      UTF_8
    );




    for( String module_name : module_names)
    {
      file_manager.setLocationForModule(MODULE_SOURCE_PATH,module_name, singletonList(source_path.resolve(module_name)));
    }
    file_manager.setLocationFromPaths(CLASS_OUTPUT, singletonList(module_output_path));

    // TODO recursivly find all java source files:
    var all_source_files = walk(source_path)
    .filter(Files::isReadable)
    .filter(Files::isRegularFile)
    .filter(path -> path.toString().endsWith(".java"))
    .filter(path -> {
      try{
        return "text/plain".equals(probeContentType(path));
      }
      catch(IOException exception)
      {
        exception.printStackTrace();
        return false;
      }
    })
    .peek(out::println)
    .collect(toList());
    var compilation_units = file_manager.getJavaFileObjectsFromPaths(all_source_files);

    compiler
    .getTask(
      //  print fatal errors to System.err
      null,
      // file manager that knows about input source files and output directories for the compiler
      file_manager,
      // custom diagnostics; leave default for now
      null,
      // compiler options. do these have to be in command line arguent form?
      Set.of(
        "-g",
        //"-verbose",
        "--source=13",
        "-Xlint:all",
        "--limit-modules=java.base,java.logging,jdk.httpserver"
        //"-encoding","UTF-8"
      ),
      // compiler plugins (annotation processing)
      null,
      // the files, that need compiling
      compilation_units
    )
    .call();
  }

  static enum tasks {
    compile,
    unit_test,
    integration_test,
    deploy,
    run,
    debug,
    clean,
    watch_test,
    watch;
  }
}