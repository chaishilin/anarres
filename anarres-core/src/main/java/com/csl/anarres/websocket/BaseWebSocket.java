package com.csl.anarres.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/20 16:12
 * @Description:
 */
@Component
public abstract class BaseWebSocket {
    private Logger logger = LoggerFactory.getLogger(BaseWebSocket.class);

    //静态变量，对象之间共享
    private static CopyOnWriteArraySet<Session> sessionSet = new CopyOnWriteArraySet<Session>();

    public static CopyOnWriteArraySet<Session> getSessionSet() {
        return new CopyOnWriteArraySet<>(sessionSet);//返回一份拷贝
    }

    public static void addSessionSet(Session session) {
        sessionSet.add(session);
    }

    public static void removeSessionSet(Session session) {
        sessionSet.remove(session);
    }

    @OnOpen
    public void onOpen(Session session) {
        addSessionSet(session);
        logger.info("websocket add " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable error){
        removeSessionSet(session);
        logger.info("websocket remove " + session.getId());
        error.printStackTrace();
    }

    @OnMessage
    public void showMessage(Session session, String msg) {
        logger.info("websocket get msg:{" + msg + "} from " + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        removeSessionSet(session);
        logger.info("websocket remove " + session.getId());
    }

    public void sendMsg(Session session, String msg) {
        try {
            session.getBasicRemote().sendText(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void boardCastMsg(String msg) {
        for (Session session : sessionSet) {
            try {
                session.getBasicRemote().sendText(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
