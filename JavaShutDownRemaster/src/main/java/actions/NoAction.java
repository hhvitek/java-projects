package actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoAction extends ActionAbstract {

    private static final Logger logger = LoggerFactory.getLogger(NoAction.class);

    public NoAction() {
        super();

        name = "NoAction";
        description = "NoAction is just example implementation";

    }



}
