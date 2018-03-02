package com.tigerzhang.test;

import org.apache.flink.api.common.io.OutputFormat;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import redis.clients.jedis.Jedis;

import java.io.IOException;

/**
 * Created by tigerzhang on 2017/8/8.
 */
public class RedisPFAddSink implements OutputFormat<Tuple2<String, String[]>> {

    private String host;
    private int port;

    private transient Jedis jedis;

    public RedisPFAddSink(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void configure(Configuration configuration) {
        jedis =  new Jedis(host, port);
    }

    @Override
    public void open(int i, int i1) throws IOException {
    }

    @Override
    public void writeRecord(Tuple2<String, String[]> tuple2) throws IOException {
        jedis.pfadd(tuple2.f0, tuple2.f1);
    }

    @Override
    public void close() throws IOException {

    }
}
