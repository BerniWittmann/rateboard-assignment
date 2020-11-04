package at.rateboard.assignment.queue;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;


@RestController
public class QueueController {
    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @GetMapping("/queue")
    public String displayQueue() {
        return queueService.parallelStream().map(QueueElement::getData).collect(Collectors.joining(", "));
    }
}
