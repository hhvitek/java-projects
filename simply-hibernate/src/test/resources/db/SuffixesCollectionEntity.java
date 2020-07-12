package db;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "suffixes_collection", schema = "main", catalog = "")
public class SuffixesCollectionEntity {
    private String name;
    private String suffixes;

    @Id
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "suffixes")
    public String getSuffixes() {
        return suffixes;
    }

    public void setSuffixes(String suffixes) {
        this.suffixes = suffixes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuffixesCollectionEntity that = (SuffixesCollectionEntity) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(suffixes, that.suffixes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, suffixes);
    }
}
