package ca.galmeida;

import ca.galmeida.module.ServerModule;
import ca.galmeida.module.TaskRunnerModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.jetty.server.Server;

public class Main {

    protected static Injector guiceInjector = Guice.createInjector(
            new TaskRunnerModule(),
            new ServerModule());

    public static void main(String[] args) throws Exception {
        var server = guiceInjector.getInstance(Server.class);
        server.start();
    }
}