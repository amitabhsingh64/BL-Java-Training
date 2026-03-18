package org.amitabh.fundoobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FundooBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FundooBackendApplication.class, args);
    }

}
