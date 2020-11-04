package at.rateboard.assignment.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Queue Implementation that persists and rehydrates the queue from a file
 */
public class FileBasedQueue extends PersistedQueue {
    private static final String FILE_PATH = "/tmp/queue.txt";
    private Logger logger = LoggerFactory.getLogger(FileBasedQueue.class);

    public FileBasedQueue() {
        initFile();
        load();
    }

    /**
     * Persist the queue data to the given file
     */
    private void store() {
        Iterable<String> lines = this.parallelStream().map(QueueElement::toRepresentation).collect(Collectors.toList());
        try {
            Files.write(Paths.get(FILE_PATH), lines);
        } catch (IOException e) {
            logger.error("File Based Queue errored", e);
        }
    }

    /**
     * Initialise the queue file and create it if it does not exist
     */
    private void initFile() {
        try {
            // Create the file if it does not exist
            new FileOutputStream(FILE_PATH, true).close();
        } catch (IOException e) {
            logger.error("File Based Queue errored", e);
        }
    }

    /**
     * Load and rehydrate the queue from the file
     */
    private void load() {
        super.clear();
        Stream<String> lines;
        try {
            lines = Objects.requireNonNull(Files.lines(Paths.get(FILE_PATH)));
            super.addAll(lines.map(QueueElement::fromRepresentation).filter(Objects::nonNull).collect(Collectors.toList()));
        } catch (IOException e) {
            logger.error("File Based Queue errored", e);
        }
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
