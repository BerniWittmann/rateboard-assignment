# Rateboard Assignment - Bernhard Wittmann

> Task: Create an HTTP service (importer) that can receive these messages/strings. Create a „consumer“ that will process the incoming messages. In your case the consumer doesn’t need to do anything useful but in our real system it would actually import the reservation and return a response to the original sender. Assume that multiple importer services can run in parallel on different hosts but there is only one single consumer. Please also consider the case that the consumer could be unreachable for some time but you shouldn’t lose messages.

## Installation and Usage

Prerequisites: JDK 1.8 or later, Maven 3.2+

Install Dependencies:

```
./mvnw clean install
```

Run Application

```
./mvnw spring-boot:run
```

Add Elements to the Queue by sending an HTTP Request (See also `Rateboard Assignment.postman_collection.json` to import Requests right into Postman)

```
curl --location --request POST 'localhost:8080/import' \
--header 'Content-Type: text/plain' \
--data-raw 'Test Data'
```

After some time you should see the item being consumed in the application logs.

## Questions

### Explanation of the Solution

The central data structure of this solution is a simple queue. The queue is ordered by the timestamp (in milliseconds) of the addition to the queue. Since i was unsure how severe the downtime of the consumer is, i implemented an extensible queue solution where different queue implementations can be easily plugged in. I created a simple InMemoryQueue, which saves the items in memory and a file based queue to persist and rehydrate the queue data to a file.
These queue implementations are then utilized by the QueueService where the type of queue can be configured and which allows autowiring to other parts of the Spring application. 
I additionally added a queue Controller, which provides a `/queue` REST Endpoint, where the contents of the queue can be observed for easier testing.

The ImportController provides the `/import` Endpoint, which stores the Request body in the queue. 

The Consumer runs a scheduled tasks at an example interval of 5 seconds, which pulls the first item from the queue and then just logs the item to the console. In a production environment this task could do the actual importing and trigger the success notification.

I added two simple test suites for the Consumer and the Importer. 

### Potential Problems

A potential problem is the difficulty of error handling. The File based queue poses quite a risk for errors due to I/O, concurrency when running this approach on distributed systems.
There should definitely be some kind of retry mechanism, that puts item back to the queue if the consumer fails for some reason. (See Improvements Section for a possibly better solution)

Right now the consumer and the rest of the application run in the same context/application, which is due to the simplification of this approach. Ideally, you would want to have the consumer and maybe also the queue separate from the rest of the application to allow real asynchronity. 

### Necessary Queue Information

Apart from the data itself the queue needs to have some kind of ordering/priority value, that provides a sorting order. This could be either a timestamp (like i chose), or a sequential number or a combination of both. I opted for the timestamp in milliseconds since it is most straight-forward however if request are coming at such a high rate that would result in duplicates, then maybe a combination would be beneficial. A possible solution would be appending an increment (or the current queue length) to the timestamp. 

### Further Improvements

Instead of maintaining the queue manually, I would advise to use Redis to handle the queuing and consuming the data. Thus, the consumer can run in a separate process (even on another machine) easily and retrying, priority etc would come automatically with that. 

Also, I am not confident with using a file based queue like this one in production since it is quite error-prone and would definitely benefit from a more thorough error checking

The Tests for the consumer could be improved and also the queue service and implementations should be tested quite extensively. However, this testing was not in scope of the assignment.
## Contact

Bernhard Wittmann

mail@bernhardwittmann.com

