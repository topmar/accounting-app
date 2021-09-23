package com.topolski.accountingapp;

import lombok.ToString;
import net.bytebuddy.build.ToStringPlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class AccountingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountingAppApplication.class, args);
    }

}
