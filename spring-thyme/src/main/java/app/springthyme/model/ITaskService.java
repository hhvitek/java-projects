package app.springthyme.model;

public interface ITaskService  {

    void setMaxIterations(int iterations);
    void setDelaySeconds(int seconds);
    void startTask();
    void stopTask();
    boolean isRunning();
}
