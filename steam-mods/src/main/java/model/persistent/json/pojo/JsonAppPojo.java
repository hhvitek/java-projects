package model.persistent.json.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonPropertyOrder({
        "default_mod_folder"
})
public class JsonAppPojo extends AbstractPojoAdditionalProperty {

    private static final Logger logger = LoggerFactory.getLogger(JsonAppPojo.class);

    @JsonProperty("default_mod_folder")
    private Path defaultModFolder;

    @JsonProperty("default_mod_folder")
    public @NotNull Path getDefaultModFolder() {
        return defaultModFolder;
    }

    @JsonProperty("default_mod_folder")
    public void setDefaultModFolder(Path defaultModFolder) {
        this.defaultModFolder = defaultModFolder;
    }

    /**
     * Returns shallow copy, so changes to returned map structure does not impact original map.
     */
    @JsonIgnore
    public Map<String, String> getAllProperties() {

        Map<String, String> appAdditionalProperties = getAdditionalProperties();
        appAdditionalProperties.put("default_mod_folder", defaultModFolder.toString());

        return appAdditionalProperties;
    }

    @JsonIgnore
    public void setAllProperties(Map<String, String> allProperties) {
        defaultModFolder = Path.of(allProperties.get("default_mod_folder"));

        allProperties.remove("default_mod_folder");

        additionalProperties = allProperties;
    }
}
