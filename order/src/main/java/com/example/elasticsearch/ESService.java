package com.example.elasticsearch;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;

import javax.enterprise.event.Observes;
import java.io.IOException;

public class ESService {

    private RestHighLevelClient client;

    void startup(@Observes StartupEvent startupEvent) {
        client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

    void shutdown(@Observes ShutdownEvent shutdownEvent) {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void index(String index, String json) {
        IndexRequest ir = new IndexRequest(index).source(json, XContentType.JSON);
        try {
            client.index(ir, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
