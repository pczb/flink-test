package com.tigerzhang.kafka;

import com.google.common.collect.Lists;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.weaving.LoadTimeWeaverAware;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tigerzhang on 2018/3/15.
 */
@Component
public class ClientTest implements LoadTimeWeaverAware {
    @Override
    public void setLoadTimeWeaver(LoadTimeWeaver loadTimeWeaver) {
        System.out.println("aaaaaa" + loadTimeWeaver);
    }

    public static void main(String[] args) {
        Map config = new HashMap();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.77.29.163:9092,10.77.29.164:9092,10.77.31.210:9092,10.77.31.211:9092,10.77.31.212:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "hotwb.cfrecTest");
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        config.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 5000);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());


        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(config);
        consumer.subscribe(Lists.newArrayList("system.mweibo_photo_info"));
        ConsumerRecords record = consumer.poll(5000);
        record = consumer.poll(5000);
        record = consumer.poll(5000);
    }
}
