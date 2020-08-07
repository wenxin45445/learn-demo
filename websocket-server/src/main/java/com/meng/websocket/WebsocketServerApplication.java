package com.meng.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.meng.websocket"})
public class WebsocketServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketServerApplication.class, args);
    }

}
