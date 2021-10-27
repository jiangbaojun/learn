package com.mrk.rmq.test;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 高级客户端
 */
public class TestHighRestClient {
    private String INDEX_NAME = "mess";

    static {
        //设置日志级别
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        List<ch.qos.logback.classic.Logger> loggerList = loggerContext.getLoggerList();
        loggerList.forEach(logger -> {
            logger.setLevel(Level.INFO);
        });
    }

    private RestHighLevelClient restHighClient;

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
        this.restHighClient = new RestHighLevelClient(restClientBuilder);
    }

    @AfterEach
    public void after() throws IOException {
        restHighClient.close();
    }

    /**
     * 创建索引
     */
    @Test
    public void create() throws Exception {
        // 1、创建 创建索引request 参数：索引名mess
        CreateIndexRequest request = new CreateIndexRequest(INDEX_NAME);

        // 2、设置索引的settings
        request.settings(Settings.builder().put("index.number_of_shards", 3) // 分片数
                .put("index.number_of_replicas", 2) // 副本数
                .put("analysis.analyzer.default.tokenizer", "ik_max_word") // 默认分词器
        );

        // 3、设置索引的mappings
        request.mapping("  {\n" +
                        "      \"properties\": {\n" +
                        "        \"id\": {\n" +
                        "          \"type\": \"keyword\"\n" +
                        "        },\n" +
                        "        \"title\": {\n" +
                        "          \"type\": \"text\"\n" +
                        "        }\n" +
                        "      }\n" +
                        "  }",
                XContentType.JSON);

        // 4、 设置索引的别名
        request.alias(new Alias("mmm"));
        // 5、 发送请求
        // 5.1 同步方式发送请求
        CreateIndexResponse createIndexResponse = restHighClient.indices()
                .create(request, RequestOptions.DEFAULT);

        // 6、处理响应
        boolean acknowledged = createIndexResponse.isAcknowledged();
        boolean shardsAcknowledged = createIndexResponse
                .isShardsAcknowledged();
        System.out.println("acknowledged = " + acknowledged);
        System.out.println("shardsAcknowledged = " + shardsAcknowledged);

        // 5.1 异步方式发送请求
            /*ActionListener<CreateIndexResponse> listener = new ActionListener<CreateIndexResponse>() {
                @Override
                public void onResponse(
                        CreateIndexResponse createIndexResponse) {
                    // 6、处理响应
                    boolean acknowledged = createIndexResponse.isAcknowledged();
                    boolean shardsAcknowledged = createIndexResponse
                            .isShardsAcknowledged();
                    System.out.println("acknowledged = " + acknowledged);
                    System.out.println(
                            "shardsAcknowledged = " + shardsAcknowledged);
                }

                @Override
                public void onFailure(Exception e) {
                    System.out.println("创建索引异常：" + e.getMessage());
                }
            };

            client.indices().createAsync(request, listener);
            */
    }

    /**
     * 查询所有索引
     */
    @Test
    public void indexes() throws Exception {
//        GetIndexRequest request = new GetIndexRequest("_all");
//        GetIndexRequest request = new GetIndexRequest("*");
        GetIndexRequest request = new GetIndexRequest("frame.*","vspn*");
        //忽略不存在、不可用的索引
        request.indicesOptions(request.indicesOptions().fromOptions(true, true, true, false));
        GetIndexResponse indexResponse = restHighClient.indices().get(request, RequestOptions.DEFAULT);
        String[] indices = indexResponse.getIndices();
        for(String index: indices){
            System.out.println(index);
        }
    }

    /**
     * 是否存在索引
     */
    @Test
    public void indexExist() throws Exception {
        GetIndexRequest request = new GetIndexRequest(INDEX_NAME);
        boolean exists = restHighClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 删除索引
     */
    @Test
    public void indexDelete() throws Exception {
        restHighClient.indices().delete(new DeleteIndexRequest(INDEX_NAME), RequestOptions.DEFAULT);
    }

    /**
     * 新增文档，同步操作
     */
    @Test
    public void insert() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("id", "2003");
        data.put("title", "北京西路2003 拎包入住 一室一厅1");
        data.put("content", "南京2003");
        data.put("price", "8500");
        IndexRequest indexRequest = new IndexRequest(INDEX_NAME).source(data).id("2003");
        IndexResponse indexResponse = restHighClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println("id->" + indexResponse.getId());
        System.out.println("index->" + indexResponse.getIndex());
        System.out.println("version->" + indexResponse.getVersion());
        System.out.println("result->" + indexResponse.getResult());
        System.out.println("shardInfo->" + indexResponse.getShardInfo());
    }

    /**
     * 新增文档，异步操作
     */
    @Test
    public void insertAsync() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("id", "2003");
        data.put("title", "南京东路 最新房源 二室一厅");
        data.put("price", "5500");
        IndexRequest indexRequest = new IndexRequest("house").source(data).id("005");
        restHighClient.indexAsync(indexRequest, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                System.out.println("id->" + indexResponse.getId());
                System.out.println("index->" + indexResponse.getIndex());
                System.out.println("version->" + indexResponse.getVersion());
                System.out.println("result->" + indexResponse.getResult());
                System.out.println("shardInfo->" + indexResponse.getShardInfo());
            }
            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println("ok");
        Thread.sleep(2000);
    }

    /**
     * 批量新增
     * 不要一条条添加，使用bulk方式效率高很多
     */
    @Test
    public void insertBatch() {
        BulkRequest request = new BulkRequest();
        for (int i=1;i<=100;i++){
            Map<String, Object> data = new HashMap<>();
            data.put("id", "id"+i);
            data.put("title", "南京东路 最新房源 二室一厅"+i);
            data.put("price", i*100);
            //添加数据
            request.add(new IndexRequest(INDEX_NAME)
                .id(String.valueOf(i))
                .source(data, XContentType.JSON));
        }
        try {
            restHighClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断是否存在
     */
    @Test
    public void testExists() throws Exception {
        GetRequest getRequest = new GetRequest(INDEX_NAME, "50"); // 不返回的字段
        // 不返回的字段
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        boolean exists = restHighClient.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println("exists -> " + exists);
    }


    /**
     * 更新数据
     */
    @Test
    public void testUpdate() throws Exception {
        UpdateRequest updateRequest = new UpdateRequest(INDEX_NAME, "50");
        Map<String, Object> data = new HashMap<>();
        data.put("title", "更新后数据");
        data.put("price", "5000");
        updateRequest.doc(data);
        UpdateResponse response = restHighClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println("version -> " + response.getVersion());
    }

    /**
     * 删除
     */
    @Test
    public <T> void delete() {
        try {
            DeleteRequest request = new DeleteRequest(INDEX_NAME, "60");
            restHighClient.delete(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量删除
     */
    @Test
    public <T> void deleteBatch() {
        BulkRequest request = new BulkRequest();
        for (int i=1;i<=50;i++){
            request.add(new DeleteRequest(INDEX_NAME, String.valueOf(i)));
        }
        try {
            restHighClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按照查询结果删除
     */
    @Test
    public void deleteByQuery() {
        DeleteByQueryRequest request = new DeleteByQueryRequest(INDEX_NAME);
        QueryBuilder builder = QueryBuilders.termQuery("title", "厅");
        request.setQuery(builder);
        //设置批量操作数量,最大为10000
        request.setBatchSize(10000);
        request.setConflicts("proceed");
        try {
            BulkByScrollResponse bulkByScrollResponse = restHighClient.deleteByQuery(request, RequestOptions.DEFAULT);
            System.out.println(bulkByScrollResponse.getDeleted());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询单条数据
     */
    @Test
    public void testQuery() throws Exception {
        GetRequest getRequest = new GetRequest("house",
                "005");
        // 指定返回的字段
        String[] includes = new String[]{"title", "id"};
        String[] excludes = Strings.EMPTY_ARRAY;
        FetchSourceContext fetchSourceContext =
                new FetchSourceContext(true, includes, excludes);
        getRequest.fetchSourceContext(fetchSourceContext);
        GetResponse response = restHighClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println("数据 -> " + response.getSource());
    }

    /**
     * 搜索
     */
    @Test
    public void testSearch() throws Exception {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //创建查询builder
        sourceBuilder.query(QueryBuilders.matchQuery("title", "南京"));
        //分页
        sourceBuilder.from(0);
        sourceBuilder.size(20);
        //超时
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //是否返回_source字段
//        sourceBuilder.fetchSource(false);
        //设置返回哪些字段
        String[] includeFields = new String[] {"title", "id"};
        String[] excludeFields = new String[] {"_type"};
        sourceBuilder.fetchSource(includeFields, excludeFields);

        //指定排序,要注意排序字段通常是数字、日期、keyword。不允许分词
        sourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.DESC));

        searchRequest.source(sourceBuilder);
        SearchResponse search = restHighClient.search(searchRequest, RequestOptions.DEFAULT);

        //处理结果
        System.out.println("搜索到 " + search.getHits().getTotalHits().value + " 条数据.");
        SearchHits hits = search.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 高亮搜索
     */
    @Test
    public void testSearchHighlight() throws Exception {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //创建查询builder
        sourceBuilder.query(QueryBuilders.matchQuery("title", "2003"));
        sourceBuilder.from(0);
        sourceBuilder.size(20);
        // 高亮设置
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //允许匹配其他字段
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.field("title").field("content")
                .preTags("<span style='color:red'>").postTags("</span>");
        //不同字段可有不同设置，如不同标签
        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("id");
        highlightTitle.preTags("<strong>").postTags("</strong>");
        highlightBuilder.field(highlightTitle);
        HighlightBuilder.Field highlightContent = new HighlightBuilder.Field("price");
        highlightContent.preTags("<b>").postTags("</b>");
        highlightBuilder.field(highlightContent).requireFieldMatch(false);

        //设置highlight
        sourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighClient.search(searchRequest, RequestOptions.DEFAULT);

        //处理响应
        if(RestStatus.OK.equals(searchResponse.status())) {
            //处理搜索命中文档结果
            SearchHits hits = searchResponse.getHits();
            long totalHits = hits.getTotalHits().value;
            System.out.println("搜索到 " + totalHits + " 条数据.");

            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                String index = hit.getIndex();
                String id = hit.getId();
                float score = hit.getScore();

                //取_source字段值
                //String sourceAsString = hit.getSourceAsString(); //取成json串
                Map<String, Object> sourceAsMap = hit.getSourceAsMap(); // 取成map对象
                System.out.println("index:" + index + "，id:" + id);
                System.out.println("sourceMap : " +  sourceAsMap);
                //取高亮结果
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                HighlightField highlight = highlightFields.get("title");
                if(highlight != null) {
                    Text[] fragments = highlight.fragments();  //多值的字段会有多个值
                    if(fragments != null) {
                        String fragmentString = fragments[0].string();
                        System.out.println("title highlight : " +  fragmentString);
                        //可用高亮字符串替换上面sourceAsMap中的对应字段返回到上一级调用
                        //sourceAsMap.put("title", fragmentString);
                    }
                }

                highlight = highlightFields.get("content");
                if(highlight != null) {
                    Text[] fragments = highlight.fragments();  //多值的字段会有多个值
                    if(fragments != null) {
                        String fragmentString = fragments[0].string();
                        System.out.println("content highlight : " +  fragmentString);
                        //可用高亮字符串替换上面sourceAsMap中的对应字段返回到上一级调用
                        //sourceAsMap.put("content", fragmentString);
                    }
                }
            }
        }

    }
}
