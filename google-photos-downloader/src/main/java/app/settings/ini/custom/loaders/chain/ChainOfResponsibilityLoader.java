package app.settings.ini.custom.loaders.chain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import app.settings.ini.IIniConfig;
import app.settings.ini.InvalidConfigFileFormatException;
import app.settings.ini.custom.loaders.ILoader;

/**
 * For the detailed description of configuration file see:
 * {@link ILoader}
 *
 * This is the concrete implementation of the above interface. It uses Chain-Of-Responsibility
 * design pattern to avoid if-else statements. The input line is passed to chain of
 * processors (implementation of AbstractProcessor). If the processor recognize the line,
 * it processes/swallows the line, otherwise it passes the line to the next Processor in the chain.
 *
 * The stateful logic is still spread across all the methods...
 *
 * @author vitek
 *
 *
 *
 */
public class ChainOfResponsibilityLoader implements ILoader {

    private final ProcessorChain processorChain = new ProcessorChain();

    private IIniConfig ini;

    private IContextState contextState;

    @Override
    public void load(IIniConfig ini, File file)
            throws IOException, InvalidConfigFileFormatException {

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            load(ini, reader);
        }

    }

    @Override
    public void load(IIniConfig ini, BufferedReader reader)
            throws IOException, InvalidConfigFileFormatException {

        this.ini = ini;
        contextState = new ContextState(ini);

        String line = null;
        while ((line = reader.readLine()) != null) {
            processorChain.processLine(contextState, line);
            if (contextState.isError()) {
                throw new InvalidConfigFileFormatException(contextState.getErrorMessage());
            }
        }
    }

}
