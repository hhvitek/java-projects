package actions;

import java.util.concurrent.Future;

/**
 * An action to be executed.
 */
public abstract class ActionAbstract {

    protected String name;
    protected String description;
    protected int parametersCount = 0;
    protected boolean isProducingResult = false;

    protected ActionAbstract() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean acceptParameter() {
        return parametersCount > 0;
    }

    public int parametersCount() {
        return parametersCount;
    }

    public boolean isProducingResult() {
        return isProducingResult;
    }

    public String executeAction() {
        throw new UnsupportedOperationException();
    }

    public String executeAction(String parameter) {
        throw new UnsupportedOperationException();
    }

    public String executeAction(String[] parameters) {
        throw new UnsupportedOperationException();
    }

    public Future<String> executeActionAsync() {
        throw new UnsupportedOperationException();
    }
    public Future<String> executeActionAsync(String parameter) {
        throw new UnsupportedOperationException();
    }

    public Future<String> executeActionAsync(String[] parameters) {
        throw new UnsupportedOperationException();
    }
}
