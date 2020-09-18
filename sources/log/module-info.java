/**
 * The log module provides a single lazy, thread safe instance to a logging
 * interface.
 */
module log {
  requires java.base;
  requires java.logging;

  exports protocol;
}
