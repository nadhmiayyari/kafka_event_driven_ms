in the previous section of this course , we looked how to configure our kafka consumer to cover from deserialization error 

product microservice will publish a kafka event : product created event to kafka topic 

Products microservice acts as kafka producer and it uses json serializer to serialize messages in json format 


on the right side , having email notifiucation microservice that acts as kafka consumer 

it also expects messages in Json format , and it uses Json deserializer to convert these messages from json to java Object using product created event Java Class 

now when another team publishes a kafka event to that topic with diffferent frtomat , our consumer will raise a deserializationError 

 ==> unless we handle this exceeption it will be taking place again and again 
 

we used ErrorHandlingDeserializer Class to enable our consumer microservice to recover from that message 

this is where the dead letter topic comes in ===>

it is a place where we send messages that failed , to be processed due to some error 
so instead of silently forgetting about bad messages 
we will send them to a dead letter topic so that we can look at these messages later and decide what we want to do with them later 



by default the name of the dead letter topic is just the same as the name of the current topic with the extension DLT