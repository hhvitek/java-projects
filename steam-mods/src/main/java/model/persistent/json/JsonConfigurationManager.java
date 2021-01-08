package model.persistent.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import model.exceptions.UnknownModification;
import model.modifications.Modification;
import model.modificationschains.ModificationsChain;
import model.persistent.*;
import model.persistent.exceptions.ConfigurationException;
import model.persistent.exceptions.NotInitializedException;
import model.persistent.json.pojo.JsonAppPojo;
import model.persistent.json.pojo.JsonConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonConfigurationManager implements FileStorageManager {

    private static final Logger logger = LoggerFactory.getLogger(JsonConfigurationManager.class);

    private static final ObjectMapper objectMapper = createDefaultObjectMapper();

    private static ObjectMapper createDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, true);
        defaultObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return defaultObjectMapper;
    }

    private Path jsonConfigurationFile;

    private JsonConfiguration jsonConfiguration;
    private PojoToAppContainer pojoToAppContainer;

    public JsonConfigurationManager(@NotNull Path jsonConfigurationFile) {
        this.jsonConfigurationFile = jsonConfigurationFile;

        pojoToAppContainer = new PojoToAppContainer();
    }

    @Override
    public void load() throws ConfigurationException, IOException{
        try {
            jsonConfiguration = objectMapper.readValue(jsonConfigurationFile.toFile(), JsonConfiguration.class);

            pojoToAppContainer.fromPojo(jsonConfiguration);
        } catch (JsonParseException | JsonMappingException | UnknownModification ex) {
            String errorMessage = "Failed to load Json file: <" + jsonConfigurationFile.toAbsolutePath() + ">." +
                    " With the following error: " + ex.toString();
            logger.error(errorMessage);

            throw new ConfigurationException(errorMessage, ex);
        }
    }

    @Override
    public void load(@NotNull Path configurationFile) throws IOException, ConfigurationException {
        jsonConfigurationFile = configurationFile;
        load();
    }

    private JsonConfiguration convertJsonNodeToAppConfiguration(@NotNull JsonNode jsonNode) throws JsonProcessingException {
        return objectMapper.treeToValue(jsonNode, JsonConfiguration.class);
    }

    private @NotNull JsonNode parseJsonFileToNode(@NotNull Path jsonFile) throws IOException {
        return  objectMapper.readTree(jsonFile.toFile());
    }

    private @NotNull JsonNode parseJsonStringToNode(@NotNull String jsonString) throws JsonProcessingException, JsonMappingException {
        return objectMapper.readTree(jsonString);
    }

    private @NotNull <T> T parseJsonNodeToPojo(@NotNull JsonNode jsonNode, @NotNull Class<T> clazz) throws JsonProcessingException {
        return objectMapper.treeToValue(jsonNode, clazz);
    }

    private @NotNull JsonNode parseJsonPojoToNode(@NotNull Object pojoObject) {
        return objectMapper.valueToTree(pojoObject);
    }

    private @NotNull String parseJsonNodeToString(@NotNull JsonNode jsonNode) throws JsonProcessingException {
        return objectMapper.writeValueAsString(jsonNode);
    }

    @Override
    public void save(@Nullable List<Modification> modifications,
                     @Nullable List<ModificationsChain> chains,
                     @Nullable Map<String, Object> appProperties) throws IOException, ConfigurationException {

        if (modifications != null) {
            pojoToAppContainer.setModifications(modifications);
            jsonConfiguration.setModifications(pojoToAppContainer.getModificationsPojos());
        }

        if (chains != null) {
            pojoToAppContainer.setModificationsChains(chains);
            jsonConfiguration.setModificationsChains(pojoToAppContainer.getModificationsChainsPojos());
        }

        if (appProperties != null) {
            JsonAppPojo appPojo = jsonConfiguration.getApp();
            appPojo.setAllProperties(appProperties);
            jsonConfiguration.setApp(appPojo);
        }

        save(jsonConfigurationFile);
    }

    @Override
    public void save(@NotNull Path configurationFile) throws IOException {
        String json = toJsonString();
        Files.writeString(configurationFile, json);
    }

    public String toJsonString() {
        JsonNode node = parseJsonPojoToNode(jsonConfiguration);
        return node.toPrettyString();
    }

    @Override
    public @NotNull List<Modification> getModifications() throws NotInitializedException {
        ifManagerNotInitializedYetThrowException();

        return pojoToAppContainer.getModifications();
    }

    private void ifManagerNotInitializedYetThrowException() throws NotInitializedException {
        if (jsonConfiguration == null) {
            throw new NotInitializedException("Manager has not been initialized yet. Please call load() method first!");
        }
    }

    @Override
    public @NotNull List<ModificationsChain> getModificationsChains() throws NotInitializedException {
        ifManagerNotInitializedYetThrowException();

        return pojoToAppContainer.getModificationsChains();
    }

    @Override
    public @NotNull Map<String, Object> getAppProperties() throws NotInitializedException {
        ifManagerNotInitializedYetThrowException();

        JsonAppPojo appPojo = jsonConfiguration.getApp();

        Map<String, String> appAdditionalProperties = appPojo.getAdditionalProperties();
        appAdditionalProperties.put("default_mod_folder", appPojo.getDefaultModFolder().toString());

        return appAdditionalProperties;
    }

    @Override
    public @NotNull Path getDefaultModFolder() throws NotInitializedException {
        return jsonConfiguration.getApp().getDefaultModFolder();
    }


}
