package com.csl.anarres.utils;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/19 17:16
 * @Description:
 */
@Component
public class KafkaUtil {
    private KafkaProducer kafkaProducer;
    private  KafkaUtil(){
    }
    public KafkaProducer<String, String> getKafkaProducerInstance(){
        if(kafkaProducer == null){
            Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("acks", "all");
            props.put("retries", 1);
            props.put("batch.size", 10);
            props.put("key.serializer", StringSerializer.class.getName());
            props.put("value.serializer", StringSerializer.class.getName());
            return new KafkaProducer<String, String>(props);
        }else{
            return kafkaProducer;
        }
    }
    //     kafkaProducer.send(new ProducerRecord<String, String>(topic,value));
}
