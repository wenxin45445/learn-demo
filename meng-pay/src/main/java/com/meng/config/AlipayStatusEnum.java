package com.meng.config;

/**
 * @author mengpp
 * 支付宝交易状态枚举值
 */
public enum AlipayStatusEnum {
    /**
     * 交易创建，等待买家付款  false（默认值不触发通知）
     */
    WAIT_BUYER_PAY(1,"交易创建，等待买家付款"),


    /**
     * 未付款交易超时关闭，或支付完成后全额退款 false（默认值不触发通知）
     */
    TRADE_CLOSED(2,"未付款交易超时关闭，或支付完成后全额退款"),


    /**
     * 交易支付成功  true（默认值触发通知）
     */
    TRADE_SUCCESS(3,"交易支付成功"),


    /**
     * 交易结束，不可退款 false（默认值不触发通知）
     */
    TRADE_FINISHED(4,"交易结束，不可退款");


    private int statusCode;
    private String statusMsg;


    private AlipayStatusEnum(int statusCode, String statusMsg){
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    @Override
    public String toString() {
        return "AlipayStatusEnum{" +
                "statusCode=" + statusCode +
                ", statusMsg='" + statusMsg + '\'' +
                '}';
    }
}
