package db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "collection_of_suffixes_collections", schema = "main", catalog = "")
public class CollectionOfSuffixesCollectionsEntity {
    private Short id;

    @Id
    @Column(name = "id")
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionOfSuffixesCollectionsEntity that = (CollectionOfSuffixesCollectionsEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
