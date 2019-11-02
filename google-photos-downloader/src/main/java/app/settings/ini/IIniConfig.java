package app.settings.ini;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

/**
 * Specifies all methods required by INI library.
 */
public interface IIniConfig {

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param sectionName the key (section name) whose associated value is to be returned
     * @return the value of IIniSection ({@link IIniSection}) to which the specified key is mapped,
     *         or
     *         {@code null} if this map contains no mapping for the key
     */
    IIniSection getSection(String sectionName);

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param sectionName the first part of the key (section name) whose associated value is to be
     *                    returned
     * @param key         the second part of the key (key) whose associated value is to be returned
     * @return the value to which the specified key is mapped, or {@code null}
     *         if this map contains no mapping for the key
     */
    String getValue(String sectionName, String key);

    /**
     * Returns {@code true} if the specified key (sectionName and key) is mapped
     * or returns {@code false}.
     *
     * @param sectionName the first part of the key (section name) whose associated value is to be
     *                    searched
     * @param key         the second part of the key (key) whose associated value is to be looked
     *                    for.
     * @return Returns {@code true} if the specified key (sectionName and key) is mapped
     *         or returns {@code false}.
     */
    boolean containsKey(String sectionName, String key);

    /**
     * Returns {@code true} if the specified section (sectionName) is mapped
     * or returns {@code false}.
     *
     * @param sectionName the section name to look for.
     * @return Returns {@code true} if the specified section (sectionName) is mapped
     *         or returns {@code false}.
     */
    boolean containsSection(String sectionName);

    /**
     * Loads the configuration file
     *
     * @param file the configuration file
     * @throws IOException
     * @throws InvalidConfigFileFormatException
     */
    void load(File file) throws IOException, InvalidConfigFileFormatException;

    void load(BufferedReader reader) throws IOException, InvalidConfigFileFormatException;

    /**
     * Adds the comment to the section. If the section is {@code null}, the comment is used as a header for
     * the whole ini configuration.
     *
     * @param sectionName section name
     * @param comment     comment to add
     */
    void putComment(String sectionName, String comment);

    /**
     * Adds the comment to the specified item in the section.
     *
     * If the section is {@code null}, the comment is used as a header for the whole ini configuration.
     * If the key is {@code null}, the comment is used as a header for the specified section.
     * If both the section and the key are {@code null}, than the comment is used as the header for the
     * whole ini configuration.
     *
     * @param sectionName
     * @param key
     * @param comment
     */
    void putComment(String sectionName, String key, String comment);

    /**
     * Simply replace/add header comment.
     *
     * @param comment
     */
    void putHeaderComment(String comment);

    /**
     * Returns the comment for the specific item, identified by sectionName and key of the item.
     *
     * @param sectionName
     * @param key
     * @return
     */
    String getComment(String sectionName, String key);

    String getHeaderComment();

    /**
     * Creates the new section, if the section already exists ignore.
     *
     * @param sectionName
     */
    void putSection(String sectionName);

    /**
     * Adds the new value with specified mapping (sectionName and key).
     *
     * @param sectionName
     * @param key
     * @param value
     */
    void putValue(String sectionName, String key, String value);

    /**
     * Stores the current object into the specified file.
     *
     * @param file
     * @throws IOException
     */

    void store(File file) throws IOException;

    String toString();
}
