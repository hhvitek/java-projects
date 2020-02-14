package dynamic_classloader;

import actions.ActionAbstract;
import actions.NoAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LoaderByClassNames {

    private static final Logger logger = LoggerFactory.getLogger(LoaderByClassNames.class);

    public static List<ActionAbstract> loadAll(List<String> classNames) throws ClassLoadingException {
        List<ActionAbstract> actions = new ArrayList<>();
        for (String className: classNames) {
            ActionAbstract action = load(className);
            actions.add(action);
        }
        return actions;
    }

    public static ActionAbstract load(String className) throws ClassLoadingException {
        ActionAbstract action = null;
        try {
            Class cls = Class.forName(className);
            Method method = cls.getDeclaredMethod("getInstance", null);
            action = (ActionAbstract) method.invoke(null, null);
            return action;
        } catch (IllegalAccessException | InvocationTargetException
                | NoSuchMethodException | ClassNotFoundException e) {
            logger.warn("Class \"{}\" not found. Forgot package name \"package.className\"? What about static method getInstance()?", className);
            throw new ClassLoadingException(e);
        }

    }

    /*
    public static ActionAbstract load(String className) throws ClassLoadingException {
        ActionAbstract action = null;
        try {
            action = (ActionAbstract) Class.forName(className).getDeclaredConstructor().newInstance();
            return action;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                | NoSuchMethodException | ClassNotFoundException e) {
            throw new ClassLoadingException(e);
        }

    }

     */
}
