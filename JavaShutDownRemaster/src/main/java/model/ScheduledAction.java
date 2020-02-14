package model;


import actions.ActionAbstract;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduledAction {

    private ActionAbstract action;
    private int uniqueId;

    private LocalDateTime goalTime;
    private String result;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime lastModifiedTime;
    private List<String> parameters;

    private ScheduledAction() {

    }

    public ScheduledAction(ActionAbstract action, String goalTime) {
        this.action = action;
        //this.goalTime = goalTime;
    }

    public ActionAbstract getAction() {
        return action;
    }

    public int getId() {
        return uniqueId;
    }

    public void setId(int id) {
        this.uniqueId = id;
    }

    public void setAction(ActionAbstract action) {
        this.action = action;
    }

    public LocalDateTime getGoalTime() {
        return goalTime;
    }

    public void setGoalTime(LocalDateTime goalTime) {
        this.goalTime = goalTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }
}
