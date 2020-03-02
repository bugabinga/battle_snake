module server {
  requires java.base;
  requires log;
  requires runtime;
  requires game;
  requires battlesnake_api;

  exports event_loop;
}
