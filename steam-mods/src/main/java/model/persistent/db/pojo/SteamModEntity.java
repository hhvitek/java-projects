package model.persistent.db.pojo;

import javax.persistence.*;

public class SteamModEntity {

    @Id
    private Integer String;

    private String name;
    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column(name = "managed", columnDefinition = "" +
            "BOOLEAN CONSTRAINT ONLY_BOOLEAN CHECK ( (managed IN (0, 1) ) ) \n" +
            "NOT NULL\n" +
            "CONSTRAINT FALSE DEFAULT (0) ")
    private Boolean managed;
}
