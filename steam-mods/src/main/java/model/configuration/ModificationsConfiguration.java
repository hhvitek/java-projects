package model.configuration;

/**
 * Represents modifications part of configuration
 */
public interface ModificationsConfiguration {
    String getId();
    void setId(String id);

    String getName();
    void setName(String name);

    String getDescription();
    void setDescription(String description);

    String getClazz();
    void setClazz(String clazz);
}
