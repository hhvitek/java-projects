package app.springthyme.model;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class LongRunningTaskService implements ITaskService {

    private int maxIterations = 0;
    private int delaySeconds = 1;
    private volatile boolean running = false;
    private volatile boolean stopped = false;

    public LongRunningTaskService() {
    }

    @Override
    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    @Override
    public void setDelaySeconds(int delaySeconds) {
        this.delaySeconds = delaySeconds;
    }

    @Override
    @Async
    public void startTask() {

        if (!running) {
            running = true;
            stopped = false;
            task(maxIterations, delaySeconds);
            running = false;
        } else { // already running
            System.err.println("Task is running.");
        }
    }

    @Override
    public void stopTask() {
         stopped = true;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    private void task(int maxIteration, int delaySeconds) {

        System.out.println("Task is starting: " + maxIteration + " iters, " + delaySeconds + "s.");

        for(int currentIteration = 1; currentIteration <= maxIteration; currentIteration++) {
            System.out.println("sleeping: <" + currentIteration + "/" + maxIteration + ">");
            try {
                Thread.sleep(delaySeconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
            }

            if (stopped) {
                break;
            }
        }
        System.out.print("Task has finished.");
    }


}
