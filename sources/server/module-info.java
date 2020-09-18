/**
  * A REST server spinning around in a couple of threads and routing from the Battlesnake API to the game simulation.
  */
module server
{
  requires java.base;
  requires log;
  requires runtime;
  requires game;
  requires battlesnake_api;
  exports event_loop;
}
