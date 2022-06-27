package br.com.ramboindustries.keynotification;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Handles the redis notification mecanism system
 * To this work, we must ensure that at least the config set: KEA is used 
 */
public enum EventType 
{

    /**
     * The key was created or updated
     * With this event, we might put this event into another source(like Mongo), so, we can have make fast search for keys instead of using SCAN or KEYS command
     */
    CREATED, 

    /** 
     * The key was deleted or expired
     * With this event, we can trigger an event which will create the key again 
     */
    REMOVED;

    private static Map<String, EventType> EVENTS_MAPPED; 

    static 
    {
        // For information: https://redis.io/docs/manual/keyspace-notifications/#events-generated-by-different-commands
        EVENTS_MAPPED = new HashMap<>(10);
        EVENTS_MAPPED.put("set", EventType.CREATED);
        EVENTS_MAPPED.put("del", EventType.REMOVED);
        EVENTS_MAPPED.put("expired", EventType.REMOVED);
    }


    public static Optional<EventType> map(final String redisValue) 
    {
        return Optional.ofNullable(EVENTS_MAPPED.get(redisValue));
    }


};
