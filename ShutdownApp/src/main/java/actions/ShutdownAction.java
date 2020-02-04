package actions;

import java.io.IOException;

public class ShutdownAction implements IAction {

    public ShutdownAction() {
    }

    @Override
    public void performAction() {
        System.out.println("SHUTDOWN ACTION TRIGGERED");
        Runtime runtime = Runtime.getRuntime();
        try {
            Process proc = runtime.exec("shutdown -s -t 0");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
