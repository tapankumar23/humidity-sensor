package com.accenture.sensor.humiditysensor.executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public abstract class Executor {

    @Value("${sensor.threshold}")
    protected Integer threshold = 1000;

    public abstract Map<String, Integer> run(List<String[]> sensorData);

    public Map<String, Integer> execute(List<String[]> sensorData) {
        Executor executor = (sensorData.size() < threshold) ? new SequentialExecutor() : new ParallelExecutor();
        return executor.run(sensorData);
    }
}
