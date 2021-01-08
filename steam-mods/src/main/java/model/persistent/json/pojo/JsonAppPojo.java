package model.persistent.json.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.*;

@JsonPropertyOrder({
        "default_mod_folder"
})
public class JsonAppPojo extends AbstractPojoAdditionalProperty {

    private static final Logger logger = LoggerFactory.getLogger(JsonAppPojo.class);

    private Path defaultModFolder;

    private Path chosenModFolder;

    private String selectedModificationsChain;

    private List<Integer> managedModIds;

    public @NotNull Path getDefaultModFolder() {
        return defaultModFolder;
    }

    public void setDefaultModFolder(Path defaultModFolder) {
        this.defaultModFolder = defaultModFolder;
    }

    public Path getChosenModFolder() {
        return chosenModFolder;
    }

    public void setChosenModFolder(Path chosenModFolder) {
        this.chosenModFolder = chosenModFolder;
    }

    public String getSelectedModificationsChain() {
        return selectedModificationsChain;
    }

    public void setSelectedModificationsChain(String selectedModificationsChain) {
        this.selectedModificationsChain = selectedModificationsChain;
    }

    public List<Integer> getManagedModIds() {
        return managedModIds;
    }

    public void setManagedModIds(List<Integer> managedModIds) {
        this.managedModIds = managedModIds;
    }

    @JsonIgnore
    public void setAllProperties(Map<String, Object> allProperties) {
        defaultModFolder = Path.of((String)allProperties.get("default_mod_folder"));
        chosenModFolder = Path.of((String)allProperties.get("chosen_mod_folder"));
        selectedModificationsChain = (String)allProperties.get("selected_modifications_chain");
    }
}
