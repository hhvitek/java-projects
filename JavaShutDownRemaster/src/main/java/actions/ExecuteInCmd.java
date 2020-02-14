package actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecuteInCmd extends ActionAbstract {

    private static final Logger logger = LoggerFactory.getLogger(ExecuteInCmd.class);

    private static final ExecuteInCmd actionSingleton = new ExecuteInCmd();

    public ExecuteInCmd() {
        super();

        name = "ExecuteInCmd";
        description = "ExecuteInCmd will execute the taken parameter in system's command line";
        parametersCount = 1;
        isProducingResult = true;
    }

    public static ExecuteInCmd getInstance() {
        return actionSingleton;
    }
}
