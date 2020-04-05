package actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShutDownAction implements IAction {

    private static final Logger logger = LoggerFactory.getLogger(ShutDownAction.class);

    @Override
    public void execute() {
        logger.info("Executing shutdown action!!!");
    }
}
