package model.configuration;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ModificationsChainConfiguration {
    @NotNull String getId();
    @NotNull String getName();
    @NotNull List<String> getModificationsIds();
}
