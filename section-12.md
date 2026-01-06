Kafka Consumer - exceptions and retries


when we have an exception : 
retryable error : example if consumer is down ==> retry to consume that message 
not retryable : configure kafka consumer to publish message to a dead letter topic 

if error is taking place is not retryable 


if error ius retryable ==> configure wait time , 
configure number of times to retry 

after retrying this operation a number of times ==> the message will be published to a dead letter topic 

 
>@KafkaHandler
>public void handle(ProductCreatedEvent productCreatedEvent){ 
> if(true) throw new NotRetryableException("an error took place , no need to conusme this message again . ");
LOGGER.info("received a new event :" + productCreatedEvent.getTitle());
}
> 
> 

we forced that to throw a notRetryableException ==> we will not consume that message again 
error handler will send this message to dead letter topic 


### adding retryable error 

in order to add retryable error in concurrentKafkaListenerFactory :
DefaultErrorHandler errorHandler = 
new DefaultErrorHandler(new DeadLetterPublishingRecoverer(kafkaTemplate)
,new FixedBackOff(5000,3));

5000 ms to wait before trying again  
3 number of retries 

consumer will wait for 5 seconds ,and after 3 attempts it will be sent to dead letter topic 


to simulate and try a retryable exception : 
we will send a rest request to external microservice and then if in response we receive exception
, then we will catch that exception and throw a retry exception 

kafka consumer will consume the message again , and try to send httpRequuest using a restTemplate ,and use main application fail 

inside Main application 
create a bean that returns an instance of rest template 


catch(ResourceAccessException ex){
LOGGER.error(ex.getMessage());
throw new RetryableException(ex.getMessage());
} inside this we will have an error that corresponds to 