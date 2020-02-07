package dynamic_classloader;

import actions.ActionAbstract;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class LoaderByClassNames {

    public static List<ActionAbstract> load(List<String> classNames) throws ClassLoadingException {

        List<ActionAbstract> actions = new ArrayList<>();
        for (String className: classNames) {
            ActionAbstract action = null;
            try {
                action = (ActionAbstract) Class.forName(className).getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                    | NoSuchMethodException | ClassNotFoundException e) {
                throw new ClassLoadingException(e);
            }
            actions.add(action);
        }
        return actions;
    }
}
