package actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoAction extends ActionAbstract {

    private static final Logger logger = LoggerFactory.getLogger(NoAction.class);

    private static NoAction actionSingleton;

    private NoAction() {
        super();

        name = "NoAction";
        description = "NoAction is just example implementation";
    }

    public static NoAction getInstance() {
        if (actionSingleton == null) {
            actionSingleton = new NoAction();
        }
        return actionSingleton;
    }


}
