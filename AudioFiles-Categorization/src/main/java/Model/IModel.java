package Model;

import java.nio.file.Path;

public interface IModel extends IModelActiveData {
    Path getDefaultInputFolder();
    Path getDefaultOutputFolder();
    String getDefaultAudioExtensions();

    void setInputFolder(Path inputFolder);
    Path getInputFolder();
    void setOutputFolder(Path outputFolder);
    Path getOutputFolder();
    void setAudioExtensions(String audioExtensions);
    String getAudioExtensions();

    int countRelevantAudioFiles();
    String validate();

    void performRenameInPlace();
    void performCopyToOutputFolder();
    void cancel();
}
