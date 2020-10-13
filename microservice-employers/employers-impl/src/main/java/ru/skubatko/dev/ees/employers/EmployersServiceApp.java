package ru.skubatko.dev.ees.employers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EmployersServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(EmployersServiceApp.class, args);
    }
}
