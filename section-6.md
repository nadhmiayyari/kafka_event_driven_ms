the main role of kafka is tom simplify the process of integration kafka 

Kafka producer will serialize messages to binary formats that can be transmitted over the network to kafka consumers 



- specify the name of the topic where the messages are sent 
- will make the decision of which partition is used , if we don't specify the partition , 
- it will be chosen in round robin fashion ensuring a balanced load



### 38 : synchronous communication style :
is when sender sends a request and then waits for a response because it does want to know if it was sent or not ; 

wait for response before continue : 

KafkaTemplate: encapsulates kafka producer and provides a very user friendly way to interact with kafka

more reliable , but because producer is still waiting will consume more time 


asynchronous communication is not blocking 
