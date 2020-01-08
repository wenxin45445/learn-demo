package com.meng.pay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.*;
import com.meng.config.AlipayConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alipay")
public class AlipayController {

    private static Logger logger = LoggerFactory.getLogger(AlipayController.class);

    @Autowired
    private AlipayConfig alipayConfig;

    /**
     * 付款
     * @param WIDout_trade_no 商户订单号，商户网站订单系统中唯一订单号，必填
     * @param WIDtotal_amount 付款金额，必填
     * @param WIDsubject 订单名称，必填
     * @param WIDbody 商品描述，可空
     */
    @PostMapping("/pay")
    public String pay(@RequestParam String WIDout_trade_no, @RequestParam String WIDtotal_amount,
                    @RequestParam String WIDsubject, @RequestParam String WIDbody){

        logger.info("[pay] pay start.......");
        logger.error("[pay] pay start.......");
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getGatewayUrl(), alipayConfig.getAppId(), alipayConfig.getPrivateKey(), "json", alipayConfig.getCharset(), alipayConfig.getPublicKey(), alipayConfig.getSignType());

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(alipayConfig.getReturnUrl());
        alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ WIDout_trade_no +"\","
                + "\"total_amount\":\""+ WIDtotal_amount +"\","
                + "\"subject\":\""+ WIDsubject +"\","
                + "\"body\":\""+ WIDbody +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        String result = null;
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            logger.error("[pay] 用户支付异常,原因：{}", e.getMessage(), e);
        }

        //输出
        logger.info("[pay] 用户支付流程完成，支付结果：{}", result);
        return  result;
    }

    /**
     *  交易查询
     * @param WIDTQout_trade_no 商户订单号，商户网站订单系统中唯一订单号
     * @param WIDTQtrade_no 支付宝交易号
     * @return
     */
    @RequestMapping("/query")
    public String query(@RequestParam String WIDTQout_trade_no, @RequestParam String WIDTQtrade_no){
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getGatewayUrl(), alipayConfig.getAppId(), alipayConfig.getPrivateKey(), "json", alipayConfig.getCharset(), alipayConfig.getPublicKey(), alipayConfig.getSignType());

        //设置请求参数
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();

       //请二选一设置
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ WIDTQout_trade_no +"\","+"\"trade_no\":\""+ WIDTQtrade_no +"\"}");

        //请求
        String result = null;
        try {
            result = alipayClient.execute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            logger.error("[query] 交易查询异常,原因：{}", e.getMessage(), e);
        }

        //输出
        logger.info("[query] 交易查询流程完成，交易查询结果：{}", result);
        return  result;
    }

    /**
     * 退款
     * @param WIDTRout_trade_no 商户订单号，商户网站订单系统中唯一订单号
     * @param WIDTRtrade_no 支付宝交易号
     * @param WIDTRrefund_amount 需要退款的金额，该金额不能大于订单金额，必填
     * @param WIDTRrefund_reason 退款的原因说明
     * @param WIDTRout_request_no 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
     * @return
     */
    @RequestMapping("/refund")
    public String refund(@RequestParam String WIDTRout_trade_no, @RequestParam String WIDTRtrade_no, @RequestParam String WIDTRrefund_amount,
                         @RequestParam String WIDTRrefund_reason, @RequestParam String WIDTRout_request_no){
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getGatewayUrl(), alipayConfig.getAppId(), alipayConfig.getPrivateKey(), "json", alipayConfig.getCharset(), alipayConfig.getPublicKey(), alipayConfig.getSignType());

        //设置请求参数
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ WIDTRout_trade_no +"\","
                + "\"trade_no\":\""+ WIDTRtrade_no +"\","
                + "\"refund_amount\":\""+ WIDTRrefund_amount +"\","
                + "\"refund_reason\":\""+ WIDTRrefund_reason +"\","
                + "\"out_request_no\":\""+ WIDTRout_request_no +"\"}");

        //请求
        String result = null;
        try {
            result = alipayClient.execute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            logger.error("[query] 退款异常,原因：{}", e.getMessage(), e);
        }

        //输出
        logger.info("[query] 退款流程完成，退款结果：{}", result);
        return  result;
    }

    /**
     * 退款查询
     * @param WIDRQout_trade_no 商户订单号，商户网站订单系统中唯一订单号
     * @param WIDRQtrade_no 支付宝交易号
     * @param WIDRQout_request_no 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号，必填
     * @return
     */
    @RequestMapping("/refund/query")
    public String refundQuery(@RequestParam String WIDRQout_trade_no, @RequestParam String WIDRQtrade_no, @RequestParam String WIDRQout_request_no){
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getGatewayUrl(), alipayConfig.getAppId(), alipayConfig.getPrivateKey(), "json", alipayConfig.getCharset(), alipayConfig.getPublicKey(), alipayConfig.getSignType());

        //设置请求参数
        AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ WIDRQout_trade_no +"\","
                +"\"trade_no\":\""+ WIDRQtrade_no +"\","
                +"\"out_request_no\":\""+ WIDRQout_request_no +"\"}");

        //请求
        String result = null;
        try {
            result = alipayClient.execute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            logger.error("[query] 退款查询异常,原因：{}", e.getMessage(), e);
        }

        //输出
        logger.info("[query] 退款查询流程完成，退款查询结果：{}", result);
        return  result;
    }

    /**
     * 交易关闭
     * @param WIDTCout_trade_no 商户订单号，商户网站订单系统中唯一订单号
     * @param WIDTCtrade_no 支付宝交易号
     * @return
     */
    @RequestMapping("/close")
    public String close(@RequestParam String WIDTCout_trade_no, @RequestParam String WIDTCtrade_no){
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getGatewayUrl(), alipayConfig.getAppId(), alipayConfig.getPrivateKey(), "json", alipayConfig.getCharset(), alipayConfig.getPublicKey(), alipayConfig.getSignType());

        //设置请求参数
        AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();

        //请二选一设置
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ WIDTCout_trade_no +"\"," +"\"trade_no\":\""+ WIDTCtrade_no +"\"}");

        //请求
        String result = null;
        try {
            result = alipayClient.execute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            logger.error("[query] 交易关闭异常,原因：{}", e.getMessage(), e);
        }

        //输出
        logger.info("[query] 交易关闭流程完成，交易关闭结果：{}", result);
        return  result;
    }

}
