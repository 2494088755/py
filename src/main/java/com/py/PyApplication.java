package com.py;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.py.dao")
@SpringBootApplication
public class PyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PyApplication.class, args);
    }

}
