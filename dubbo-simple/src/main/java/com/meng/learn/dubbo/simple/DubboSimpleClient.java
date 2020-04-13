package com.meng.learn.dubbo.simple;

import com.meng.dubbo.services.user.UserService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;

public class DubboSimpleClient {
    static String remoteUrl = "dubbo://127.0.0.1:12345/com.meng.dubbo.services.user.UserService";

    public static void main(String[] args) {
        new DubboSimpleClient().buildRemoteSerbice(remoteUrl).getUser("dubbo-simple-client");
    }

    public UserService buildRemoteSerbice(String remoteUrl) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("simple-client");
        ReferenceConfig<UserService> referenceConfig = new ReferenceConfig<UserService>();
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setInterface(UserService.class);
        UserService userService = referenceConfig.get();
        return userService;

    }
}
