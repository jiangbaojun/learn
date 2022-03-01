package com.mrk.ws.ws4;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangbaojun
 * @version v1.0
 * @workid 1861
 * @date 2022/1/18 11:35
 */
public class UserConstant {
    static User u1 = new User("u1","123","token1");
    static User u2 = new User("u2","123","token2");
    static Map<String, User> users = new HashMap();
    static {
        users.put(u1.getToken(), u1);
        users.put(u2.getToken(), u2);
    }
}
