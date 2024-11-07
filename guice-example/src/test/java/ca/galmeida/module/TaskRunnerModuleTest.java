package ca.galmeida.module;

import ca.galmeida.tasks.TaskRunner;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskRunnerModuleTest {

    @Test
    public void testTaskRunnerCreation() {
        final AtomicBoolean flag = new AtomicBoolean(false);
        final TaskRunner runner = new TaskRunnerModule().createTaskRunner();
        runner.dispatch(() -> flag.set(true));
        assertTrue(flag.get());
    }

}
