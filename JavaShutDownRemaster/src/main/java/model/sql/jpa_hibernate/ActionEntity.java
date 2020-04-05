package model.sql.jpa_hibernate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "action", schema = "main", catalog = "")
public class ActionEntity {
    private Short id;
    private String name;
    private String className;
    private String description;
    private short parametersCount;
    private Object isProducingResult;
    private Collection<ScheduledActionEntity> scheduledActionsById;

    @Id
    @Column(name = "id")
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "class_name")
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "parameters_count")
    public short getParametersCount() {
        return parametersCount;
    }

    public void setParametersCount(short parametersCount) {
        this.parametersCount = parametersCount;
    }

    @Basic
    @Column(name = "is_producing_result")
    public Object getIsProducingResult() {
        return isProducingResult;
    }

    public void setIsProducingResult(Object isProducingResult) {
        this.isProducingResult = isProducingResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionEntity that = (ActionEntity) o;
        return parametersCount == that.parametersCount &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(className, that.className) &&
                Objects.equals(description, that.description) &&
                Objects.equals(isProducingResult, that.isProducingResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, className, description, parametersCount, isProducingResult);
    }

    @OneToMany(mappedBy = "actionByActionId")
    public Collection<ScheduledActionEntity> getScheduledActionsById() {
        return scheduledActionsById;
    }

    public void setScheduledActionsById(Collection<ScheduledActionEntity> scheduledActionsById) {
        this.scheduledActionsById = scheduledActionsById;
    }
}
