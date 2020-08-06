package com.meng.game.card;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication(scanBasePackages = {"com.meng.game.card.controller",
        "com.meng.game.card.service",
        "com.meng.game.card.websocket.inweb",
        "com.meng.game.card.netty"
})
@EnableWebSocket
public class WSCardServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WSCardServerApplication.class, args);
//        ApplicationContext context = SpringUtil.getApplicationContext();
//        UserServiceI userServiceI = context.getBean(UserServiceI.class);// 注意是UserServiceI ， 不是UserServiceImpl
//        User user = userServiceI.findById("1234");
    }
}
