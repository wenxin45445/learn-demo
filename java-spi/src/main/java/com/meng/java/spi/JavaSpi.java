package com.meng.java.spi;


import com.meng.dubbo.services.user.UserService;

import java.util.Iterator;
import java.util.ServiceLoader;

public class JavaSpi {


    public static void main(String[] args) {
        Iterator<UserServiceSpi> iterator = ServiceLoader.load(UserServiceSpi.class).iterator();

        for (int i = 1;iterator.hasNext(); i ++ ) {
            UserServiceSpi service = iterator.next();
            System.out.println(service.getUser("pangpang" + i));
        }

        Iterator<UserService> iterator2 = ServiceLoader.load(UserService.class).iterator();

        for (int i = 1;iterator2.hasNext(); i ++ ) {
            UserService service = iterator2.next();
            System.out.println(service.getUser("pangpang" + i));
        }
    }
}
