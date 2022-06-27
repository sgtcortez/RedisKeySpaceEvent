package br.com.ramboindustries.keynotification;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("keys")
public class KeyEntity 
{

    @Id
    private String id;
    
    private String value;

    public KeyEntity(final String value) 
    {
        id = null;
        this.value = value;
    }

    public void setId(final String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }

    public void setValue(final String value) 
    {
        this.value = value;
    }
    
    public String getValue() 
    {
        return value;
    }

}
