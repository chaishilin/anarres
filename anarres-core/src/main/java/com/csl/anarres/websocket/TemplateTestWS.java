package com.csl.anarres.websocket;

import com.csl.anarres.dto.ProgramTemplateDto;
import com.csl.anarres.interfaces.impl.NumberGenetateIterator;
import com.csl.anarres.service.ProcessIterator;
import org.springframework.stereotype.Component;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/21 9:41
 * @Description:
 */
@Component
@ServerEndpoint("/testTemplate/{id}")
public class TemplateTestWS extends BaseWebSocket {

    public void run(ProgramTemplateDto dto) {
        try {
            ProcessIterator processIterator = new NumberGenetateIterator();
            while (processIterator.hasNext()){
                String msg = processIterator.next();
                sendMsg(dto.getUserId(),msg);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //todo 有没有方法强制要求子类实现至少一个带有@onopen注解的函数？
    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        addSession(id,session);
        super.logger.info("websocket add "+id);
    }
}