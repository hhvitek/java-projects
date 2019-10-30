package app.settings.ini.custom;

public class CustomItem {

    private String key;
    private String comment;
    private String value;

    public CustomItem() {
    }

    public CustomItem(String key, String value, String comment) {
        this.key = key;
        this.value = value;
        this.comment = comment;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        String toStr = "";
        if (comment != null && !comment.isBlank()) {
            toStr += createToStringComment(comment) + System.lineSeparator();
        }

        return toStr + key + " = " + value;
    }

    public static String createToStringComment(String multiline) {
        if (multiline == null || multiline.isBlank()) {
            return multiline;
        }

        return multiline.lines()
                .map("# "::concat)
                .reduce((x, y) -> x + System.lineSeparator() + y)
                .get();
    }
}
