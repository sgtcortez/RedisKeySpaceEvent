package br.com.ramboindustries.keynotification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

@SpringBootApplication
@EnableMongoRepositories
public class KeynotificationApplication implements CommandLineRunner {

	private static final String HOST = "localhost";
	private static final int PORT = 6379;
	public static final String KEY_APPLICATION_PREFIX = "MyApp";

	public static void main(String[] args) 
	{
		SpringApplication.run(KeynotificationApplication.class, args);
	}

    @Bean(destroyMethod = "close")
    public JedisPool pool() 
    {
        final JedisPool pool = new JedisPool(HOST, PORT);
        return pool;
    }

	@Autowired
	@Qualifier("KeyEventListener")
	private JedisPubSub listener;

	@Override
	public void run(String... args) throws Exception 
	{

		final var keyNotification = new Thread()
		{

			// https://redis.io/docs/manual/keyspace-notifications/#type-of-events
			// we are interested in receiving events of any key that starts with the our key application prefix
			final String channel = "__key*__:" + KEY_APPLICATION_PREFIX + "*";
	
			@Override
			public void run() 
			{
				final var jedis = new Jedis(HOST, PORT);
				System.out.println("Subscibed to channel: " + channel);

				// psubcribe will block(since it does polling to redis), this is why we need a dedicated thread
				// https://github.com/redis/jedis/wiki/AdvancedUsage#publishsubscribe
				jedis.psubscribe(listener, channel);
				System.out.println("Done!");
				jedis.close();
			}
		};
		keyNotification.start();		
	}

}
