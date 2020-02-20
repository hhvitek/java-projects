package model;


import actions.ActionAbstract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class ScheduledAction {

    private ActionAbstract action;
    private int uniqueId = Integer.MIN_VALUE;

    private LocalDateTime goalTime;
    private String result = "";
    private String status = "";
    private List<String> parameters = Collections.emptyList();

    private ScheduledAction() {

    }

    public ScheduledAction(@NotNull ActionAbstract action, @NotNull LocalDateTime goalTime) {
        this.action = action;
        this.goalTime = goalTime;
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

    public LocalDateTime getGoalTime() {
        return goalTime;
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

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(@NotNull List<String> parameters) {
        this.parameters = parameters;
    }

    public String getGoalTimeAsFormattedString(@Nullable String aformat) throws IllegalArgumentException {

        String theformat = aformat;
        String defaultFormat = "yyyy-MM-dd HH:mm:ss";
        if (theformat == null) {
            theformat = defaultFormat;
        }

        return goalTime.format(DateTimeFormatter.ofPattern(theformat));
    }

    public String getRemainingTimeAsHHmmssString()  {
        Duration remainingTime = computeRemainingTime();

        return String.format("%02d:%02d:%02d",
                remainingTime.toHoursPart(),
                remainingTime.toMinutesPart(),
                remainingTime.toSecondsPart());
    }

    public Duration computeRemainingTime() {
        Duration d = Duration.between(LocalDateTime.now(), goalTime);
        if (d.isNegative()) {
            return Duration.ofSeconds(0);
        }
        return d;
    }
}
