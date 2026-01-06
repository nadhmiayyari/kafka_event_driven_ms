
# Idempotent kafka consumer 

idempotent kafka consumer is a consumer that can consume
the same message * times without causing any effects or data inconsistencies 
 
even if the producer will produce the same message multiple times ,
the consumer will process the same message multiple times  without causing any side effects 

for example : 
```  
source topic -> consumer -> database -> another topic 
consume message -> reads from db -> perform business logic -> write to database 

```
when doing all of these operations , it is possible that this code takes a very long time to complete 

exceeds the max poll interval in milliseconds : max.poll.interval.ms exceeded  

````
max.poll.interval.ms
````

controls how much time kafka consumer has to process full messages before it should pull a new batch of messages from kafka topic

if kafka consumer does not pull new messages from kafka broker longer than the time configured with max.poll.interval.ms 

than kafka broker will assume that this consumer has failed or it stopped and it will remove it from the consumer group 

if it happens ==> consumer will not update the partition offset ==> for kafka broker this message was not successfully consumed


=> kafka broker will deliver the same message to another instance of the same microservice 



this way the kafka producer if it is not idempotent it will produce duplicate messages 
kafka consumer should be able to be idempotent ==>

receive the same message multiple times but process it 1 time 


techniques to avoid duplicate messages : 

Idempotent consumer 
Idempotent Producer
Transactions 
7
### 106 - including a unique id into message header


update products microservice to make it include a unique ID into message header 
this message id ==> read by consumer ms to check if a message with this id was already processed or not 
we can pass a unique id in a message header 

to work with message headers : 
include unique id into message header before sending this message 
