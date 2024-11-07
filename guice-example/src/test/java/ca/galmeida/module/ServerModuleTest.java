package ca.galmeida.module;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServerModuleTest {

    @Mock
    private ServerModule.ServerSocketFactory serverSocketFactory;

    @Mock
    private ServerSocket serverSocket;

    @Test
    public void testGetAvailablePort_TwoPorts() throws IOException {
        var serverModule = new ServerModule();
        when(serverSocketFactory.get(anyInt()))
                .thenReturn(serverSocket)
                .thenThrow(new IOException("port not available"))
                .thenReturn(serverSocket);
        int firstPort = serverModule.getAvailablePort(serverSocketFactory);
        int secondPort = serverModule.getAvailablePort(serverSocketFactory);
        assertNotEquals(firstPort, secondPort);
        assertTrue(firstPort < secondPort);
    }

    @Test
    public void testGetAvailablePort_NoneAvailable() throws IOException {
        var serverModule = new ServerModule();
        when(serverSocketFactory.get(anyInt()))
                .thenThrow(new IOException("port not available"));
        var exception = assertThrows(RuntimeException.class, () -> serverModule.getAvailablePort(serverSocketFactory));
        assertTrue(exception.getMessage().contains("Could not find an available port"));
    }

}
