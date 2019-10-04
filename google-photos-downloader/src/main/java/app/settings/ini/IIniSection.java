package app.settings.ini;

public interface IIniSection {

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or {@code null}
     *         if this map contains no mapping for the key
     */
    public String getValue(String key);

    /**
     * Returns {@code true} if the specified key (key) is mapped
     * or returns {@code false}.
     * @param key the key whose associated value is to be looked for.
     * @return Returns {@code true} if the specified key is mapped
     *         or returns {@code false}.
     */
    public boolean containsKey(String key);

    public String toString();
}
