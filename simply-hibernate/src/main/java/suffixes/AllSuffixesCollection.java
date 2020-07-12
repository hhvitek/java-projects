package suffixes;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents ALL possible (and impossible) suffixes. Could be used in place of suffixes.SuffixesCollectionImpl...
 */
public class AllSuffixesCollection extends SuffixesCollectionImpl {

    private static final String name = "ALL";

    @Override
    public void addSuffix(String suffix) {

    }

    @Override
    public void addSuffixes(List<String> newSuffixes) {

    }

    @Override
    public void addSuffixes(String delimitedSuffixes, String delimiter) {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasName(String anotherName) {
        return anotherName != null && anotherName.equalsIgnoreCase(name);
    }

    @Override
    public String getSuffixesAsDelimitedString(@NotNull String delimiter) {
        return "";
    }

    @Override
    public String[] getSuffixesAsStrArray() {
        return null;
    }

    @Override
    public String getFileGlobRegexFromSuffixes() {
        return "glob:*";
    }

    @NotNull
    @Override
    public Iterator<String> iterator() {
        return Collections.emptyIterator();
    }

}
