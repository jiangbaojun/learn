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
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * ???????????????
 */
public class TestHighRestClient {
    private String INDEX_NAME = "mess";

    static {
        //??????????????????
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
                System.out.println("????????? -> " + node);
            }
        });
        this.restHighClient = new RestHighLevelClient(restClientBuilder);
    }

    @AfterEach
    public void after() throws IOException {
        restHighClient.close();
    }

    /**
     * ????????????
     */
    @Test
    public void create() throws Exception {
        // 1????????? ????????????request ??????????????????mess
        CreateIndexRequest request = new CreateIndexRequest(INDEX_NAME);

        // 2??????????????????settings
        request.settings(Settings.builder().put("index.number_of_shards", 3) // ?????????
                .put("index.number_of_replicas", 2) // ?????????
                .put("analysis.analyzer.default.tokenizer", "ik_max_word") // ???????????????
        );

        // 3??????????????????mappings
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

        // 4??? ?????????????????????
        request.alias(new Alias("mmm"));
        // 5??? ????????????
        // 5.1 ????????????????????????
        CreateIndexResponse createIndexResponse = restHighClient.indices()
                .create(request, RequestOptions.DEFAULT);

        // 6???????????????
        boolean acknowledged = createIndexResponse.isAcknowledged();
        boolean shardsAcknowledged = createIndexResponse
                .isShardsAcknowledged();
        System.out.println("acknowledged = " + acknowledged);
        System.out.println("shardsAcknowledged = " + shardsAcknowledged);

        // 5.1 ????????????????????????
            /*ActionListener<CreateIndexResponse> listener = new ActionListener<CreateIndexResponse>() {
                @Override
                public void onResponse(
                        CreateIndexResponse createIndexResponse) {
                    // 6???????????????
                    boolean acknowledged = createIndexResponse.isAcknowledged();
                    boolean shardsAcknowledged = createIndexResponse
                            .isShardsAcknowledged();
                    System.out.println("acknowledged = " + acknowledged);
                    System.out.println(
                            "shardsAcknowledged = " + shardsAcknowledged);
                }

                @Override
                public void onFailure(Exception e) {
                    System.out.println("?????????????????????" + e.getMessage());
                }
            };

            client.indices().createAsync(request, listener);
            */
    }

    /**
     * ??????????????????
     */
    @Test
    public void indexes() throws Exception {
//        GetIndexRequest request = new GetIndexRequest("_all");
//        GetIndexRequest request = new GetIndexRequest("*");
        GetIndexRequest request = new GetIndexRequest("frame.*","vspn*");
        //????????????????????????????????????
        request.indicesOptions(request.indicesOptions().fromOptions(true, true, true, false));
        GetIndexResponse indexResponse = restHighClient.indices().get(request, RequestOptions.DEFAULT);
        String[] indices = indexResponse.getIndices();
        for(String index: indices){
            System.out.println(index);
        }
    }

    /**
     * ??????????????????
     */
    @Test
    public void indexExist() throws Exception {
        GetIndexRequest request = new GetIndexRequest(INDEX_NAME);
        boolean exists = restHighClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * ????????????
     */
    @Test
    public void indexDelete() throws Exception {
        restHighClient.indices().delete(new DeleteIndexRequest(INDEX_NAME), RequestOptions.DEFAULT);
    }

    /**
     * ???????????????????????????
     */
    @Test
    public void insert() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("id", "2003");
        data.put("title", "????????????2003 ???????????? ????????????1");
        data.put("content", "??????2003");
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
     * ???????????????????????????
     */
    @Test
    public void insertAsync() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("id", "2003");
        data.put("title", "???????????? ???????????? ????????????");
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
     * ????????????
     * ??????????????????????????????bulk?????????????????????
     */
    @Test
    public void insertBatch() {
        BulkRequest request = new BulkRequest();
        for (int i=1;i<=100;i++){
            Map<String, Object> data = new HashMap<>();
            data.put("id", "id"+i);
            data.put("title", "???????????? ???????????? ????????????"+i);
            data.put("price", i*100);
            //????????????
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
     * ??????????????????
     */
    @Test
    public void testExists() throws Exception {
        GetRequest getRequest = new GetRequest(INDEX_NAME, "50"); // ??????????????????
        // ??????????????????
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        boolean exists = restHighClient.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println("exists -> " + exists);
    }


    /**
     * ????????????
     */
    @Test
    public void testUpdate() throws Exception {
        UpdateRequest updateRequest = new UpdateRequest(INDEX_NAME, "50");
        Map<String, Object> data = new HashMap<>();
        data.put("title", "???????????????");
        data.put("price", "5000");
        updateRequest.doc(data);
        UpdateResponse response = restHighClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println("version -> " + response.getVersion());
    }

    /**
     * ??????
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
     * ????????????
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
     * ????????????????????????
     */
    @Test
    public void deleteByQuery() {
        DeleteByQueryRequest request = new DeleteByQueryRequest(INDEX_NAME);
        QueryBuilder builder = QueryBuilders.termQuery("title", "???");
        request.setQuery(builder);
        //????????????????????????,?????????10000
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
     * ??????????????????
     */
    @Test
    public void testQuery() throws Exception {
        GetRequest getRequest = new GetRequest("house",
                "005");
        // ?????????????????????
        String[] includes = new String[]{"title", "id"};
        String[] excludes = Strings.EMPTY_ARRAY;
        FetchSourceContext fetchSourceContext =
                new FetchSourceContext(true, includes, excludes);
        getRequest.fetchSourceContext(fetchSourceContext);
        GetResponse response = restHighClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println("?????? -> " + response.getSource());
    }

    /**
     * ??????
     */
    @Test
    public void testSearch() throws Exception {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //????????????builder
        sourceBuilder.query(QueryBuilders.matchQuery("title", "??????"));
        //??????
        sourceBuilder.from(0);
        sourceBuilder.size(20);
        //??????
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //????????????_source??????
//        sourceBuilder.fetchSource(false);
        //????????????????????????
        String[] includeFields = new String[] {"title", "id"};
        String[] excludeFields = new String[] {"_type"};
        sourceBuilder.fetchSource(includeFields, excludeFields);

        //????????????,????????????????????????????????????????????????keyword??????????????????
        sourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.DESC));

        searchRequest.source(sourceBuilder);
        SearchResponse search = restHighClient.search(searchRequest, RequestOptions.DEFAULT);

        //????????????
        System.out.println("????????? " + search.getHits().getTotalHits().value + " ?????????.");
        SearchHits hits = search.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * ????????????
     */
    @Test
    public void testSearchHighlight() throws Exception {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //????????????builder
        sourceBuilder.query(QueryBuilders.matchQuery("title", "2003"));
        sourceBuilder.from(0);
        sourceBuilder.size(20);
        // ????????????
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //????????????????????????
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.field("title").field("content")
                .preTags("<span style='color:red'>").postTags("</span>");
        //????????????????????????????????????????????????
        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("id");
        highlightTitle.preTags("<strong>").postTags("</strong>");
        highlightBuilder.field(highlightTitle);
        HighlightBuilder.Field highlightContent = new HighlightBuilder.Field("price");
        highlightContent.preTags("<b>").postTags("</b>");
        highlightBuilder.field(highlightContent).requireFieldMatch(false);

        //??????highlight
        sourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighClient.search(searchRequest, RequestOptions.DEFAULT);

        //????????????
        if(RestStatus.OK.equals(searchResponse.status())) {
            //??????????????????????????????
            SearchHits hits = searchResponse.getHits();
            long totalHits = hits.getTotalHits().value;
            System.out.println("????????? " + totalHits + " ?????????.");

            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                String index = hit.getIndex();
                String id = hit.getId();
                float score = hit.getScore();

                //???_source?????????
                //String sourceAsString = hit.getSourceAsString(); //??????json???
                Map<String, Object> sourceAsMap = hit.getSourceAsMap(); // ??????map??????
                System.out.println("index:" + index + "???id:" + id);
                System.out.println("sourceMap : " +  sourceAsMap);
                //???????????????
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                HighlightField highlight = highlightFields.get("title");
                if(highlight != null) {
                    Text[] fragments = highlight.fragments();  //??????????????????????????????
                    if(fragments != null) {
                        String fragmentString = fragments[0].string();
                        System.out.println("title highlight : " +  fragmentString);
                        //?????????????????????????????????sourceAsMap??????????????????????????????????????????
                        //sourceAsMap.put("title", fragmentString);
                    }
                }

                highlight = highlightFields.get("content");
                if(highlight != null) {
                    Text[] fragments = highlight.fragments();  //??????????????????????????????
                    if(fragments != null) {
                        String fragmentString = fragments[0].string();
                        System.out.println("content highlight : " +  fragmentString);
                        //?????????????????????????????????sourceAsMap??????????????????????????????????????????
                        //sourceAsMap.put("content", fragmentString);
                    }
                }
            }
        }

    }
}
