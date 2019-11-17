import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorkerLibrary implements IObservable {


    private final List<IObserver> observers = new ArrayList<>();

    private int    countNo = 0;
    private int    totalNo = 0;
    private Status status  = Status.Ok;
    private String message = "";

    private volatile boolean cancelled       = false;
    private          Random  randomGenerator = new Random();

    public void startWorker(int cyclesNo) {
        init();

        totalNo = cyclesNo;
        for (countNo = 1; countNo < cyclesNo; countNo++) {
            if (cancelled) {
                status = Status.Cancelled;
                message = "Computing has been cancelled";
                notifyObservers();
                done();
                return;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            status = generateStatus();
            notifyObservers();
            if (status == Status.Error) {
                return;
            }
        }

        status = Status.Finished;
        message = "Finished Successfully";
        notifyObservers();
        done();
    }

    private void init() {
        cancelled = false;
        status = Status.Ok;
        message = "";
    }

    private Status generateStatus() {

        int generatedNo = randomGenerator.nextInt(100);

        if (generatedNo > 2) {
            message = "Running";
            return Status.Ok;
        } else {
            message = "Error encountered";
            return Status.Error;
        }
    }


    @Override
    public void attachObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detachObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void stop() {
        cancelled = true;
    }

    @Override
    public void notifyObservers() {
        observers.forEach(
                x -> x.updateObserver(countNo, totalNo, status, message)
        );
    }

    @Override
    public void done() {
        System.out.println("Observable: finished()");


        observers.forEach(
                x -> x.observableFinished()
        );
    }
}
