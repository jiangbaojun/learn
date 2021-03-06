package com.mrk.ws.ws5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 点对点发送消息，将消息发送到指定用户
     */
    @MessageMapping("/test")
    public void sendUserMessage(Principal principal, MessageBody messageBody) {
        messageBody.setFrom(principal.getName());
        simpMessagingTemplate.convertAndSendToUser(messageBody.getTargetUser(), messageBody.getDestination(), messageBody);
    }

}
