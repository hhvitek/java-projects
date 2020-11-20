package model.modifications.operations.directory;

import model.exceptions.ModificationException;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;

class InPlaceModDescriptorFilesModificationTest extends AbstractOperationTest {

    public InPlaceModDescriptorFilesModificationTest() {
        super("src/test/resources/model/modifications/operations");
        executableOperation = new InPlaceModDescriptorDirOperation();
    }

    @Override
    protected String getOperationDirectoryName() {
        return "MODIFY_MOD_DESCRIPTOR_FILES";
    }

    @Test
    public void modifyModFileTest() throws IOException, ModificationException {
        executableOperation.execute(workingDirectory);

        String expectedMd5sum = "c50df5ea82a72c94995199f76121ed41";

        String actualMd5sum = DigestUtils.md5Hex(
                new FileInputStream(
                        workingDirectory.resolve(
                                "2219070393_legendary_worlds_2_7_x/descriptor.mod"
                        ).toFile()
                )
        );

        Assertions.assertEquals(expectedMd5sum, actualMd5sum);

    }






}
