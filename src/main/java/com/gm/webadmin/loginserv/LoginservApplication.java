package com.gm.webadmin.loginserv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class LoginservApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginservApplication.class, args);
    }

}
