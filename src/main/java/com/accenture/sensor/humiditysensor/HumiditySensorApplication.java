package com.accenture.sensor.humiditysensor;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.stream.IntStream;

@Log4j2
@SpringBootApplication
public class HumiditySensorApplication implements CommandLineRunner {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(HumiditySensorApplication.class, args);
    }

    @Override
    public void run(String... args) {
        IntStream.range(0, args.length).forEach(i -> log.info("args[{}]: {}", i, args[i]));
    }

    private static void printBeans(ApplicationContext ctx) {
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) System.out.println(beanName);
    }
}
