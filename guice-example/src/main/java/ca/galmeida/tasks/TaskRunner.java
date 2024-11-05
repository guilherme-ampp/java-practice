package ca.galmeida.tasks;

public interface TaskRunner {

    void dispatch(final Runnable runnable);

}
