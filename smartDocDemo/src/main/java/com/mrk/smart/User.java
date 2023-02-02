package com.mrk.smart;

import lombok.Data;

@Data
public class User {
 
    /**
     * 用户名
     * @desc 对于字段是否为空，支持jsr303规范。代码详见：com.power.doc.utils.JavaClassValidateUtil#isJSR303Required(java.lang.String)
     */
//    @NotNull
    private String userName;
 
    /**
     * 昵称
     */
    private String nickName;
 
    /**
     * 用户地址
     */
    private String userAddress;
 
    /**
     * 用户年龄
     */
    private int userAge;
 
    /**
     * 手机号
     */
    private String phone;
 
    /**
     * 创建时间
     */
    private Long createTime;
 
    /**
     * ipv6
     */
    private String ipv6;
 
    /**
     * 固定电话
     */
    private String telephone;
    //省略get set
}