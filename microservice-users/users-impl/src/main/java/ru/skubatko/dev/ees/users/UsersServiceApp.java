package ru.skubatko.dev.ees.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class UsersServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(UsersServiceApp.class, args);
    }
}
