package Model.SimpleModel;

import Model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class SimpleModel implements IModel {

    private static final Logger logger = LoggerFactory.getLogger(SimpleModel.class);
    private final DefaultModelData defaultModelData = new DefaultModelData();
    private final ISimpleModelData currentModelData = new CurrentModelData();

    public SimpleModel() {
        currentModelData.setInputFolder(
                defaultModelData.getDefaultInputFolder()
        );
        currentModelData.setOutputFolder(
                defaultModelData.getDefaultOutputFolder()
        );
        currentModelData.setAudioExtensions(
                defaultModelData.getDefaultAudioExtensions()
        );
    }

    @Override
    public Path getDefaultInputFolder() {
        return defaultModelData.getDefaultInputFolder();
    }

    @Override
    public Path getDefaultOutputFolder() {
        return defaultModelData.getDefaultOutputFolder();
    }

    @Override
    public String getDefaultAudioExtensions() {
        return defaultModelData.getDefaultAudioExtensions();
    }

    @Override
    public void setInputFolder(Path inputFolder) {
        currentModelData.setInputFolder(inputFolder);
    }

    @Override
    public Path getInputFolder() {
        return currentModelData.getInputFolder();
    }

    @Override
    public void setOutputFolder(Path outputFolder) {
        currentModelData.setOutputFolder(outputFolder);
    }

    @Override
    public Path getOutputFolder() {
        return currentModelData.getOutputFolder();
    }

    @Override
    public void setAudioExtensions(String audioExtensions) {
        currentModelData.setAudioExtensions(audioExtensions);
    }

    @Override
    public String getAudioExtensions() {
        return currentModelData.getAudioExtensions();
    }

    @Override
    public int countRelevantAudioFiles() {
        FindFilesBy findFiles = new FindFilesBy(currentModelData.getInputFolder());
        List<File> files = findFiles.findFilesByExtensions(currentModelData.getAudioExtensionsAsList());
        return files.size();
    }

    @Override
    public String validate() {
        return currentModelData.validate();
    }

    @Override
    public void performRenameInPlace() {

    }

    @Override
    public void performCopyToOutputFolder() {

    }

    @Override
    public void cancel() {
        
    }

    @Override
    public int getNumberOfAudioFilesOverall() {
        return currentModelData.getNumberOfAudioFiles();
    }

    @Override
    public int getNumberOfAudioFilesProcessed() {
        return currentModelData.getNumberOfAudioFiles() - currentModelData.getNumberOfAudioFilesRemaining();
    }

    @Override
    public int getNumberOfAudioFilesRemaining() {
        return currentModelData.getNumberOfAudioFilesRemaining();
    }
}
