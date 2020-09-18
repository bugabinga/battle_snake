/**
 * A serializer that can read and write the
 * <a href="https://www.json.org/json-en.html">JSON Format</a>. This
 * implementation is probably inefficient and incomplete, but correct and fast
 * enough for the purposes of the Battlesnake API.
 * <p>
 * Following assumptions have been made during development of this module:
 * <ul>
 * <li>JSON payloads are small (max 4kb)
 * <li>they do not exceed a nesting lvl of 5
 * </ul>
 */
module json {
  requires java.base;

  exports composer;
  exports parser;
}