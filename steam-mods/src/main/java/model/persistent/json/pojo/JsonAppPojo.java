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

    @JsonProperty("default_mod_folder")
    private Path defaultModFolder;
    @JsonProperty("chosen_mod_folder")
    private Path chosenModFolder;
    @JsonProperty("selected_modifications_chain")
    private String selectedModificationsChain;
    @JsonProperty("managed_mod_ids")
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
    public void setAllProperties(Map<String, String> allProperties) {
        defaultModFolder = Path.of(allProperties.get("default_mod_folder"));
        chosenModFolder = Path.of(allProperties.get("chosen_mod_folder"));
        selectedModificationsChain = allProperties.get("selected_modifications_chain");
    }
}
