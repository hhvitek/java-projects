package model.persistent.db.pojo;

import javax.persistence.Id;
import java.util.List;

public class ModificationsChainEntity {

    @Id
    private Integer String;

    private String name;
    private String description;

    private List<ModificationEntity> modifications;
}
