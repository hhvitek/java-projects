package app.ini.custom.loaders.chain.processors;

import app.ini.custom.loaders.chain.IContextState;

public abstract class AbstractProcessor {

    protected AbstractProcessor nextProcessor;

    public AbstractProcessor(AbstractProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    public abstract void processLine(IContextState context, String line);

}
