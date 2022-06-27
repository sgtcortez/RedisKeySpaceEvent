# Introduction

This proof of concept only demonstrates how we can use the redis key space notification mecanism to build something nice.   

So, for example, we can use it to send keys that were created to another source(like Mongo), and then, we can handle the keys that exists on redis, without using the **KEYS** or **SCAN** command, which, are very slows on huge databases.   
So, for example, instead of using redis scan command to make a lookup for a key, we can use mongo text search.    





