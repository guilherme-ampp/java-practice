package ca.galmeida.module;

import ca.galmeida.tasks.TaskRunner;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class TaskRunnerModule extends AbstractModule {

    @Provides
    @Singleton
    public TaskRunner createTaskRunner() {
        return Runnable::run;
    }

}
