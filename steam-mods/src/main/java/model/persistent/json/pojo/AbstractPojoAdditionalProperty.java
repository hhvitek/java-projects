package model.persistent.json.pojo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import model.persistent.exceptions.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractPojoAdditionalProperty {

    private static final Logger logger = LoggerFactory.getLogger(AbstractPojoAdditionalProperty.class);

    @JsonIgnore
    protected Map<String, String> additionalProperties = new LinkedHashMap<>();

    @JsonAnyGetter
    public Map<String, String> getAdditionalProperties() {
        return (additionalProperties);
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, String value) {
        additionalProperties.put(name, value);
    }

}
