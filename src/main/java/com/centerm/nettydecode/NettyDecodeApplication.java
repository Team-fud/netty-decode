package com.centerm.nettydecode;

import com.centerm.nettydecode.server.HttpServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Sheva
 */
@SpringBootApplication
public class NettyDecodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyDecodeApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        new HttpServer().start();
//    }
}
