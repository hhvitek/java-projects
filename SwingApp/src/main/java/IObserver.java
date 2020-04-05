public interface IObserver {


    void updateObserver(int count, int total, Status status, String message);

    void observableFinished();
}
