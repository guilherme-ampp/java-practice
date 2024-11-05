package ca.galmeida;

import ca.galmeida.module.ServerModule;
import ca.galmeida.module.TaskRunnerModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.jetty.server.Server;

public class Main {
    public static void main(String[] args) throws Exception {
        final Injector injector = Guice.createInjector(
                new TaskRunnerModule(),
                new ServerModule());

        var server = injector.getInstance(Server.class);
        server.start();
    }
}