package model.configuration.json.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import model.configuration.AppConfiguration;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

@JsonPropertyOrder({
        "default_mod_folder"
})
public class JsonAppPojo extends AbstractPojoAdditionalProperty implements AppConfiguration {

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
}
