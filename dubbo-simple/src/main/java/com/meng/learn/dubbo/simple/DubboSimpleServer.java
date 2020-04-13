package com.meng.learn.dubbo.simple;

import com.meng.dubbo.services.user.UserService;
import com.meng.dubbo.services.user.UserServiceImp;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.io.IOException;

public class DubboSimpleServer {
    public static void main(String[] args) throws IOException {
        new DubboSimpleServer().openServer(12345);
        for (; ; ) {
            int read = System.in.read();
            if (read == 0) {
                System.exit(0);
            }
        }
    }

    public void openServer(int port) {
        ApplicationConfig config = new ApplicationConfig();
        config.setName("simple-app");
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(port);
        protocolConfig.setThreads(20);
        ServiceConfig<UserService> serviceConfig = new ServiceConfig<UserService>();
        serviceConfig.setApplication(config);
        serviceConfig.setProtocol(protocolConfig);
        serviceConfig.setRegistry(new RegistryConfig(RegistryConfig.NO_AVAILABLE));
        serviceConfig.setInterface(UserService.class);
        serviceConfig.setRef(new UserServiceImp());
        serviceConfig.export();

    }
}
