package actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShutDownAction extends ActionAbstract {

    private static final Logger logger = LoggerFactory.getLogger(ShutDownAction.class);

    public ShutDownAction() {
        super();

        name = "ShutDown";
        description = "This will turn this computer off.";
    }

    @Override
    public String executeAction() {
        logger.info("Execution shutdown Action");
        return null;
    }


}
