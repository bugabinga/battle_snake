package option;

/**
 * Handles all external configurability of the application.
 *
 * <h2>Sources of config</h2>
 * <p>
 * There a 3 sources where external configuration could originate from:
 * <ol>
 * <li>Command line arguments to the process
 * <li>Environment variables in the system
 * <li>The <code>config.properties</code> file
 * </ol>
 * All three sources can be used exclusively or in a mix to provide any config
 * value this app understands.
 *
 * <h2>Config names</h2>
 * <p>
 * The names of the keys to the configuration values are identical for all
 * incoming sources. In order to follow the convention of upper case environment
 * variable names, those key names are also always upper cased. Let imagine for
 * example, we wanted to set the TCP port of the REST server via configuration
 * variables. The key would be named <code>BS_TCP_PORT</code> and the value
 * would be expected to be some short integer.
 *
 * <blockquote> The key name is prefixed with "BS_" in order to minimize the
 * chance of a naming clash with other environment variables. We could have
 * chosen to follow several naming conventions regarding key names. For example
 * command line argument typically are prefixed with "-" or "--" and keys in
 * property files typically use "." as a word separator. But we keep it simple
 * by using the lowest common denominator approach. </blockquote>
 * <p>
 * As command line argument:
 *
 * <pre>
 * $ battle_snake BC_TCP_PORT=6666
 * </pre>
 *
 * As environment variable:
 *
 * <pre>
 * $ # the exact method of setting environment variables depends on your system...
 * $ set BC_TCP_PORT=6666
 * $ battle_snake
 * </pre>
 *
 * As property:
 *
 * <pre>
 * $ cat config.properties
 * $ BC_TCP_PORT=6666
 * $ battle_snake
 * </pre>
 *
 * In the case the same key is specified using multiple sources, the principle
 * of locality is used to decide precedence.
 *
 * <pre>
 * command line arguments &gt; config.properties &gt; environment variables
 * </pre>
 */
public class Configuration {

}