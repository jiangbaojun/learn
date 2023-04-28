package com.mrk.smart.controller;

import com.mrk.smart.CommonResult;
import com.mrk.smart.Goods;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 商品管理
 * @author jiangbaojun
 * @date 2023/1/31 15:56
 * @version v1.0
 */
@Controller
public class Test2Controller implements ApiTestDef {

    /**
     * 查询商品信息
     * @param name 名称
     * @param group 分组
     * @return com.mrk.smart.CommonResult<com.mrk.smart.Goods>
     * @date 2022/2/26 15:52
     * @myDoclet 测试值
     */
    @PostMapping("/get/goods")
    public CommonResult<List<Goods>> getGoods(@RequestParam("name") String name, @RequestParam("group") String group){
        return CommonResult.ok().setResult("ok");
    }

    @PostMapping("/get/goods1")
    public CommonResult<List<Goods>> getGoods1(@RequestParam("name") String name, @RequestParam("group") String group){
        return CommonResult.ok().setResult("ok");
    }

    /**
     * 测试接口
     * @apiNote 测试接口实现描述
     * @param bytes 参数1
     * @param fileName 参数2
     * @param folderId 参数3
     * @param description 参数4
     * @param country 参数5
     * @param siteId 参数6
     * @return com.mrk.smart.CommonResult<java.lang.Integer>
     * @date 2023/2/2 11:16
     */
    @Override
    public CommonResult<Integer> testApi(byte[] bytes, String fileName, Integer folderId, String description, String country, Integer siteId) {
        return null;
    }
}
