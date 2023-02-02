package com.mrk.smart.controller;


import com.mrk.smart.CommonResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ApiTestDef {

    /**
     * 测试接口
     * @apiNote 测试接口描述
     * @return com.mrk.smart.CommonResult<java.lang.Integer>
     * @date 2023/2/2 11:14
     */
    @PostMapping("/folder/{folderId}/upload-bytes")
    CommonResult<Integer> testApi(@RequestBody byte[] bytes,
                                  @RequestParam("fileName") String fileName,
                                  @PathVariable("folderId") Integer folderId,
                                  @RequestParam(value = "description", required = false) String description,
                                  @RequestParam(required = false)String country,
                                  @RequestParam(required = false)Integer siteId);

}
