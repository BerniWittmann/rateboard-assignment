package at.rateboard.assignment.queue;

import org.springframework.stereotype.Service;

/**
 * Spring Service to use the queue throughout the Spring application
 * change the extended class to change the queue implementation
 */
@Service
public class QueueService extends FileBasedQueue {
}
