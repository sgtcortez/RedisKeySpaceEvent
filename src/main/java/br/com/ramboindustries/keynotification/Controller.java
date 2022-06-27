package br.com.ramboindustries.keynotification;

import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

@RestController
@RequestMapping(value = "/api/keys")
public class Controller {
    
    public record Request(
        String name, 
        String email,
        int age
    ){};

    @Autowired
    private JedisPool pool;

    /**
     * curl -X POST  \
     * --data '{"name": "Matheus", "email": "mahteusrambo@gmail.com", "age": 26}' \
     * --header "Content-Type:application/json" \
     * "http://localhost:8080/api/keys"
     */

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody final Request request)
    {
        try (final var jedis = pool.getResource()) 
        {
            var params = SetParams.setParams().nx().ex(30L);
            jedis.set(String.format("%s@NAME=%s|E_MAIL=%s", KeynotificationApplication.KEY_APPLICATION_PREFIX, request.name, request.email), request.toString(), params);
            return ResponseEntity.ok().build();
        }
    }
    
    @GetMapping(value = "/{key}")
    public ResponseEntity<String> get(@PathVariable(value = "key") final String key) 
    {
        try (final var jedis = pool.getResource()) 
        {
            final var result = jedis.get(key);
            if (Objects.isNull(result)) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping
    public ResponseEntity<Set<String>> get() 
    {
        try (final var jedis = pool.getResource()) 
        {
            return ResponseEntity.ok(jedis.keys(KeynotificationApplication.KEY_APPLICATION_PREFIX + "*"));
        }
    }

}
