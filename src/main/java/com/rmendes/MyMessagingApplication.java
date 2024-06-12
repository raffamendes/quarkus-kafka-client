package com.rmendes;

import java.time.Duration;
import java.util.Random;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MyMessagingApplication {
    
	//@Outgoing("test-downtime")
    public Multi<Record<Integer, Integer>> generate() {
        return Multi.createFrom().ticks().every(Duration.ofSeconds(1))
        		.onOverflow().drop()
        		.map(tick -> {
        			System.out.println("Sending Message to kafka");
        			return Record.of(new Random().nextInt(5), new Random().nextInt(500));
        		});
    }
	
	@Incoming("test-downtime-consumer")
	public void consume(ConsumerRecord<String, String> message){
		System.out.println("Tópico: "+message.topic()+"\n Key: "+message.key()+" Value: "+message.value());
	}
	
	
	
	
    
    

    
}
