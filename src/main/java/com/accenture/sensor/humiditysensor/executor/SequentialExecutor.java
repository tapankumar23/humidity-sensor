package com.accenture.sensor.humiditysensor.executor;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
 * Map Reduce
 * */
public class SequentialExecutor extends Executor {

    @Override
    public Map<String, Integer> run(List<String[]> sensorData) {
        Map<String, Integer> result = new TreeMap<>();
        sensorData.forEach(data -> merge(result, data));
        return result;
    }

    private Integer merge(Map<String, Integer> result, String[] data) {
        return result.merge(data[0], "NaN".equals(data[1]) ? 1 : Integer.parseInt(data[1]), Integer::sum);
    }
}
