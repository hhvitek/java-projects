package app.settings.ini.custom.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import app.settings.ini.IIniConfig;
import app.settings.ini.InvalidConfigFileFormatException;

/**
 * Parses specific ini configuration file such as:
 *
 * {@code
 * # my header comment 1st line.
 * # my header comment 2nd line.
 *
 * # my first section comment.
 * [first_section]
 * first_key = first_value
 * second_key = second_value
 *
 * [second_section]
 * # the second section, first item comment.
 * first_ket = first_value_second_section
 * }
 *
 * Reads the input line by line. The any line of the valid configuration file have to comfort to
 * the one of the following rules:
 *
 * 1] Comment
 * - Begins with the "#" character
 * - #[space]any characters to the end of line
 * 2] Empty line
 * 3] Section
 * - Begins with the "[" character
 * - [[a-zA-Z_0-9]+] -> word character \w+
 * 4] Item line defined by key and value
 * - Begins with any word character \w
 * - key = value
 * - \w+[space]=[space]\w+
 *
 *
 * @author vitek
 *
 */
public interface ILoader {
    public void load(IIniConfig ini, File file)
            throws IOException, InvalidConfigFileFormatException;

    public void load(IIniConfig ini, BufferedReader reader)
            throws IOException, InvalidConfigFileFormatException;
}
