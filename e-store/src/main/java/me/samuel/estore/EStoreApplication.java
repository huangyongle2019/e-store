package me.samuel.estore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("me.samuel.estore.*.mapper")
public class EStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(EStoreApplication.class, args);
    }

}
