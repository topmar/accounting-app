package com.topolski.accountingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class AccountingAppApplication {

    public static void main(final String[] args) {
        SpringApplication.run(AccountingAppApplication.class, args);
    }

}
