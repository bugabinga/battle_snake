/**
 * The REST-API of a Battlesnake slave.
 */
module battlesnake_api {
  requires java.base;
  requires jdk.httpserver;

  exports rest;
  exports data;
}