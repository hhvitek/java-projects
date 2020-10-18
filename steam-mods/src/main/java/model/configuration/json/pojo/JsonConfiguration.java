package model.configuration.json.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "app",
        "modifications",
        "modifications-chains"
})
public class JsonConfiguration {

    @JsonProperty("app")
    private JsonAppPojo app = null;
    @JsonProperty("modifications")
    private List<JsonModificationPojo> modifications = null;
    @JsonProperty("modifications-chains")
    private List<JsonModificationsChainPojo> modificationsChains = null;

    @JsonProperty("app")
    public JsonAppPojo getApp() {
        return app;
    }

    @JsonProperty("app")
    public void setApp(JsonAppPojo app) {
        this.app = app;
    }

    @JsonProperty("modifications")
    public List<JsonModificationPojo> getModifications() {
        return modifications;
    }

    @JsonProperty("modifications")
    public void setModifications(List<JsonModificationPojo> modifications) {
        this.modifications = modifications;
    }

    @JsonProperty("modifications-chains")
    public List<JsonModificationsChainPojo> getModificationsChains() {
        return modificationsChains;
    }

    @JsonProperty("modifications-chains")
    public void setModificationsChains(List<JsonModificationsChainPojo> modificationsChains) {
        this.modificationsChains = modificationsChains;
    }

}
