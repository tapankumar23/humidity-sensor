package com.accenture.sensor.humiditysensor.processor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.PropertySourcesPropertyResolver;

import java.io.IOException;

@SpringBootTest
public class CSVProcessorTest {

    @Autowired
    private PropertySourcesPropertyResolver propertySourceResolver;

    @Test
    public void testCSVProcessor() throws IOException {
        String path = "/Users/tapankumar/dev/projects/accenture/humidity-sensor-java/src/main/java/com/accenture/sensor/humiditysensor/processor/all";
        CSVProcessor pro = new CSVProcessor();
        pro.process(path);
    }
}
