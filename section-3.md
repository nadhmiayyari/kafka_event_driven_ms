### Kafka CLI topics 


kafka command line interface :  

the number of partitions must be equal or greater than the number of consumers 
if we create a topic with 1 partition only than we can have only 1 consumer to work with it 


kafka-topics.sh --create --topic topic1 --partitions 3 --replication-factor 1 --bootstrap-server localhost:9092 

replication factor specifies how many copies of each partition are stored across different brokers 
replicaiton factor == 3 => eeach partitioon will have 3 replicas 
it cannot be greater than the number of brokers that you have in your cluster 


###23 list and describe kafka topics 


kafka-topics --list --bootstrap-server localhost:9092

./kafka-topics --list --bootstrap-server localhost:9092


kafka-topics --describe --bootstrap-server localhost:9092


Topic: dealers TopicId: UIxz1w5MR4eMvHhD1--rRA PartitionCount: 1       ReplicationFactor: 1    Configs:
Topic: dealers  Partition: 0    Leader: 1       Replicas: 1     Isr: 1
Topic: dealer-events    TopicId: fd1p4cjEQxCYgW-6nx4Kjw PartitionCount: 1       ReplicationFactor: 1    Configs:
Topic: dealer-events    Part ition: 0    Leader: 1       Replicas: 1     Isr: 1
Topic: test     TopicId: pmVvYhQyTMKvcq6tcLwU7w PartitionCount:1       ReplicationFactor: 1    Configs:
Topic: test     Partition: 0    Leader: 1       Replicas: 1     Isr: 1
Topic: product-created-events-topic     TopicId: rMR-9YnJRja_s3y8Q859sQ PartitionCount: 1       ReplicationFactor: 1    Configs:
Topic: product-created-events-topic     Partition: 0    Leader: 1       Replicas: 1     Isr: 1
Topic: __consumer_offsets       TopicId: kffw1O6nTza-2m8aClvwAA PartitionCount: 50      ReplicationFactor: 1    Configs: compre

topic name , topic id , how many partitions , replication factor 

and the configured maximum size of a topic segment 


Kafka stores messages in files that are called logs ,
these logs are divided into smaller pieces that are called segments each 
segment has a maximum size that you can set with segments bytes configuration 

when a segment reaches its max size , it is closed and a new one is created , so this is a configured 
we also see that each topic is divied into partitons 

ISR :: in sync replicas which are up to date with the leader 


### 24 - delete kafka topics 


kafka-topics --delete --topic 

we can configure this in the server configuration file : because by default the value of this property is true 
