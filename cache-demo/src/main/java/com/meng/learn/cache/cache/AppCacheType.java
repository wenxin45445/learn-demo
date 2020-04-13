package com.meng.learn.cache.cache;

public enum  AppCacheType {
    IZUUL(10),

    MUMU(5);

    private int expires;

    AppCacheType(int expires) {
        this.expires = expires;
    }

    public int getExpires() {
        return expires;
    }
}
