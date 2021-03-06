package at.rateboard.assignment.importer;

import at.rateboard.assignment.queue.QueueElement;
import at.rateboard.assignment.queue.QueueService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ImportController {
    private final QueueService queueService;
    private static final String template = "Received Data: %s";

    public ImportController(QueueService queueService) {
        this.queueService = queueService;
    }

    /**
     * Use the given data and put them in the queue for the consumer to pick up
     * @param data String of import data
     * @return Success Message
     */
    @PostMapping("/import")
    public String importData(@RequestBody String data) {
        queueService.add(new QueueElement(data));
        return String.format(template, data);
    }
}
