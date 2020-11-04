package at.rateboard.assignment.queue;

/**
 * Element of a Queue, with timestamp as priority of the queue and payload data
 */
public class QueueElement {
    private final long timestamp;
    private final String data;

    public QueueElement(String data) {
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }

    public QueueElement(String data, long timestamp) {
        this.timestamp = timestamp;
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getData() {
        return data;
    }

    public String toRepresentation() {
        return getTimestamp() + ":" + getData();
    }

    public static QueueElement fromRepresentation(String str) {
        String[] parts = str.split(":", 2);
        try {
            return new QueueElement(parts[1], Long.parseLong(parts[0]));
        } catch (Exception e) {
            return null;
        }
    }
}
