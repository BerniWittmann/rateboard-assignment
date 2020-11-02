package at.rateboard.assignment.importer;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ImportController {

    private static final String template = "Received Data: %s";

    @PostMapping("/import")
    public String importData(@RequestBody String data) {
        return String.format(template, data);
    }
}
