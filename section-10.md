$


Handle deserialization error : 

error handling in kafka consumer $

before an event is stored in kafka topic ==> it is serialized using json serializer 

email notification service as consumer , listens for product created events and when it reads new message from kafka it uses jsonDeserializer to deserialize that message 


let's assume another team in the organization studies called admin microservice and uses string serialize to push a message to the same topic

when the kafka consumer would consume that it will generate an error 


3**** causing the deserialization problem 

Caused by: org.apache.kafka.common.errors.RecordDeserializationException: Error deserializing VALUE for partition products-event-topic-0 at offset 1. If needed, please seek past the record to continue consumption.


when we have an error it will raise this Caused by: org.springframework.kafka.support.serializer.DeserializationException: failed to deserialize 