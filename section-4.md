kafka-console-producer script allows 



### 27 , producing kafka message without a key 

./kafka-console-producer --bootstrap-server localhost:9092 --topic products-created-event-topic 

if you send a message to a topic that does not exist , kafka producer will respond with error 
kafka broker will create the topic , 
the property that enables this is ,
auto.create.topics.enable = true 


### 28 send a message with a key to kafka topic 

stored as key value pair , 
without key ==> key is null , 

one of reason ==> with the same key ==> stored in the same partition , they are ordered 
to send message with a key 

this is to enable key value message support in console producer command
--property "parse.key=true"

--property "key.separator=:"


>./kafka-console-producer --bootstrap-server localhost:9092 --topic product-created-events-topic  --proper
ty "parse.key=true"  --property "key.separator=:"

>test
org.apache.kafka.common.KafkaException: No key separator found on line number 1: 'test'
at kafka.tools.ConsoleProducer$LineMessageReader.parse(ConsoleProducer.scala:381)
at kafka.tools.ConsoleProducer$LineMessageReader.readMessage(ConsoleProducer.scala:356)
at kafka.tools.ConsoleProducer$.main(ConsoleProducer.scala:50)
at kafka.tools.ConsoleProducer.main(ConsoleProducer.scala) 
> 
> 
> error because no key separator > 
