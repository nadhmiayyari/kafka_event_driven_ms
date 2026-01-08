

Kafka transactions 
exactly once : 
idempotent producer 
Transaactions 

a typical kafka application consists of this pattern 

Consume -> process -> produce

Kafka topic ( from which kafka consumer will consume messages ) 

when a new event gets recorded to a transfer topic ==> 
transfer microservice will consume it and will start processing it 

it is common that the same microservice application
acts as a consumer and a producer at the same time 

example : 

Transfers Topic -----> Transfer Microservice -----> Withdrawals Topic 

to read messages from withdrawals topic ==> you will have another consumer microservice that is called withdraw microservice 



if the transfer microservice did not finish processing this message successfully 
and to kafka this message in kafka topic is not considered as successfully consumed ==>
and this means that it will be delivered again to another active instance of this microservice
==> in this case , when this message is consumed again producer api in this microservice 
will publish a new message  to withdrawal again 


then it will produce another message to a different topic that is called deposits and deposits microservice will be the one that 
to process it .
 


enabling transactions in transfer microservice the following withdrawals and deposits operations will 
be executed within one 

Kaf