package model.sql.jpa_hibernate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "scheduled_action_status_enum", schema = "main", catalog = "")
public class ScheduledActionStatusEnumEntity {
    private String status;
    private Collection<ScheduledActionEntity> scheduledActionsByStatus;

    @Id
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduledActionStatusEnumEntity that = (ScheduledActionStatusEnumEntity) o;
        return Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }

    @OneToMany(mappedBy = "scheduledActionStatusEnumByStatus")
    public Collection<ScheduledActionEntity> getScheduledActionsByStatus() {
        return scheduledActionsByStatus;
    }

    public void setScheduledActionsByStatus(Collection<ScheduledActionEntity> scheduledActionsByStatus) {
        this.scheduledActionsByStatus = scheduledActionsByStatus;
    }
}
