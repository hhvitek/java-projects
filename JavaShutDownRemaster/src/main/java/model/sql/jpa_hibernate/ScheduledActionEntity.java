package model.sql.jpa_hibernate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "scheduled_action", schema = "main", catalog = "")
public class ScheduledActionEntity {
    private Short id;
    private short actionId;
    private String goalTime;
    private String result;
    private String status;
    private Object createdTime;
    private Object lastModifiedTime;
    private ActionEntity actionByActionId;
    private ScheduledActionStatusEnumEntity scheduledActionStatusEnumByStatus;
    private Collection<ScheduledActionParameterEntity> scheduledActionParametersById;

    @Id
    @Column(name = "id")
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    @Basic
    @Column(name = "action_id")
    public short getActionId() {
        return actionId;
    }

    public void setActionId(short actionId) {
        this.actionId = actionId;
    }

    @Basic
    @Column(name = "goal_time")
    public String getGoalTime() {
        return goalTime;
    }

    public void setGoalTime(String goalTime) {
        this.goalTime = goalTime;
    }

    @Basic
    @Column(name = "result")
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "created_time")
    public Object getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Object createdTime) {
        this.createdTime = createdTime;
    }

    @Basic
    @Column(name = "last_modified_time")
    public Object getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Object lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduledActionEntity that = (ScheduledActionEntity) o;
        return actionId == that.actionId &&
                Objects.equals(id, that.id) &&
                Objects.equals(goalTime, that.goalTime) &&
                Objects.equals(result, that.result) &&
                Objects.equals(status, that.status) &&
                Objects.equals(createdTime, that.createdTime) &&
                Objects.equals(lastModifiedTime, that.lastModifiedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, actionId, goalTime, result, status, createdTime, lastModifiedTime);
    }

    @ManyToOne
    @JoinColumn(name = "action_id", referencedColumnName = "id", nullable = false)
    public ActionEntity getActionByActionId() {
        return actionByActionId;
    }

    public void setActionByActionId(ActionEntity actionByActionId) {
        this.actionByActionId = actionByActionId;
    }

    @ManyToOne
    @JoinColumn(name = "status", referencedColumnName = "status", nullable = false)
    public ScheduledActionStatusEnumEntity getScheduledActionStatusEnumByStatus() {
        return scheduledActionStatusEnumByStatus;
    }

    public void setScheduledActionStatusEnumByStatus(ScheduledActionStatusEnumEntity scheduledActionStatusEnumByStatus) {
        this.scheduledActionStatusEnumByStatus = scheduledActionStatusEnumByStatus;
    }

    @OneToMany(mappedBy = "scheduledActionByScheduledActionId")
    public Collection<ScheduledActionParameterEntity> getScheduledActionParametersById() {
        return scheduledActionParametersById;
    }

    public void setScheduledActionParametersById(Collection<ScheduledActionParameterEntity> scheduledActionParametersById) {
        this.scheduledActionParametersById = scheduledActionParametersById;
    }
}
