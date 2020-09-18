/**
 * Boots up the application and wires all modules together. The logging system
 * is initialized here and the server module spun up.
 */
module main {
  requires java.base;
  requires server;
  requires log;
  requires runtime;
}
