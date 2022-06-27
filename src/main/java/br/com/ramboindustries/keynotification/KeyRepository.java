package br.com.ramboindustries.keynotification;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface KeyRepository extends MongoRepository<KeyEntity, String> 
{
    
}

