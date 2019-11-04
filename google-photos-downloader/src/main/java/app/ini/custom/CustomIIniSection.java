package app.ini.custom;

import app.ini.IIniSection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class CustomIIniSection implements IIniSection, Iterable<CustomItem> {

    private final Logger logger = LoggerFactory.getLogger(CustomIIniSection.class);
    private String comment = "";
    private String name;
    private List<CustomItem> items = new ArrayList<>();

    public CustomIIniSection(String name) {
        this.name = name;
    }

    @Override
    public String getValue(String key) {
        if (key == null || key.isBlank()) {
            return null;
        }

        Optional<CustomItem> optItem = items.stream()
                .filter(x -> key.equals(x.getKey()))
                .findAny();

        return optItem.map(CustomItem::getValue).orElse(null);
    }

    @Override
    public boolean containsKey(String key) {
        if (key == null || key.isBlank()) {
            logger.warn("Asking for an empty key.");
            return false;
        }

        return items.stream()
                .anyMatch(x -> key.equals(x.getKey()));
    }

    @Override
    public void putValue(String key, String value) {
        if (key == null || key.isBlank()) {
            logger.warn("Trying to input an empty key/value item.");
            return;
        }

        items.stream()
                .filter(x -> key.equals(x.getKey()))
                .findAny()
                .ifPresentOrElse(
                        x -> x.setValue(value),
                        () -> {
                            CustomItem item = new CustomItem(key, value, "");
                            items.add(item);
                        });

    }

    @Override
    public void putComment(String key, String comment) {
        if (key == null) { // if the key is null, it is considered to become the new section
            // comment.
            this.comment = comment;
            return;
        }

        if (key.isBlank()) {
            logger.warn("Trying to use an invalid blank key.");
            return;
        }

        items.stream()
                .filter(x -> key.equals(x.getKey()))
                .findAny()
                .ifPresentOrElse(
                        x -> x.setComment(comment),
                        () -> {
                            CustomItem item = new CustomItem(key, "", comment);
                            items.add(item);
                        });
    }

    @Override
    public String getComment(String key) {
        if (key == null) {
            return this.comment;
        }

        if (key.isBlank()) {
            logger.warn("Trying to use an invalid blank key.");
            return null;
        }

        Optional<CustomItem> optItem = items.stream()
                .filter(x -> key.equals(x.getKey()))
                .findAny();

        return optItem.map(CustomItem::getComment).orElse(null);
    }

    @Override
    public String toString() {
        String toStr = "";
        if (this.comment != null && !comment.isBlank()) {
            toStr += CustomItem.createToStringComment(comment) + System.lineSeparator();
        }
        toStr += "[" + name + "]" + System.lineSeparator();

//        items.stream()
//                .map(CustomItem::toString)
//                .reduce((x, y) -> x + System.lineSeparator() + y)
//                .orElse("");


        for (CustomItem item : items) {
            toStr += item.toString() + System.lineSeparator();
        }

        return toStr;
    }

    @Override
    public Iterator<CustomItem> iterator() {
        return items.iterator();
    }

}
