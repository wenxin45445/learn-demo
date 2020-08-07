package com.meng.websocket.controller;

import com.meng.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebSocketController {
    @Autowired
    private WebSocketServer webSocketServer;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/webSocket")
    public ModelAndView webSocket(){
        return new ModelAndView("/webSocket");
    }
}
