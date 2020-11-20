package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "modification_in_chain_mapping")
public class ModificationInChainMappingEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "modification_id", nullable = false)
    private String modificationId;

    @Column(name = "chain_id", nullable = false)
    private String chainId;

    @Column(name = "modification_order", nullable = false)
    private Integer modificationOrder;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModificationId() {
        return modificationId;
    }

    public void setModificationId(String modificationId) {
        this.modificationId = modificationId;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public Integer getModificationOrder() {
        return modificationOrder;
    }

    public void setModificationOrder(Integer modificationOrder) {
        this.modificationOrder = modificationOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModificationInChainMappingEntity that = (ModificationInChainMappingEntity) o;
        return Objects.equals(id, that.id) &&
                modificationId.equals(that.modificationId) &&
                chainId.equals(that.chainId) &&
                modificationOrder.equals(that.modificationOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, modificationId, chainId, modificationOrder);
    }
}
