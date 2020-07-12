package jpa.entities;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "collection_of_suffixes_collections")
public class CollectionOfSuffixesCollectionsEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name", nullable = true, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "collections_to_suffixes_collections_mapping",
            joinColumns = @JoinColumn(name = "collection_id"),
            inverseJoinColumns = @JoinColumn(name = "suffixes_name")
    )
    List<SuffixesCollectionEntity> suffixesCollections = new ArrayList<>();

    CollectionOfSuffixesCollectionsEntity() {

    }

    public CollectionOfSuffixesCollectionsEntity(@NotNull String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public List<SuffixesCollectionEntity> getSuffixesCollections() {
        return suffixesCollections;
    }

    public void setSuffixesCollections(List<SuffixesCollectionEntity> suffixesCollections) {
        this.suffixesCollections = suffixesCollections;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final CollectionOfSuffixesCollectionsEntity that = (CollectionOfSuffixesCollectionsEntity) o;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }
}
