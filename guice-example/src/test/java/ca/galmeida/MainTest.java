package ca.galmeida;

import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static ca.galmeida.Main.guiceInjector;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MainTest {

    private Server server;

    @BeforeEach
    public void setup() {
        // We can use the main guice injector (the same instantiated in production), or we could
        // create a new injector overriding some of the producer methods with mocks
        server = guiceInjector.getInstance(Server.class);
    }

    @Test
    public void testServerCanStart() {
        assertNotNull(server);
        runAsync(() -> {
            try {
                server.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        waitFor(true, () -> server.isRunning());
        runAsync(() -> {
            try {
                server.stop();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        waitFor(false, () -> server.isRunning());
        waitFor(true, () -> server.isStopped());
    }

    @Test
    public void testMainStartsServer() throws Exception {
        Main.main(null);
        waitFor(true, () -> server.isRunning());
        runAsync(() -> {
            try {
                server.stop();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        waitFor(true, () -> server.isStopped());
    }

    private static Thread runAsync(final Runnable runnable) {
        var t = new Thread(runnable);
        t.start();
        return t;
    }

    private static <R> void waitFor(final R expected, final Supplier<R> func) {
        final AtomicInteger attempts = new AtomicInteger(10);
        R result = null;
        while (attempts.getAndDecrement() > 0) {
            result = func.get();
            if (result.equals(expected)) {
                return;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        assertEquals(result, expected);
    }

}
