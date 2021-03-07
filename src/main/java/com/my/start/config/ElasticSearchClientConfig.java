package com.my.start.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;

/**
 * @Author duxiaopeng
 * @Date 2021/3/6 2:15 下午
 * @Description ElasticSearchClientConfig
 */
@Configuration
public class ElasticSearchClientConfig {

    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient restHighLevelClient =
                new RestHighLevelClient(
                        RestClient.builder(
                                new HttpHost("127.0.0.1", 9200, "http")));
        return restHighLevelClient;
    }
}
