package model.persistent.json;

import model.exceptions.UnknownModification;
import model.modifications.BasicModification;
import model.modifications.operations.Operation;
import model.modifications.operations.directory.AbstractDirectoryOperation;
import model.modifications.Modification;
import model.modificationschains.BasicChain;
import model.modificationschains.ModificationsChain;
import model.persistent.json.pojo.JsonConfiguration;
import model.persistent.json.pojo.JsonModificationPojo;
import model.persistent.json.pojo.JsonModificationsChainPojo;
import utilities.reflection.ReflectionApi;
import utilities.reflection.ReflectionApiException;

import java.util.*;
import java.util.stream.Collectors;

public class PojoToAppContainer {

    private static final ReflectionApi reflectionApi = new ReflectionApi(true, false);

    private Map<String, Modification> modifications;
    private Map<String, ModificationsChain> chains;

    public PojoToAppContainer() {
    }

    public void fromPojo(JsonConfiguration jsonConfiguration) throws UnknownModification {
        modifications = new LinkedHashMap<>();
        chains = new LinkedHashMap<>();

        for (var modificationPojo: jsonConfiguration.getModifications()) {
            Modification modification = fromPojoToModification(modificationPojo, jsonConfiguration);
            modifications.put(modification.getId(), modification);
        }

        for (var modificationChain: jsonConfiguration.getModificationsChains()) {
            ModificationsChain chain = fromPojoToChain(modifications, modificationChain);
            chains.put(chain.getId(), chain);
        }
    }

    private Modification fromPojoToModification(JsonModificationPojo pojoModification, JsonConfiguration jsonConfiguration) throws UnknownModification {

        try {
            Operation operation;
            if (pojoModification.getId().equals("MOD_DOWNLOAD_STEAM_WORKSHOP")) {
                operation = (Operation) reflectionApi.instantiateWithParametersFromStringPackageNameClassName(
                        pojoModification.getClazz(),
                        jsonConfiguration.getApp().getManagedModIds()
                );
            } else {
                operation = (Operation) reflectionApi.instantiateFromStringPackageNameClassName(
                        pojoModification.getClazz()
                );
            }

            return new BasicModification(
                    pojoModification.getId(),
                    pojoModification.getName(),
                    operation,
                    Class.forName(pojoModification.getClazz()),
                    pojoModification.getDescription()
            );
        } catch (ReflectionApiException | ClassNotFoundException e) {
            throw new UnknownModification(pojoModification.getId(), pojoModification.getClazz());
        }
    }



    private ModificationsChain fromPojoToChain(Map<String, Modification> modifications, JsonModificationsChainPojo pojoChain) throws UnknownModification {
        ModificationsChain chain = new BasicChain(pojoChain.getId(), pojoChain.getName(), pojoChain.getDescription());

        for (var modificationId: pojoChain.getModificationsIds()) {
            addModificationToChainIfModificationDoesNotExistThrow(modificationId, chain);
        }

        return chain;
    }

    private void addModificationToChainIfModificationDoesNotExistThrow(String modificationId, ModificationsChain chain) throws UnknownModification {
        if (!modifications.containsKey(modificationId)) {
            throw new UnknownModification(modificationId);
        }

        chain.addModification(
                modifications.get(modificationId)
        );
    }

    public List<Modification> getModifications() {
        return new ArrayList<>(modifications.values());
    }

    public void setModifications(List<Modification> modifications) {
        this.modifications.clear();

        modifications.forEach(
                modification -> this.modifications.put(modification.getId(), modification)
        );
    }

    public List<ModificationsChain> getModificationsChains() {
        return chains.values()
                .stream().collect(Collectors.toList());
    }

    public void setModificationsChains(List<ModificationsChain> chains) {
        this.chains.clear();

        chains.forEach(
                chain -> this.chains.put(chain.getId(), chain)
        );
    }

    public List<JsonModificationPojo> getModificationsPojos() {
        return modifications.values().stream()
                .map(this::fromModToPojo)
                .collect(Collectors.toList());
    }

    private JsonModificationPojo fromModToPojo(Modification modification) {
        JsonModificationPojo modificationPojo = new JsonModificationPojo(
                modification.getId(),
                modification.getName(),
                modification.getClazz().toString()
        );

        modificationPojo.setDescription(modification.getDescription());

        return modificationPojo;
    }

    public List<JsonModificationsChainPojo> getModificationsChainsPojos() {
        return chains.values().stream()
                .map(this::fromModChainToPojo)
                .collect(Collectors.toList());
    }

    private JsonModificationsChainPojo fromModChainToPojo(ModificationsChain chain) {
        JsonModificationsChainPojo chainPojo = new JsonModificationsChainPojo(
                chain.getId(),
                chain.getName()
        );

        chainPojo.setDescription(chain.getDescription());


        List<String> modificationIds = chain.getModifications().stream()
                .map(Modification::getId)
                .collect(Collectors.toList());

        chainPojo.setModificationsIds(modificationIds);

        return chainPojo;
    }

}
