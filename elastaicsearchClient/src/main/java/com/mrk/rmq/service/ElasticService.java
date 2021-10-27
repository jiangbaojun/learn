package com.mrk.rmq.service;

import com.mrk.rmq.model.House;
import com.mrk.rmq.repository.ElasticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class ElasticService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;
    @Autowired
    private ElasticRepository elasticRepository;

    private Pageable pageable = PageRequest.of(0,10);

    public void createIndex(String key) {
        elasticsearchTemplate.indexOps(IndexCoordinates.of(key)).create();
    }

    public void deleteIndex(String key) {
        elasticsearchTemplate.indexOps(IndexCoordinates.of(key)).delete();
    }

    public void save(House House) {
        elasticRepository.save(House);
    }

    public void saveAll(List<House> list) {
        elasticRepository.saveAll(list);
    }

    public Iterator<House> findAll() {
        return elasticRepository.findAll().iterator();
    }

    public Page<House> findByContent(String content) {
        return elasticRepository.findByContent(content,pageable);
    }

    public Page<House> findByTitle(String title) {
        return elasticRepository.findByTitle(title,pageable);
    }
}
