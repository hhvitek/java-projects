package entities;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "app")
public class AppEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "default_mod_folder")
    private String defaultModFolder;

    @Column(name = "chosen_mod_folder")
    private String chosenModFolder;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_selected_chain_id", referencedColumnName = "id")
    private ModificationsChainEntity selectedModificationsChain;

    @OneToMany(
            mappedBy = "app",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SteamModEntity> managedMods = new ArrayList<>();

    public AppEntity() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDefaultModFolder() {
        return defaultModFolder;
    }

    public void setDefaultModFolder(Path defaultModFolder) {
        this.defaultModFolder = defaultModFolder.toAbsolutePath().toString();
    }

    public String getChosenModFolder() {
        return chosenModFolder;
    }

    public void setChosenModFolder(Path chosenModFolder) {
        this.chosenModFolder = chosenModFolder.toAbsolutePath().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppEntity appEntity = (AppEntity) o;
        return Objects.equals(id, appEntity.id) &&
                Objects.equals(defaultModFolder, appEntity.defaultModFolder) &&
                Objects.equals(chosenModFolder, appEntity.chosenModFolder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, defaultModFolder, chosenModFolder);
    }
}
