package com.accenture.sensor.humiditysensor;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@SpringBootApplication
public class HumiditySensorApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(HumiditySensorApplication.class, args);
    }

    @Override
    public void run(String... args) {
        for (int i = 0; i < args.length; ++i)
            log.info("args[{}]: {}", i, args[i]);
    }
}
