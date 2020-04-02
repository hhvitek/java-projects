package Model.SimpleModel;

import java.nio.file.Path;
import java.nio.file.Paths;

public class DefaultModelData {

    private static String DEFAULT_AUDIO_EXTENSION = "mp3,m4a,mpa,ogg,wma,aac,wav,flac,aiff,alac";
    private static String DEFAULT_OUTPUT_FOLDER_PART = "OUTPUT";

    /**
     * Returns Current Working Directory
     * @return
     */
    public Path getDefaultInputFolder() {
        return Path.of("C:\\Libraries\\VirtualBox\\Share\\TEST");
        //return Paths.get("").toAbsolutePath();
    }

    /**
     * Returns Current Working Directory + appended "/OUTPUT"
     * @return
     */
    public Path getDefaultOutputFolder() {
        return getDefaultInputFolder().resolve(DEFAULT_OUTPUT_FOLDER_PART);
    }

    public String getDefaultAudioExtensions() {
        return DEFAULT_AUDIO_EXTENSION;
    }
}
