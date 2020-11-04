package at.rateboard.assignment.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileBasedQueue extends PersistedQueue {
    private static final String FILE_PATH = "/tmp/queue.txt";
    Logger logger = LoggerFactory.getLogger(FileBasedQueue.class);

    public FileBasedQueue() {
        initFile();
        load();
    }

    private void store() {
        Iterable<String> lines = this.parallelStream().map(QueueElement::toRepresentation).collect(Collectors.toList());
        try {
            Files.write(Paths.get(FILE_PATH), lines);
        } catch (IOException e) {
            logger.error("File Based Queue errored", e);
        }
    }

    private void initFile() {
        try {
            // Create the file if it does not exist
            new FileOutputStream(FILE_PATH, true).close();
        } catch (IOException e) {
            logger.error("File Based Queue errored", e);
        }
    }
    private void load() {
        super.clear();
        Stream<String> lines = null;
        try {
            lines = Files.lines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            logger.error("File Based Queue errored", e);
        }
        super.addAll(lines.map(QueueElement::fromRepresentation).collect(Collectors.toList()));
    }

    @Override
    public boolean add(QueueElement queueElement) {
        boolean result = super.add(queueElement);
        store();
        return result;
    }

    @Override
    public boolean offer(QueueElement queueElement) {
        boolean result = super.offer(queueElement);
        store();
        return result;
    }

    @Override
    public boolean remove(Object o) {
        boolean result = super.remove(o);
        store();
        return result;
    }

    @Override
    public void clear() {
        super.clear();
        store();
    }

    @Override
    public boolean addAll(Collection<? extends QueueElement> c) {
        boolean result = super.addAll(c);
        store();
        return result;
    }

    @Override
    public QueueElement poll() {
        QueueElement result = super.poll();
        store();
        return result;
    }
}
