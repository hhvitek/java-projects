package app.ini;

import java.io.IOException;

public class InvalidConfigFileFormatException extends IOException {

    /*
     * Because of The serializable class InvalidConfigFileFormatException
     * does not declare a static final serialVersionUID field of type long
     */
    private static final long serialVersionUID = 2948126632313553466L;

    public InvalidConfigFileFormatException(String message) {
        super(message);
    }
}
