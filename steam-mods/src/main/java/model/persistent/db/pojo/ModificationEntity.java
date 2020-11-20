package model.persistent.db.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class ModificationEntity {

    @Id
    private Integer String;

    private String name;
    private String description;

    @Column(name = "class", nullable = false)
    private String clazz;
}
