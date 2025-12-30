

Idempotent kafka producer 

Idempotence : 
having a producer   ------------------------------- Broker

                send message A ----> kafka topic
               X <------------ ACK

It is possible that a network error can take place and this ack will not reach kafka producer 

if producer retries are configured ==> the producer will send the same message again 



the problem here is that we can have 2 identical messages in kafka topic 

in some applications , duplicate messages might not be a very big problem but in some applications this will be a very big problem 

idempotent kafka producer avoids duplicate messages in the log in the presence of failures and retries 


to enable this ===> set enable.idempotence = true

spring.kafka.producer.properties.enable.idempotence=true
by default this configuration property is already true 


to disable idempotence 
you can set the following configuration to conflicting values 

acks = all 

retries = 2147483647

max.in.flight.requests.per.connection=5


all retries must be greater than 0 and maximum in flight requests per connection must be equal or less than  5 

kafka producer can send up to 5 requests or batches of messages to broker at the same time without waiting ack 

==> 

if you enable idempotence but set acks to different value ,
than your application will throw a configuration exception if you try to produce a message 

