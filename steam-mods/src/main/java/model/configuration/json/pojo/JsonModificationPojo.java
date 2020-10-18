package model.configuration.json.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import model.configuration.ModificationsConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.ConstructorProperties;

/**
 * In the json format:
 *     ...
 *     "modifications": [
 *         {
 *             "id": "EXTRACT_ALL",
 *             "name": "Extract All",
 *             "description": "Extract all .zip files in the given folder.",
 *             "class": "model.modifications.ExtractAllModifications"
 *         },
 *         {
 *             ...
 *         }
 *     ...
 */


@JsonPropertyOrder({
        "id",
        "name",
        "description",
        "class"
})
public class JsonModificationPojo implements ModificationsConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(JsonModificationPojo.class);

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("class")
    private String clazz;

    @ConstructorProperties({"id", "name", "clazz"})
    public JsonModificationPojo(String id, String name, String clazz) {
        this.id = id;
        this.name = name;
        this.clazz = clazz;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("class")
    public String getClazz() {
        return clazz;
    }

    @JsonProperty("class")
    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", clazz='" + clazz + '\'' +
                '}';
    }
}
