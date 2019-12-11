package com.meng.dubbo.services.user;

public class UserServiceImp implements UserService {
    public String getUser(String userName) {
        return "user:" + userName;
    }
}
