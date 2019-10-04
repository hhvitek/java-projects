package app.settings.ini;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import org.ini4j.Config;
import org.ini4j.Ini;

public class Ini4jIIniConfig implements IIniConfig {

    protected Ini ini;

    public Ini4jIIniConfig() {
        ini = new Ini();
        Config iniConfig = ini.getConfig();
        iniConfig.setEmptyOption(true);
        iniConfig.setEscape(false); // do not change/escape "https://" to "https\://" etc.
    }

    @Override
    public IIniSection getSection(String sectionName) {
        Ini.Section iniSection = ini.get(sectionName);
        if (iniSection != null) {
            return new Ini4jIIniSection(iniSection);
        }
        return null;
    }

    @Override
    public String getValue(String sectionName, String key) {
        return ini.get(sectionName, key);
    }

    @Override
    public boolean containsKey(String sectionName, String key) {
        return (ini.get(sectionName, key) != null);
    }

    @Override
    public boolean containsSection(String sectionName) {
        return ini.get(sectionName) != null;
    }

    @Override
    public void load(File file) throws IOException, InvalidConfigFileFormatException {
        ini.load(file);
    }

    @Override
    public void load(Reader reader) throws IOException, InvalidConfigFileFormatException {
        ini.load(reader);
    }

    @Override
    public void putComment(String section, String comment) {
        ini.putComment(section, comment);
    }

    @Override
    public void putComment(String section, String key, String comment) {
        Ini.Section iniSection = ini.get(section);
        if (iniSection != null) {
            iniSection.putComment(key, comment);
        }

    }

    @Override
    public void putValue(String sectionName, String key, String value) {
        ini.put(sectionName, key, value);
    }

    @Override
    public void store(File file) throws IOException {
        ini.store(file);
    }

    @Override
    public String toString() {
        return ini.toString();
    }



}
