Kafka consumer groups 

how to start multiple kafka consumers and how to make them work together as a group 

example : product ms ==> producer : 

and 3 ms that act like consumers : sms notification service , email notification ,; push notification microservice 

three different microservice will receive a new product created event message 


if we have 3 instances of the email notification microservices ==> now 4 of them are running 

in this case , when a new message is published to a topic , an sms notification microservice will receive its own copy 


when coming to email notification microservice : 
do we want 4  email notification ms to consume exactly the same message ? ==> the answer is no 

even though we have 4 instances of email notification microservice running , we want only one of them to receive a new message 

the message ==>one time only 


kafka allows us to run multiple consumer microservices in a group   : 
very helpful when you have lots of messages in the topic and you need to process them faster 

each group member ==> will pick one message from the topic  ==> they will consumed faster 


when new message ==> 1 time only consumed by the consumer group 


### rebalancing and partition assignment in apache kafka 


3 partitions  : all messages in this kafka topic ==> stored in three separate partitions 

to consume messages from this topic : 

when we have only 1 consumer microservice ==> 
this consumer will read messages from all three partitions 

assuming that there is a large load on our application and one notification microservice is not enough to 


having more instances ==> kafka will reassign partitions among these two consumer applications  
one instance will read messages from one partition and another instance of email notification reads from 2 partitions 

rebalancing  : 



consumer will send hearbeats at irregular time intervals 

when kafka broker notices that there are no heartbeats comoing from consumer ==> it will remove this consumer from the consumer group 



#101 ==> how to add kafka listener to consumer group 

inside @KafkaListener annotation ( add groupId= "product-created-events")


