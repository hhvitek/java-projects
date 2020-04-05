package model;

import ini.myini.CustomIIniConfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConfigIni extends CustomIIniConfig {

    public ConfigIni() {
        super();
    }

    public List<String> getClassNames() {
        if (hasItem("actions", "class_names")) {
            String actionsItem = getValue("actions", "class_names");
            String[] actionsSplit =  actionsItem.split("\\s*,\\s*");
            return Arrays.asList(actionsSplit);
        }
        return Collections.emptyList();
    }
}
