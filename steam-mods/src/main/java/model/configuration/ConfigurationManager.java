package model.configuration;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Interface representing how to load app's configuration.
 *
 * Simple method returning interface of AppConfiguration
 */
public interface ConfigurationManager {
    void load() throws ConfigurationException, IOException;

    void save() throws ConfigurationException, IOException;

    String toJsonString();

    @NotNull List<ModificationsConfiguration> getModifications() throws NotInitializedException;

    @NotNull List<ModificationsChainConfiguration> getModificationsChain() throws NotInitializedException;

    @NotNull AppConfiguration getAppConfiguration() throws NotInitializedException;
}
