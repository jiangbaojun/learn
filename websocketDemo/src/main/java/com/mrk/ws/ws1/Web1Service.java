package com.mrk.ws.ws1;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangbaojun
 * @version v1.0
 * @workid 1861
 * @date 2022/1/18 09:07
 */
public class Web1Service {

    static Map<String, Session> sessions = new HashMap();

    public static void pushSession(String userId, Session session) {
        sessions.put(userId, session);
    }

    public static void sendMsg(String id, String msg) throws IOException {
        Session session = sessions.get(id);
        session.getBasicRemote().sendText(msg);
    }
}
