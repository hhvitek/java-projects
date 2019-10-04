package app.settings.ini;

import org.ini4j.Ini;

public class Ini4jIIniSection implements IIniSection {

    private Ini.Section iniSection;

    public Ini4jIIniSection(Ini.Section iniSection) {
        this.iniSection = iniSection;
    }

    @Override
    public String getValue(String key) {
        return iniSection.get(key);
    }

    @Override
    public boolean containsKey(String key) {
        return iniSection.containsKey(key);
    }

    @Override
    public String toString() {
        return iniSection.toString();
    }





}
