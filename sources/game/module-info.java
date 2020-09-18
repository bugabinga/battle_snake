/**
 * This module holds the game state and simulation logic pertaining to
 * Battlesnake.
 */
module game {
  requires java.base;
  requires runtime;
  requires log;
  requires snake_ai;

  exports world;
}
