package entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "modification")
public class ModificationEntity {

    @Id
    private String id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "class", nullable = false)
    private String clazz;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "modification_in_chain_mapping",
            joinColumns = @JoinColumn(name = "modification_id"),
            inverseJoinColumns = @JoinColumn(name = "chain_id")
    )
    private List<ModificationsChainEntity> chainsMembership;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModificationEntity that = (ModificationEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(clazz, that.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, clazz);
    }
}
