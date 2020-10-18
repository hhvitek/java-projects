package model.configuration.json.pojo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import model.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPojoAdditionalProperty {

    private static final Logger logger = LoggerFactory.getLogger(AbstractPojoAdditionalProperty.class);

    @JsonIgnore
    private Map<String, String> additionalProperties = new HashMap<>();

    @JsonAnyGetter
    public String getAdditionalProperty(String name) throws ConfigurationException {
        if (additionalProperties.containsKey(name)) {
            return additionalProperties.get(name);
        } else {
            String errorMessage = "The JSON does NOT contain requested ADDITIONAL key: <" + name + ">. " + this;
            logger.error(errorMessage);
            throw new ConfigurationException(errorMessage);
        }
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, String value) {
        additionalProperties.put(name, value);
    }
}
