package com.csl.anarres.websocket;

import com.csl.anarres.interfaces.impl.TestTemplateIterator;
import com.csl.anarres.service.ProcessIterator;
import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/21 9:41
 * @Description:
 */
@Component
@ServerEndpoint("/testTemplate")
public class TemplateTestWS extends BaseWebSocket {

    public void run() {
        try {
            ProcessIterator processIterator = new TestTemplateIterator();
            while (processIterator.hasNext()){
                String msg = processIterator.next();
                boardCastMsg(msg);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
