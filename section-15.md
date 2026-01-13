Apache kafka transactions 
    why use transactions in apache kafka?
### all or nothing behavior 
 ==> either all kafka operations within transactions succeed or none of them , 
 if one operation fails then none of the messages is committed 

exactly once ==> only 1 time *
 

transactions ensure that consumer does not read message that is part of incomplete transaction


typical kafka app ==>
consume ==> process ==> produce 
Transfers topic ==> Transfer microservice
( transfer certain amount of money from one account to another account)

when a new event gets recorded to transfer topic ==> then transfer microservice will consume it then 
transfer microservice will consume it and send it 
it is common that the same microservice application will act as consumer and producer at the same time 
so while processing this kafka message 
transfer microservice might produce a new message to another topic ===> withdrawals topic .

to read messages from withdrawal topic ==> you will have another microservice called withdraw 


withdrawal microservice ===> will read messages from withdrawal topic AND withdraw the request amount of money from user s account 

your transfer microservice will send a new kafka message 



something happens app crashes and did not finish processing  ==> message for kafka will be delivered again 
when this message is consumed again ==> producer API in this microservice will publish a new message to withdrawals topic 


==> we requested to withdraw money from user's account twice , but deposited one time only 

when we enable transactions in this microservices))> all operations will be executed within one transaction 

kafka producer will publish a new message to a withdrawal topic  , but because this transaction did not complete yet  . 

withdrawal microservice  will not see this message yet 
we will configure the deposit microservice to consume only those messages that have been successfully committed.
until transaction is complete successfully message in kafka is marked as uncommitted and uncommitted messages will not be visible to kafka 

at this moment , if the transfer microservice crashes or if an exception took place then transaction will be aborted and message will remain in topic marked as 
uncommitted 


to enable kafka transactions : go to application.properties 


spring.kafka.producer.transaction-id-prefix=


when we use this property , spring framework will create and configure  kafka transaction manager for us automatically 


by default it rolls back transactiond for unchecked exceptions and for errors 