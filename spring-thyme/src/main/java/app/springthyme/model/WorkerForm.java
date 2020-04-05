package app.springthyme.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class WorkerForm {

    @NotNull
    @Min(1)
    @Max(10)
    private Integer seconds = 2;

    @NotNull
    @Min(1)
    @Max(10)
    private Integer iterations = 5;

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public Integer getIterations() {
        return iterations;
    }

    public void setIterations(Integer iterations) {
        this.iterations = iterations;
    }

}
