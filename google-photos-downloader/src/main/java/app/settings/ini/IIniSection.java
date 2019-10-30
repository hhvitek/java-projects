package app.settings.ini;

public interface IIniSection {

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or {@code null}
     *         if this map contains no mapping for the key
     */
    public String getValue(String key);

    /**
     * Returns {@code true} if the specified key (key) is mapped
     * or returns {@code false}.
     *
     * @param key the key whose associated value is to be looked for.
     * @return Returns {@code true} if the specified key is mapped
     *         or returns {@code false}.
     */
    public boolean containsKey(String key);

    /**
     * Inserts the new value into the section
     *
     * @param key   the name of the specific item
     * @param value the associated value to be inserted into the structure.
     */
    public void putValue(String key, String value);

    /**
     * Inserts the new comment for the specific item (key). If the key is null, the comment is used
     * as a header comment for the whole section.
     *
     * @param key     the name of the specific item
     * @param comment the associated comment to be inserted into the structure
     */
    public void putComment(String key, String comment);

    /**
     * Returns the comment for the specific item (key). If the parameter key is null, returns
     * section header comment.
     *
     * @return
     */
    public String getComment(String key);

    public String toString();
}
