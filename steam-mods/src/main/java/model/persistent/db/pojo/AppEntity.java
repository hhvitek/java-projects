package model.persistent.db.pojo;

import javax.persistence.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Table(name = "app")
public class AppEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "default_mod_folder")
    private Path defaultModFolder;

    @Column(name = "chosen_mod_folder")
    private Path chosenModFolder;

    private ModificationsChainEntity selectedModificationsChain;

    @OneToMany(mappedBy = "managed")
    private List<SteamModEntity> managedMods = new ArrayList<>();
}
