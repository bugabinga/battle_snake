/**
  * Contains augmentations to the java runtime that are shared throughout the application.
  * Augments are typically facades, delegators or extensions.
  */
module runtime
{
  requires java.base;
  exports thread;
  exports exception;
  exports option;
}
