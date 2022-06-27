# Introduction

This proof of concept only demonstrates how we can use the redis key space notification mecanism to build something nice.   

So, for example, we can use it to send keys that were created to another source(like Mongo), and then, we can handle the keys that exists on redis, without using the **KEYS** or **SCAN** command, which, are very slows on huge databases.   
So, for example, instead of using redis scan command to make a lookup for a key, we can use mongo text search.    

**Note:** We need to reminder that redis publish/subscribe model is **fire** and **forget**, so, it means that, if we are not consuming a channel, the data will be lost ...   
From [redis docs](https://redis.io/docs/manual/keyspace-notifications/):    
> Note: Redis Pub/Sub is fire and forget that is, if your Pub/Sub client disconnects, and reconnects later, all the events delivered during the time the client was disconnected are lost.








