package com.meng.game.card.controller;

import com.meng.game.card.service.WebSocketMgtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

@Controller
public class LoginController {

    @Autowired
    private WebSocketMgtService webSocketMgtService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String login(String username, String password, HttpSession httpSession){
        Session session = webSocketMgtService.getSession(httpSession.getId());
        webSocketMgtService.kickOffClient("0");
        System.out.println("username: " + username + " password:" + password);
        return "server got you, username: " + username + " password:" + password;
    }
}
