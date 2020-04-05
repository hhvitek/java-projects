package model.sql.jpa_hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "scheduled_action_parameter", schema = "main", catalog = "")
public class ScheduledActionParameterEntity {
    private Short id;
    private short scheduledActionId;
    private String value;
    private ScheduledActionEntity scheduledActionByScheduledActionId;

    @Id
    @Column(name = "id")
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    @Basic
    @Column(name = "scheduled_action_id")
    public short getScheduledActionId() {
        return scheduledActionId;
    }

    public void setScheduledActionId(short scheduledActionId) {
        this.scheduledActionId = scheduledActionId;
    }

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduledActionParameterEntity that = (ScheduledActionParameterEntity) o;
        return scheduledActionId == that.scheduledActionId &&
                Objects.equals(id, that.id) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scheduledActionId, value);
    }

    @ManyToOne
    @JoinColumn(name = "scheduled_action_id", referencedColumnName = "id", nullable = false)
    public ScheduledActionEntity getScheduledActionByScheduledActionId() {
        return scheduledActionByScheduledActionId;
    }

    public void setScheduledActionByScheduledActionId(ScheduledActionEntity scheduledActionByScheduledActionId) {
        this.scheduledActionByScheduledActionId = scheduledActionByScheduledActionId;
    }
}
