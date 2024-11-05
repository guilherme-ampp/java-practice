package ca.galmeida.module;

import ca.galmeida.servlet.TaskServletHandler;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class ServerModule extends AbstractModule {

    private static final int START_PORT = 8080;
    private static final int MAX_PORT_ATTEMPTS = 10;

    @Provides
    @Singleton
    @Named(ConstantsModule.SERVER_PORT)
    public int getAvailablePort() {
        int port = START_PORT;
        final AtomicInteger countDown = new AtomicInteger(MAX_PORT_ATTEMPTS);
        while (countDown.getAndDecrement() > 0) {
            try (ServerSocket socket = new ServerSocket(port)) {
                // If no exception was thrown, the port is available
                return port;
            } catch (IOException e) {
                // If the port is already in use, move to the next one
                port++;
            }
        }
        throw new RuntimeException("Could not find an available port to start the server");
    }

    @Provides
    @Singleton
    @Named(ConstantsModule.ROOT_SERVLET_CLASSNAME)
    public String getServerHandlerClassName() {
        return TaskServletHandler.class.getName();
    }

    @Provides
    @Singleton
    public Server createServer(@Named(ConstantsModule.SERVER_PORT) final int serverPort,
                               @Named(ConstantsModule.ROOT_SERVLET_CLASSNAME) final String rootServletClassName) {
        final Server server = new Server(serverPort);
        final ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        handler.addServlet(rootServletClassName, "/");
        server.setHandler(handler);
        return server;
    }

}
