package com.meng.game.card.bo;

public class WebSocketPermissionBO {
    /**
     * 用户登陆token token <br>
     */
    private String token;

    /**
     * 用户id <br>
     */
    private String userId;

    /**
     * get token
     *
     * @return Returns the token.<br>
     */
    public String getToken() {
        return token;
    }

    /**
     * set token
     *
     * @param token The token to set. <br>
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * get userId
     *
     * @return Returns the userId.<br>
     */
    public String getUserId() {
        return userId;
    }

    /**
     * set userId
     *
     * @param userId The userId to set. <br>
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
