package Model.SimpleModel;

import java.nio.file.Path;
import java.util.List;

public class CurrentModelData implements ISimpleModelData {

    private Path inputFolder;
    private Path outputFolder;
    private String audioExtensions;

    private int numberOfAudioFiles = 0;
    private int numberOfAudioFilesRemaining = 0;

    public CurrentModelData() {}

    public Path getInputFolder() {
        return inputFolder;
    }

    public void setInputFolder(Path inputFolder) {
        this.inputFolder = inputFolder;
    }

    public Path getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(Path outputFolder) {
        this.outputFolder = outputFolder;
    }

    public String getAudioExtensions() {
        return audioExtensions;
    }

    @Override
    public List<String> getAudioExtensionsAsList() {
        return List.of(audioExtensions.split("\\s*,\\s*"));
    }

    public void setAudioExtensions(String audioExtensions) {
        this.audioExtensions = audioExtensions;
    }

    @Override
    public int getNumberOfAudioFiles() {
        return numberOfAudioFiles;
    }

    @Override
    public void setNumberOfAudioFiles(int count) {
        this.numberOfAudioFiles = count;
    }

    @Override
    public int getNumberOfAudioFilesRemaining() {
        return numberOfAudioFilesRemaining;
    }

    @Override
    public void setNumberOfAudioFilesRemaining(int count) {
        this.numberOfAudioFilesRemaining = count;
    }

    @Override
    public void audioFileProcessed() {
        this.numberOfAudioFilesRemaining--;
    }

    @Override
    public String validate() {

        String status = "INVALID";
        if (inputFolder != null && inputFolder.toFile().isDirectory()) {
            status = "VALID";
        }
        String validationText = String.format("Input folder: \"%s\", is %s%n", inputFolder, status);

        status = "does NOT EXIST - INVALID";
        if (outputFolder != null && outputFolder.toFile().isDirectory()) {
            status = "EXISTS - VALID";
        }
        validationText += String.format("Output folder: \"%s\", %s%n", outputFolder, status);

        status = "INVALID";
        if (audioExtensions != null && !audioExtensions.isEmpty()) {
            status = "VALID";
        }

        validationText += String.format("Audio Extensions: \"%s\", %s%n", audioExtensions, status);

        status = "INVALID";
        if (numberOfAudioFiles >= 0 && numberOfAudioFiles >= numberOfAudioFilesRemaining) {
            status = "VALID";
        }

        validationText += String.format("numberOfAudioFiles=%d, %s%n", numberOfAudioFiles, status);

        status = "INVALID";
        if (numberOfAudioFilesRemaining >= 0 && numberOfAudioFiles >= numberOfAudioFilesRemaining) {
            status = "VALID";
        }
        validationText += String.format("numberOfAudioFilesRemaining=%d, %s%n", numberOfAudioFilesRemaining, status);

        return validationText;
    }

    @Override
    public String toString() {
        return "CurrentModelData{" +
                "inputFolder=" + inputFolder +
                ", outputFolder=" + outputFolder +
                ", audioExtensions='" + audioExtensions + '\'' +
                ", numberOfAudioFiles=" + numberOfAudioFiles +
                ", numberOfAudioFilesRemaining=" + numberOfAudioFilesRemaining +
                '}';
    }
}
