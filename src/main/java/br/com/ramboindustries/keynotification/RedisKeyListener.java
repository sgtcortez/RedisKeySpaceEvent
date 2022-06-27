package br.com.ramboindustries.keynotification;

import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPubSub;

/**
 * Is triggered when an event on the pattern used happen
 */
@Component("KeyEventListener")
public class RedisKeyListener extends JedisPubSub
{

    /**
     * This is used to send events to mongo database
     */
    private final KeyRepository repository;
        
    public RedisKeyListener(final KeyRepository repository)
    {
        this.repository = repository;
    }

    /*
    * Pattern: The pattern that was used to receive events
    * Channel: The pattern + the key in which the event happened
    * Message: The command used(set, unlink, del, get)
     */
    @Override
    public void onPMessage(String pattern, String channel, String message) {
        final var key = channel.split(":")[1];
        final var event = EventType.map(message);
        if (event.isEmpty()) return;
        System.out.printf("Key: %s, Event: %s\n", key, event);
        if (event.get() == EventType.CREATED)
        {
            System.out.println("Saving it to MongoDB!");
            repository.save(new KeyEntity(key));
        }
    }

}
