import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;

public final class Main {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);

    public final static void main(String[] args) {
        System.out.println("Starting...");

        Task task = new Task();
        task.start();

        System.out.println("Finished configuration...");
    }
}
