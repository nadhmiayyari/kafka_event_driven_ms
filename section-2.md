###  - Leader and follower roles : 

LeaderShip balance : 
leader => handle all write and read requests : manages operations for topic partitions 
any microservice that want to write message ==> go through leader 

once persisted in the topic partition for the leader ,
it will be replicated to the brokers that act like followers 



followers replicate data from the leader in exact order it was written , maintaining data consistency

leader = single source of truth for all writes and reads in topic partitions 
if followers were allowed to accept writes it could lead to inconsistencies and conflicts 


every kafka broker can be a leader and follower at the same time 

each partition has a leader assigned to it ,
and a leader to a partition is assigned when the topic is created 

Kafka through its internal processes assigns a leader for each partition right away 
each partition will have its own leader and followers 