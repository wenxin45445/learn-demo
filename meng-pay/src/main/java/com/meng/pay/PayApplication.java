package com.meng.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.meng.config", "com.meng.pay.controller"})
//@SpringBootApplication(scanBasePackages = {"com.meng.config"})
//@ServletComponentScan(value={"com.meng.pay.controller"})
public class PayApplication {
	public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
	}
}