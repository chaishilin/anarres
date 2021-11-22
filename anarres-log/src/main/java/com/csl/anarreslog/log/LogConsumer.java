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
    private String topic = "log";
    private KafkaConsumer initKafkaConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "123");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("max.poll.records", 1000);
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        return new KafkaConsumer<String, String>(props);
    }


    @PostConstruct
    public void consume() {
        KafkaConsumer consumer = initKafkaConsumer();
        consumer.subscribe(Arrays.asList(topic));
        while (true) {
            ConsumerRecords<String, String> msgList = consumer.poll(1000);
            for (ConsumerRecord<String, String> msg : msgList) {
                processLog(msg.value());
            }
        }
    }

    private void processLog(String msg) {
        System.out.println("---------");
        System.out.println(msg);
    }
}
