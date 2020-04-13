package com.meng.learn.cache.controller;

import com.meng.learn.cache.service.CaffeineServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GuavaController {

    @Autowired
    private CaffeineServer caffeineServer;

    @RequestMapping("/testCache")
    @ResponseBody
    public String testCache(){
        return "success";
    }

    @RequestMapping("/getCache")
    @ResponseBody
    public String getCache(String key){
        caffeineServer.cacheIZUUL(key);
        return "success";
    }
}
