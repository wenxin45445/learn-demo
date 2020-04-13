package com.meng.pay.controller;

import com.meng.config.AlipayConfig;
import com.meng.config.User;
import org.aspectj.lang.annotation.RequiredTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private AlipayConfig alipayConfig;

    /**
     *
     */
    @PostMapping("/test/sb")
    public String test(User user) {
        String result = "";
        if (user != null) {
            result = user.toString();
        }
        return result;
    }
}
