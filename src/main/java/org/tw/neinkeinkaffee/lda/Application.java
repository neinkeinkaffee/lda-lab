package org.tw.neinkeinkaffee.lda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID());
        SpringApplication.run(Application.class, args);
    }
}