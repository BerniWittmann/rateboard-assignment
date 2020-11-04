package at.rateboard.assignment;

import at.rateboard.assignment.importer.ImportController;
import at.rateboard.assignment.queue.QueueElement;
import at.rateboard.assignment.queue.QueueService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ImportController.class)
public class ImportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QueueService queueService;

    @Test
    public void importShouldAddDataToQueue() throws Exception {
        when(queueService.add(any(QueueElement.class))).thenReturn(true);

        this.mockMvc.perform(post("/import" ).content("Test").contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Received Data: Test")));

        ArgumentCaptor<QueueElement> argumentCaptor = ArgumentCaptor.forClass(QueueElement.class);
        verify(queueService).add(argumentCaptor.capture());
        verifyNoMoreInteractions(queueService);
    }
}
