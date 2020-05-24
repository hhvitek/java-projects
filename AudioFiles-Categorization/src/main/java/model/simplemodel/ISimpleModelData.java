package model.simplemodel;

import java.nio.file.Path;
import java.util.List;

public interface ISimpleModelData {
    Path getInputFolder();
    void setInputFolder(Path inputFolder);

    Path getOutputFolder();
    public void setOutputFolder(Path outputFolder);

    String getAudioExtensions();
    List<String> getAudioExtensionsAsList();
    void setAudioExtensions(String audioExtensions);

    int getNumberOfAudioFiles();
    void setNumberOfAudioFiles(int count);

    int getNumberOfAudioFilesRemaining();
    void setNumberOfAudioFilesRemaining(int count);

    void audioFileProcessed();

    String validate();
}
