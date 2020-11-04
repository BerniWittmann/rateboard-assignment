package at.rateboard.assignment;

import at.rateboard.assignment.consumer.Consumer;
import at.rateboard.assignment.queue.QueueElement;
import at.rateboard.assignment.queue.QueueService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.mockito.Mockito.*;

@SpringJUnitConfig(AssignmentApplication.class)
public class ConsumerTest {
    @MockBean
    private QueueService queueService;

    @SpyBean
    private Consumer consumer;

    @Test
    public void shouldConsumeQueueElement() throws InterruptedException {
        doNothing().when(consumer).process(any(String.class));
        when(queueService.poll()).thenReturn(new QueueElement("Test"));

        Thread.sleep(5000L);

        verify(queueService, times(1)).poll();
        verifyNoMoreInteractions(queueService);

        verify(consumer, times(1)).process("Test");
    }

    @Test
    public void shouldRunAtInterval() throws InterruptedException {
        when(queueService.poll()).thenReturn(new QueueElement("Test"));

        Thread.sleep(8000L);

        verify(queueService, times(2)).poll();
        verifyNoMoreInteractions(queueService);
    }

    @Test
    public void shouldHandleEmptyQueue() throws InterruptedException {
        doNothing().when(consumer).process(any(String.class));
        when(queueService.poll()).thenReturn(null);

        Thread.sleep(5000L);

        verify(queueService, times(1)).poll();
        verifyNoMoreInteractions(queueService);
        verify(consumer, times(0)).process(any(String.class));
    }
}
