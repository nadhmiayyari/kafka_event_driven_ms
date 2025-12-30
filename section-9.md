each message will be stored in its own partition
each parition is a row , you decide by yourself how many partition to create 

7
when you start a consumer it starts pulling messages from kafka topic at a regular time 

when kafka consumer reads messages from partitions it reads them in parallel ,
there is no order guarantee which messages from partitions will be read first
but it reads messages in order within a single partition 

there is no order guarantee that messages from partition one will always be read before messages 

but if you have three consumers running ==> each ocnsumer will be assigned to read messages from 






but if you have 3 consumers then each consumer will be assigned to read messages from one partition only 

consumers can be grouped to work as a group 
==> instead of one one consumer ==> you will have 3 consumers reading messages from the same topic


kafka topic is configured to keep messages for 168 hours ==> 7 days 