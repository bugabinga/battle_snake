package exception;

/**
 * This is the global exception handler for all threads in this app. As such, it
 * is responsible for error handling and failure recovery.
 * <p>
 * The general strategy in case of failure will be to log extensively and exit
 * the process soon. Everything potentially useful will be logged:
 * <ul>
 * <li>system environment variables
 * <li>jvm state (heap, variables, threads, ...)
 * <li>host system specs (os, ram, cpu, ...)
 * <li>stacktrace and exception infos
 * </ul>
 * We fail early in order to avoid corrupted program states and not waste other
 * peoples (or AIs) times. If there is a bug, let us fix it as soon as possible.
 */
public class FinalExceptionWall {

}