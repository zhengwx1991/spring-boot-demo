package com.sysware.elasticsearch.service;

import com.alibaba.fastjson.JSONObject;
import com.sysware.elasticsearch.domain.NbaPlayer;
import com.sysware.elasticsearch.mapper.NbaPlayerMapper;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 *
 * @Author: zwx
 * @Company 云迹科技
 * @Date: 2019/10/15 10:26
 */
@Service
public class NbaPlayerServiceImpl implements NbaPlayerService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String NBA_INDEX = "nba_latest";

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private NbaPlayerMapper mapper;


    @Override
    public String addNbaPlayer(NbaPlayer nbaPlayer) throws IOException {
        IndexRequest request = new IndexRequest(NBA_INDEX).id(Integer.toString(nbaPlayer.getId())).source(beanToMap(nbaPlayer));
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        logger.info(JSONObject.toJSONString(response));
        return null;
    }

    @Override
    public void removeNbaPlayer(String id) throws IOException {
        DeleteRequest request = new DeleteRequest(NBA_INDEX, id);
        client.delete(request, RequestOptions.DEFAULT);
    }

    @Override
    public void removeAll() throws IOException {
        DeleteByQueryRequest request = new DeleteByQueryRequest(NBA_INDEX);
        BulkByScrollResponse response = client.deleteByQuery(request, RequestOptions.DEFAULT);
    }

    @Override
    public void updateNbaPlayer(NbaPlayer nbaPlayer) throws IOException {
        UpdateRequest request = new UpdateRequest(NBA_INDEX, Integer.toString(nbaPlayer.getId())).doc(beanToMap(nbaPlayer));
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        logger.info(JSONObject.toJSONString(response));
    }

    @Override
    public Map<String, Object> getNbaPlayer(String id) throws IOException {
        GetRequest request = new GetRequest(NBA_INDEX, id);
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        Map<String, Object> map = response.getSource();
        logger.info(JSONObject.toJSONString(map));
        return map;
    }

    @Override
    public void importAll() throws IOException {
        List<NbaPlayer> list = mapper.selectAll();
        if (list != null && list.size() != 0) {
            for (NbaPlayer player : list) {
                addNbaPlayer(player);
            }
        }
    }

    @Override
    public List<NbaPlayer> searchMatch(String key, String value, int page, int size) throws IOException {
        SearchRequest request = new SearchRequest(NBA_INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery(key, value));
        sourceBuilder.from(page);
        sourceBuilder.size(size);
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHit[] hits = response.getHits().getHits();
        logger.info(JSONObject.toJSONString(hits));
        List<NbaPlayer> playerList = new ArrayList<>();
        if (hits != null) {
            for (SearchHit hit : hits) {
                NbaPlayer player = JSONObject.parseObject(hit.getSourceAsString(), NbaPlayer.class);
                playerList.add(player);
            }
        }
        return playerList;
    }

    @Override
    public List<NbaPlayer> searchTerm(String key, String value, int page, int size) throws IOException {
        SearchRequest request = new SearchRequest(NBA_INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery(key, value));
        sourceBuilder.from(page);
        sourceBuilder.size(size);
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHit[] hits = response.getHits().getHits();
        logger.info(JSONObject.toJSONString(hits));
        List<NbaPlayer> playerList = new ArrayList<>();
        if (hits != null) {
            for (SearchHit hit : hits) {
                NbaPlayer player = JSONObject.parseObject(hit.getSourceAsString(), NbaPlayer.class);
                playerList.add(player);
            }
        }
        return playerList;
    }

    @Override
    public List<NbaPlayer> searchMatchPrefix(String key, String value, int page, int size) throws IOException {
        SearchRequest request = new SearchRequest(NBA_INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.prefixQuery(key, value));
        sourceBuilder.from(page);
        sourceBuilder.size(size);
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHit[] hits = response.getHits().getHits();
        logger.info(JSONObject.toJSONString(hits));
        List<NbaPlayer> playerList = new ArrayList<>();
        if (hits != null) {
            for (SearchHit hit : hits) {
                NbaPlayer player = JSONObject.parseObject(hit.getSourceAsString(), NbaPlayer.class);
                playerList.add(player);
            }
        }
        return playerList;
    }

    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                if (beanMap.get(key) != null) {
                    map.put(key + "", beanMap.get(key));
                }
            }
        }
        return map;
    }
}
