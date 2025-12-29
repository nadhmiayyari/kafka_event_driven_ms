### 8 : messages and events in kafka 
convention when naming convention :
noun-performedActionEvent ; 
example ; productCreatedEvent , productDeletedEvent , productUpdartedEvent 

message versus event : 

message = message envelope that carries something inside and the event data 
and the event data are the content of this message 
event data can be a simple string value 



message : message key + event ( bytes ) string , json , avro , or null 
, it is important to have timestamp 
because there can be use cases where you need to know at what time in the history event took place 


for example : 

headers :
headers is a list of key value paris that you can use to include additional
metadata information to your kafka message .



### 9 : topics and partitions 

topic is partitioned : e
ach partition is replicated across multiple kafka servers for durability 
each topic ==> will have a unique name , topic is split into 3 partitions 
 

consuming microservices can read data from topic partitions ins parallel , this can help increase throughput ,
scale our application and make our system work faster 


### - ordering of events in Apache Kafka

If we publish one more event with exactly the same message key , 
kafka will store it in the same partition as other events that have the same message key 
this is how kafka helps us to achieve ordering of events 

if a consumer microservice reads from kafka topic , the order in which it consumes the messages ==> will be the same as they were persisted 
