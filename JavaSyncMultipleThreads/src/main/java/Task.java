import utilities.timer.MyTimer;
import utilities.timer.MyTimerUtilImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Task {

    private MyTimer timerForIteration;
    private MyTimer timerForModification;

    private List<Integer> list;
    private int currentValue = 0;


    public Task() {
        list = new ArrayList<>();
        /* this just decorated all methods, but
           * doesn't ensure iteration thread safety
           * simply cannot synchronize semantic logic of application
         Collections.synchronizedList(new ArrayList());
        */

        timerForIteration = new MyTimerUtilImpl();
        timerForModification = new MyTimerUtilImpl();
    }

    public void start() {
        timerForIteration.scheduleAtFixedRate(this::taskIteration, 1000);
        timerForModification.scheduleAtFixedRate(this::taskModification, 1000);
    }

    private synchronized void taskIteration() {
        System.out.print("Current state using standard for-cycle: ");
        for (Integer value: list) {
            System.out.print(value + " ");
        }
        System.out.println();

        System.out.print("Current state using forEach method: ");
        list.forEach(
                value -> System.out.print(value + " ")
        );
        System.out.println();
    }

    private synchronized void taskModification() {
        int listSize = list.size();
        if (listSize > 0) {
            System.out.println("Removing index: " + (listSize - 1));
            list.remove(listSize - 1);
        }

        list.add(currentValue++);
        list.add(currentValue++);
    }
}
