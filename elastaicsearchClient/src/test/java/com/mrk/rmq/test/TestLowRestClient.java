package com.mrk.rmq.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 初级客户端
 */
public class TestLowRestClient {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private RestClient restClient;

    @BeforeEach
    public void init() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials("elastic", "12345678"));
        RestClientBuilder restClientBuilder = RestClient.builder(
                new HttpHost("140.100.135.211", 9200, "http"),
                new HttpHost("140.100.135.211", 9201, "http"),
                new HttpHost("140.100.135.211", 9202, "http"));
        restClientBuilder.setHttpClientConfigCallback(httpClientConfigCallback -> {
            httpClientConfigCallback.disableAuthCaching();
            return httpClientConfigCallback.setDefaultCredentialsProvider(credentialsProvider);
        });
        restClientBuilder.setFailureListener(new RestClient.FailureListener() {
            @Override
            public void onFailure(Node node) {
                System.out.println("出错了 -> " + node);
            }
        });
        this.restClient = restClientBuilder.build();
    }

    @AfterEach
    public void after() throws IOException {
        restClient.close();
    }

    // 查询集群状态
    @Test
    public void testGetInfo() throws IOException {
        Request request = new Request("GET", "/_cluster/state");
        request.addParameter("pretty", "true");
        Response response = this.restClient.performRequest(request);
        System.out.println(response.getStatusLine());
        System.out.println(EntityUtils.toString(response.getEntity()));
    }


    // 新增数据
    @Test
    public void testCreateData() throws IOException {
//        Request request = new Request("POST", "/house/_doc");
        //指定id
        Request request = new Request("POST", "/house/_doc/001");
        Map<String, Object> data = new HashMap<>();
        data.put("id", "2001");
        data.put("title", "test测试1");
        data.put("price", "3200");
        request.setJsonEntity(MAPPER.writeValueAsString(data));
        Response response = this.restClient.performRequest(request);
        System.out.println(response.getStatusLine());
        System.out.println(EntityUtils.toString(response.getEntity()));
    }


    // 根据id查询数据
    @Test
    public void testQueryData() throws IOException {
        Request request = new Request("GET", "/house/_doc/001");
        Response response = this.restClient.performRequest(request);
        System.out.println(response.getStatusLine());
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    // 搜索数据
    @Test
    public void testSearchData() throws IOException {
        Request request = new Request("POST", "/house/_search");
        String searchJson = "{\"query\": {\"match\": {\"title\": \"拎包入住\"}}}";
        request.setJsonEntity(searchJson);
        request.addParameter("pretty", "true");
        Response response = this.restClient.performRequest(request);
        System.out.println(response.getStatusLine());
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

}
