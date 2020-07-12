package jpa.entities;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "suffixes_collection")
public class SuffixesCollectionEntity {

    @Id
    private String name;

    @Column(name = "suffixes")
    private String suffixes;

    @ManyToMany(mappedBy = "suffixesCollections")
    private List<CollectionOfSuffixesCollectionsEntity> belongingToCollections = new ArrayList<>();

    SuffixesCollectionEntity() {

    }

    public SuffixesCollectionEntity(@NotNull String name, @NotNull String suffixes) {
        this.name = name;
        this.suffixes = suffixes;
    }

    public String getName() {
        return name;
    }

    public String getSuffixes() {
        return suffixes;
    }

    public void setSuffixes(String suffixes) {
        this.suffixes = suffixes;
    }

    public List<CollectionOfSuffixesCollectionsEntity> getBelongingToCollections() {
        return belongingToCollections;
    }

    public void setBelongingToCollections(List<CollectionOfSuffixesCollectionsEntity> belongingToCollections) {
        this.belongingToCollections = belongingToCollections;
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
