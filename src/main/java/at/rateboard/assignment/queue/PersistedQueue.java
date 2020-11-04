package at.rateboard.assignment.queue;

import java.util.Comparator;
import java.util.PriorityQueue;

public abstract class PersistedQueue extends PriorityQueue<QueueElement> {
    public static final Comparator<QueueElement> COMPARE_BY_TIMESTAMP = Comparator
            .comparing(QueueElement::getTimestamp, Comparator.naturalOrder());

    public PersistedQueue() {
        super(10, COMPARE_BY_TIMESTAMP);
    }
}
