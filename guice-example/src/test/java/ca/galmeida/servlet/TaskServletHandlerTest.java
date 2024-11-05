package ca.galmeida.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintWriter;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServletHandlerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Test
    public void testDoGet() throws Exception {
        var mockPrintWriter = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(mockPrintWriter);

        var handler = new TaskServletHandler();
        handler.doGet(request, response);
        verify(response).setContentType(eq("application/json"));
        verify(mockPrintWriter).println(contains("ok"));
    }

}
