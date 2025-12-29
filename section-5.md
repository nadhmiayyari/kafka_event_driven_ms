### 31 - kafka consume messages from the beginning 

kafka-console-consumer  --topic product-created-events-topic --from-beginning --bootstrap-server 
localhost:9092

as soon as the message is sent to the topic , all consumers that are subscribed to read messages will consume that message


in order to consume messages and print their keys:
kafka-console-consumer  --topic product-created-events-topic --bootstrap-server localhost:9092
--property print.key=true
--property print.value=true




### 34 

in order to consume kafka messages in order , we will have to use message key and add it 
