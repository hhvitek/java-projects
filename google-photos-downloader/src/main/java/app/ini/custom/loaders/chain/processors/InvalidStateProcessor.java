package app.ini.custom.loaders.chain.processors;

import app.ini.custom.loaders.chain.IContextState;

public class InvalidStateProcessor extends AbstractProcessor {

    public InvalidStateProcessor(AbstractProcessor nextProcessor) {
        super(null);
    }

    @Override
    public void processLine(IContextState context, String line) {
        context.setErrorMessage("Unknown line format: " + line);
    }

}
