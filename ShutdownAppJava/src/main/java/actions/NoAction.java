package actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoAction implements IAction {

    private static final Logger logger = LoggerFactory.getLogger(NoAction.class);
    @Override
    public void execute() {
        logger.info("NoAction invoked!");
    }
}
