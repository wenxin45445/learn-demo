package com.meng.java.spi;

public class UserServiceSpiImp1 implements UserServiceSpi {
    public String getUser(String userName) {
        return "user:" + userName;
    }
}
