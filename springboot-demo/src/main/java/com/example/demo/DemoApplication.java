package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Map;
import java.util.Scanner;

@SpringBootApplication(scanBasePackages = {"com.example.demo"})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Bean()
    public ApplicationStartJobListener mutiCharacterEncodingFilter() {
        return new ApplicationStartJobListener();
    }

    class ApplicationStartJobListener implements ApplicationListener<ContextRefreshedEvent> {

        @Override
        public void onApplicationEvent(ContextRefreshedEvent event) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String command;
                    while (true) {
                        ThreadPoolClient bean = event.getApplicationContext().getBean(ThreadPoolClient.class);
                        if (bean != null) {
                            while (true) {
                                Scanner scanner = new Scanner(System.in);
                                command = scanner.nextLine();
                                if ("1".equals(command)) {
                                    bean.sayHelloFuture(100, 5000);
                                } else if ("2".equals(command)) {

                                    bean.sayHelloFuture2(100, 8000);
                                } else if ("3".equals(command)) {
                                    break;
                                }
                            }
                        }

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).run();
        }
    }

}
