package app.settings.ini.custom;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomIIniConfigWithoutLoadAndStoreTest {

    @Test
    void testGetSectionExist() {
    }

    @Test
    void testGetSectionNotExistReturnsNull() {
        fail("Not yet implemented");
    }

    @Test
    void testGetValueKeyExist() {
        fail("Not yet implemented");
    }

    @Test
    void testGetValueKeyNotExistReturnsNull() {
        fail("Not yet implemented");
    }

    @Test
    void testContainsKeyExist() {
        fail("Not yet implemented");
    }

    @Test
    void testContainsKeyNotExistReturnsFalse() {
        fail("Not yet implemented");
    }

    @Test
    void testContainsSectionExist() {
        fail("Not yet implemented");
    }

    @Test
    void testContainsSectionNotExistReturnsFalse() {
        fail("Not yet implemented");
    }

    @Test
    void testPutComment() {
        fail("Not yet implemented");
    }

    @Test
    void testPutCommentAlreadyHasCommentOverwriteOldComment() {
        fail("Not yet implemented");
    }

    @Test
    void testPutValue() {
        fail("Not yet implemented");
    }

    @Test
    void testPutValueAlreadyHasValueOverwriteOldValue() {
        fail("Not yet implemented");
    }

    @Test
    void testCreateToStringComment() {
        String multiline = String.join(System.lineSeparator(),
                "The first comment",
                "The second comment");
        String actual = CustomIIniConfig.createToStringComment(multiline);
        String expected = "# The first comment" + System.lineSeparator() + "# The second comment";

        Assertions.assertEquals(expected, actual);
    }

}
