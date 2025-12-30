Kafka producer ACK and Retries 

when kafka producer sends a message ,this message is sent to kafka broker
Kafka broker stores this message in kafka topic

the message is stored in one of the partitions  
if we have more than one broker and proper replication factor configured ==> topic partitions will be copied over to the other brokers as well 

1 broker is a leader , and it is the first one to receive kafka messages 

it receives messages and then copies this message over to the other brokers that act as followers 


once leader broker stores the message succesfully ==> sends ACK to the producer by edfault 


what if leader broker goes down before sharing this message with its followers and never comes back ? 
==> this message is lost ==> 

we can configure kafka producer to wait for ACK from other brokers as well ( follower brokers )

wait for ack from all in-sync replicas

if the message is not critical you can configure your producer to not wait for any ack at all 

to configure your kafka producer to wait for ACK from all brokers , you will use the following property 


'spring.kafka.producer.acks = all' 
waits for ACK from all brokers 

in this case kafka producer will wait for a full set of in-sync replicas to acknoweldge the record 
this option provides the strongest durability guarantees because no data will no data will be lost as long as at least one in sync replica is alive 

to configure a kafka producer to wait for acknowldegment from a leader                            *


spring.kafka.producer.acks = 1 : wait for ack from the leader broker only 

this option is a a trade off between latency and durability 

it ensures that kafka message has been received and stored by the leader broker but it doesnt guarantee the the message has been replicated to the follower brokers 

some data may be lost if leader broker fails before followers catch up ,
so this option is faster than waiting for   all brokers to acknoweldge , ==> less reliable 



set to 0 ==> not to wait for any ack at all 
this option is when to send real time messages for GPS application 


does this make producer slower ?


no =)===> kafka producer will wait for ACK not just from any broker , but from in sync replicas 

you can have more brokers ==> but how many replicas your topic partitions will have will depend on the replication factor configured 
and you configure replication factor at the time when creating a topic 

so kafka producer will wait fro ACK not just from any broker , but only from in sync replicas 
the minimum in sync replicas equals to 2 ==> this will make kafka producer work faster ; it doesnt wait for ACK from 5 in sync replicas 

                                                              



###  Kafka producer retries 
                           
you can configure minimum number of in sync replicas that must respond with ACK

3 ==> kafka producer will wait for ACK from the leader and will wait for ACK from 2 in sync replicas 

but what if one of the followers goes down and we don't have this in sync replica at the moment    ?
the default behavior of the kafka producer is to retry the send operation for a very large number of times 

or until the delivery timeout is reached 
the default timeout is 2 minutes

so if one of the brokers is not available and producer receives a retrial error ==> it will retry to deliver the msg again and again 

until 2 minutes or until it reaches number of times 

 
after sending we have no repsonse ==> the producer is configured with ACK = 0

ACK of successfull storage 
but if the error takes places ==> then it can receive either non-writable error or retryable error 


non retryable error ==> permanent unlikely to be resolved by retries ,
if the message size is too large and it exceeds the maximum limit   


retryable error : temporary problem that can be resolved by retrying the send operation 
 for example if there is a network error , or leader is unavailable 

kafka producer will check what kind of error it is 
                                                              
### 55 - kafka producer retries 

>spring.kafka.producer.retries = 10

how many times kafka producer will try to send a message before marking it as failed
the default value is very large ==> it will retry infinitely


>spring.kafka.producer.properties.retry.backoff.ms=1000

 
how long the producer will wait before attempting to retry a failed request ==> default is 100ms

instead of controlling the number of times to retry , we are encouraged to control the maximum time kafka producer can 
spend trying to send a message

spring.kafka.producer.properties.delivery.timeout.ms=120000

the maximum time producer can spend trying to deliver the message ==> default value is 120000( 2 minutes)
producer will wait for a message to be delivered before raising a timeout exception , 
and this includes :  time to send request    + time it waits for ACKs from all in-sync replicas + time it spends retrying the send operation 

during these 2 minutes , kafka producer will retry sending message again and again 


value of the delivery timeout ms must be >= linger.ms and request.timeout.ms 

linger.ms ==     configure the maximum time producer will wait before sending a batch of messages to kafka 
0 ==> as soon as it receives a message it will send it 

spring.kafka.producer.properties.linger.ms = 0 ==> the maximum time in milliseconds , that the producer will wait and buffer data before sending a batch of messages ; 
default value is 0 

                                 
spring.kafka.producer.properties.request.timeout.ms = 30000 

the maximum time to wait for a response from the broker after sending a request ; default value is 30000



### 57 - min.insync.replicas configuration 
kafka-topics.sh --create --topic insync-topic --partitions 3 --replication-factor 3 --bootstrap-server localhost:9092
--config min.insync.replicas = 2 


or if we already have this kafka-configs.sh --bootstrap-server localhost:9092 --alter --entity-type  topics --entity-name 
--add-config min.insync.replicas = 2 


### 59 - kafka producer retries 


if we set producer.retries = 10 
and set properties.retry.backoff.ms = 1000 ==> 

kafka producer will retry sending message 10 times with 1 second time interval 


### 61 - kafka producer delivery and request timeout 


Instead of controlling the number of retries and the backoff ms ( time will wait 1 second before retrying to send that message  )




63 - configure producer in bean method 











 
 
 
 
 
 
 
 
 



















 
 
 
 
 

