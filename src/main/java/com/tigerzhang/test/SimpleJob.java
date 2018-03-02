package com.tigerzhang.test;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;

import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by tigerzhang on 2017/8/4.
 */
public class SimpleJob {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(1000, CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        env.setStateBackend(new FsStateBackend("file:///data0/checkpoint"));

        Properties properties = new Properties();
        properties.load(SimpleJob.class.getClassLoader().getResourceAsStream("kafka.properties"));

        FlinkKafkaConsumer010<String> consumer010 = new FlinkKafkaConsumer010<>("hotWeiboEncode", new SimpleStringSchema(), properties);
        consumer010.setCommitOffsetsOnCheckpoints(false);
        consumer010.setStartFromEarliest();

        DataStream<ActionLog> stream = env.addSource(consumer010).
                map(ActionLog::parse).
                filter(Objects::nonNull).setParallelism(2);

        DataStream<Tuple2<String, String[]>> stream1 = stream.keyBy(ActionLog::getMid)
                .countWindow(100).apply(((key, globalWindow, iterable, collector) -> {
                    Set<String> uids = StreamSupport.stream(iterable.spliterator(), false)
                            .map(ActionLog::getUid)
                            .collect(Collectors.toSet());
                    collector.collect(new Tuple2<>(key, uids.toArray(new String[0])));
        }));

        stream1.writeUsingOutputFormat(new RedisPFAddSink("localhost", 6379));

        env.execute();
    }
}
