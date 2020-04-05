package actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShutDownAction extends ActionAbstract {

    private static final Logger logger = LoggerFactory.getLogger(ShutDownAction.class);

    private static ShutDownAction actionSingleton;

    private ShutDownAction() {
        super();

        name = "ShutDown";
        description = "This will turn this computer off.";
    }

    public static ShutDownAction getInstance() {
        if (actionSingleton == null) {
            actionSingleton = new ShutDownAction();
        }
        return actionSingleton;
    }

    @Override
    public String executeAction() {
        logger.info("Execution shutdown Action");
        return null;
    }


}
