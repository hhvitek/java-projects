package model.exceptions;

public class UnknownModification extends Exception {

    private String modificationId;
    private String packageAndClass;

    public UnknownModification(String modificationId) {
        this.modificationId = modificationId;
    }

    public UnknownModification(String modificationId, String packageAndClass) {
        this.modificationId = modificationId;
        this.packageAndClass = packageAndClass;
    }

    @Override
    public String toString() {
        return "UnknownModification{" +
                "modificationId='" + modificationId + '\'' +
                ", packageAndClass='" + packageAndClass + '\'' +
                '}';
    }
}
