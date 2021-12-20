package com.csl.anarres.websocket;

import org.springframework.stereotype.Controller;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/20 16:12
 * @Description:
 */
@Controller
@ServerEndpoint("/testTemplate")
public class TestTemplate {
    private static CopyOnWriteArraySet<Session> SessionSet = new CopyOnWriteArraySet<Session>();

    @OnOpen
    public void onOpen(Session session){
        System.out.println("新朋友" + session.getId());
        SessionSet.add(session);
    }

    @OnMessage
    public void showMessage(Session session,String msg){
        System.out.println(msg);
    }

    @OnClose
    public void onClose(Session session){
        SessionSet.remove(session);
    }

    public void boardCastMsg(String msg){
        for(Session session : SessionSet){
            try {
                session.getBasicRemote().sendText(msg);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
