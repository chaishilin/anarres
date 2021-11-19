package com.csl.anarresLog.log;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/19 16:46
 * @Description:
 */
@Component
public class LogConsumer {
    static {
        System.out.println("hello world! log");
    }

    @PostConstruct
    public void cunsume() {
        System.out.println("kaishixiaofei");
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "112");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("max.poll.records", 1000);
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("test"));
        ConsumerRecords<String, String> msgList = consumer.poll(1000);
        for (ConsumerRecord<String, String> msg : msgList) {
            processLog(msg.value());
        }
        System.out.println("endkaishixiaofei");
    }

    private void processLog(String msg){
        System.out.println("---------");
        System.out.println(msg);
    }
}
