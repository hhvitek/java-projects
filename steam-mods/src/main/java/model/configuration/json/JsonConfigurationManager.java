package model.configuration.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.*;
import model.configuration.*;
import model.configuration.json.pojo.JsonConfiguration;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class JsonConfigurationManager implements FileConfigurationManager {

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

    public JsonConfigurationManager(@NotNull Path jsonConfigurationFile) {
        this.jsonConfigurationFile = jsonConfigurationFile;
    }

    @Override
    public void load() throws ConfigurationException, IOException{
        try {
            jsonConfiguration = objectMapper.readValue(jsonConfigurationFile.toFile(), JsonConfiguration.class);
        } catch (JsonParseException | JsonMappingException ex) {
            String errorMessage = "Failed to load Json file: <" + jsonConfigurationFile.toAbsolutePath() + ">." +
                    " With the following error: " + ex.getMessage();
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
    public void save() throws ConfigurationException, IOException {
        save(jsonConfigurationFile);
    }

    @Override
    public void save(@NotNull Path configurationFile) throws IOException {
        String json = toJsonString();
        Files.writeString(configurationFile, json);
    }

    @Override
    public String toJsonString() {
        JsonNode node = parseJsonPojoToNode(jsonConfiguration);
        return node.toPrettyString();
    }

    @Override
    public @NotNull List<ModificationsConfiguration> getModifications() throws NotInitializedException {
        ifManagerNotInitializedYetThrowException();

        return jsonConfiguration.getModifications()
                .stream()
                .collect(Collectors.toList());
    }

    private void ifManagerNotInitializedYetThrowException() throws NotInitializedException {
        if (jsonConfiguration == null) {
            throw new NotInitializedException("Manager has not been initialized yet. Please call load() method first!");
        }
    }

    @Override
    public @NotNull List<ModificationsChainConfiguration> getModificationsChain() throws NotInitializedException {
        ifManagerNotInitializedYetThrowException();

        return jsonConfiguration.getModificationsChains()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public @NotNull AppConfiguration getAppConfiguration() throws NotInitializedException {
        ifManagerNotInitializedYetThrowException();

        return jsonConfiguration.getApp();
    }




}
