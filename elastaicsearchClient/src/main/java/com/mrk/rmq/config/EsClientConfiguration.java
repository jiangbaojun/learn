package com.mrk.rmq.config;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EsClientConfiguration {

    @Autowired
    ApplicationContext applicationContext;

    // 高级客户端
    @Bean
    @ConditionalOnMissingBean
    RestHighLevelClient elasticsearchRestHighLevelClient(RestClientBuilder restClientBuilder) {
        return new RestHighLevelClient(restClientBuilder);
    }

    // 低级客户端
    @Bean
    @ConditionalOnMissingBean
    RestClient elasticsearchRestClient(RestClientBuilder builder,
                                       ObjectProvider<RestHighLevelClient> restHighLevelClient) {
        RestHighLevelClient client = restHighLevelClient.getIfUnique();
        if (client != null) {
            return client.getLowLevelClient();
        }
        return builder.build();
    }
}
