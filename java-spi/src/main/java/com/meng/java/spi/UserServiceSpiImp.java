package com.meng.java.spi;

public class UserServiceSpiImp implements UserServiceSpi {
    public String getUser(String userName) {
        return "user:" + userName;
    }
}
