package model.persistent.json.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.ConstructorProperties;
import java.util.List;

@JsonPropertyOrder({
        "id",
        "name",
        "modifications-ids",
})
public class JsonModificationsChainPojo {

    private static final Logger logger = LoggerFactory.getLogger(JsonModificationsChainPojo.class);

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String desc;

    @JsonProperty("modifications-ids")
    private List<String> modificationsIds;

    @ConstructorProperties({"id", "name"})
    public JsonModificationsChainPojo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonProperty("id")
    @NotNull
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(final String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(final String name) {
        this.name = name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return name;
    }

    @JsonProperty("description")
    public void setDescription(final String desc) {
        this.desc = desc;
    }

    @JsonProperty("modifications-ids")
    public List<String> getModificationsIds() {
        return modificationsIds;
    }

    @JsonProperty("modifications-ids")
    public void setModificationsIds(final List<String> modificationsIds) {
        this.modificationsIds = modificationsIds;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", modificationsIds=" + modificationsIds +
                '}';
    }
}
