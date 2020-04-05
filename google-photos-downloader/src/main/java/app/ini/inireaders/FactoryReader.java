package app.ini.inireaders;

import app.ini.inireaders.chain.ChainOfResponsibilityIniReader;
import app.ini.inireaders.fsm.LineIniReader;

public final class FactoryReader {

    public enum IniReaders {
        Fsm,
        Chain
    }

    private FactoryReader() {
    }

    public static IIniReader getIniReader(IniReaders type) {
        switch (type) {
            case Fsm:
                return new LineIniReader();
            case Chain:
                return new ChainOfResponsibilityIniReader();
            default:
                throw new UnsupportedOperationException("Unknown parameter: " + type);
        }
    }

    public static IIniReader getDefaultIniReader() {
        return new LineIniReader();
    }

}
