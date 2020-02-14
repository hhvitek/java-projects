package dynamic_classloader;

import actions.ActionAbstract;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoaderByClassNamesTest {

    @Test
    void loadTest() {
        List<String> input = new ArrayList<>();
        input.add("actions.NoAction");
        input.add("actions.ShutDownAction");

        try {
            List<ActionAbstract> output = LoaderByClassNames.loadAll(input);
            Assertions.assertEquals(2, output.size());
            ActionAbstract action = output.get(1);
        } catch (ClassLoadingException e) {
            Assertions.fail(e);
        }
    }
}
