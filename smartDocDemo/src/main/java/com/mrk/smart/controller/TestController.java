package com.mrk.smart.controller;

import com.mrk.smart.CommonResult;
import com.mrk.smart.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户管理
 * @author jiangbaojun
 * @date 2023/1/31 15:55
 * @version v1.0
 */
@Controller
public class TestController {

    /**
     * 添加用户
     * @apiNote 这是添加用户的说明
     * @author jiangbaojun1
     * @author jiangbaojun11
     * @param user 用户信息
     * @date 2022/2/3 15:50
     */
    @PostMapping("/add")
    public CommonResult<User> addUser(@RequestBody User user){
        return CommonResult.ok().setResult(user);
    }

    /**
     * 删除用户
     * @param id 用户id
     * @return com.mrk.smart.CommonResult<com.mrk.smart.User>
     * @date 2022/2/11 15:50
     */
    @RequestMapping("/del")
    public CommonResult<User> delUser(@RequestParam("id") Integer id){
        return CommonResult.ok().setResult("ok");
    }
}
