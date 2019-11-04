package app.ini.custom;

import app.ini.IIniSection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomIIniSectionTest {

    @Test
    void testGetValueWhichNotExistReturnNull_OrPutValue() {
        IIniSection section = new CustomIIniSection("section");
        section.putValue("new_key", "new_value");

        Assertions.assertNull(section.getValue("unknown_key"));
    }

    @Test
    void testGetValueNullReturnNull_OrPutValue() {
        IIniSection section = new CustomIIniSection("section");
        section.putValue("new_key", "new_value");

        Assertions.assertNull(section.getValue(null));
    }

    @Test
    void testContainsKeyExist_OrContainsKey() {
        IIniSection section = new CustomIIniSection("section");
        section.putValue("new_key", "new_value");

        Assertions.assertTrue(section.containsKey("new_key"));
    }

    @Test
    void testContainsKeyNotExistReturnNull_OrContainsKey() {
        IIniSection section = new CustomIIniSection("section");
        section.putValue("new_key", "new_value");

        Assertions.assertFalse(section.containsKey("unknown_key"));
    }

    @Test
    void testContainsKeyNullReturnNull_OrContainsKey() {
        IIniSection section = new CustomIIniSection("section");
        section.putValue("new_key", "new_value");

        Assertions.assertFalse(section.containsKey(null));
    }

    @Test
    void testPutValueNewContainNewValue_OrGetValue() {
        IIniSection section = new CustomIIniSection("section");
        section.putValue("new_key", "new_value");

        Assertions.assertEquals("new_value", section.getValue("new_key"));
    }

    @Test
    void testPutValueAlreadyContainUpdateValue_OrGetValue() {
        IIniSection section = new CustomIIniSection("section");
        section.putValue("new_key", "new_value");
        section.putValue("new_key", "replace_value");

        Assertions.assertEquals("replace_value", section.getValue("new_key"));
    }

    @Test
    void testPutValueNullAlreadyContainUpdateValueToNull_OrGetValue() {
        IIniSection section = new CustomIIniSection("section");
        section.putValue("new_key", "new_value");
        section.putValue("new_key", null);

        Assertions.assertNull(section.getValue("new_key"));
    }

    @Test
    void testPutCommentHeaderHasHeader_OrGetComment() {
        IIniSection section = new CustomIIniSection("section");
        section.putComment(null, "This is the first section header comment.");
        section.putComment(null, "This is the second section header comment.");

        Assertions.assertEquals("This is the second section header comment.",
                section.getComment(null));
    }

    @Test
    void testPutCommentItemExistHasComment_OrGetCommentPutValue() {
        IIniSection section = new CustomIIniSection("section");
        section.putValue("new_key", "new_value");
        section.putComment("new_key", "This is the item comment.");

        Assertions.assertEquals("This is the item comment.",
                section.getComment("new_key"));
    }

    @Test
    void testPutCommentItemNotExistHasComment_OrGetComment() {
        IIniSection section = new CustomIIniSection("section");
        section.putComment("new_key", "This is the item comment.");
        Assertions.assertEquals("This is the item comment.",
                section.getComment("new_key"));
    }

    @Test
    void testGetCommentNotExistItemReturnNull_OrPutValuePutComment() {
        IIniSection section = new CustomIIniSection("section");
        section.putValue("new_key", "new_value");
        section.putComment("new_key", "This is the item comment.");

        Assertions.assertNull(section.getComment("unknown_key"));
    }

    @Test
    void testToString() {
        IIniSection section = new CustomIIniSection("section");
        section.putValue("first_key", "first_value");
        section.putComment("first_key", "This is the first item comment.");

        section.putValue("second_key", "second_value");
        section.putComment("second_key", "This is the second item comment.");

        section.putComment(null, "The section comment");

        String expected = String.join(System.lineSeparator(),
                "# The section comment",
                "[section]",
                "# This is the first item comment.",
                "first_key = first_value",
                "# This is the second item comment.",
                "second_key = second_value");
        expected += System.lineSeparator();
        String actual = section.toString();

        Assertions.assertEquals(expected, actual);
    }

}
