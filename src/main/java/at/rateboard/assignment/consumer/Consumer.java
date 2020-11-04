package at.rateboard.assignment.consumer;

import at.rateboard.assignment.queue.QueueElement;
import at.rateboard.assignment.queue.QueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Consumer that consumes the data from the queue
 */
@Component
public class Consumer {
    private static final Logger log = LoggerFactory.getLogger(Consumer.class);
    private final QueueService queueService;

    public Consumer(QueueService queueService) {
        this.queueService = queueService;
    }

    /**
     * consume items from queue at scheduled interval and log them
     */
    @Scheduled(fixedRate = 5000)
    public void consumeQueue() {
        QueueElement item = queueService.poll();
        if (item == null) {
            return;
        }
        // Instead of logging this method could trigger the import and success notification
        log.info("Consumed Item: {}", item.getData());
    }
}
