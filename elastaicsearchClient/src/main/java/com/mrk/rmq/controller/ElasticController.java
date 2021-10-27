package com.mrk.rmq.controller;

import com.mrk.rmq.model.House;
import com.mrk.rmq.service.ElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/elastic")
public class ElasticController {

    @Autowired
    private ElasticService elasticService;

    @GetMapping("/create")
    public void create(){
        elasticService.createIndex("house");
    }

    @GetMapping("/add")
    public void add(){
        List<House> list =new ArrayList<>();
        list.add(new House(7L,"XX0193","XX8064",1000));
        list.add(new House(8L,"XX0210","XX7475",8766));
        list.add(new House(9L,"XX0257","XX8097",3000));
        elasticService.saveAll(list);
    }

    @GetMapping("/all")
    public Iterator<House> all(){
        return elasticService.findAll();
    }

}
