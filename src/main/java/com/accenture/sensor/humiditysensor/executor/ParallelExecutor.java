package com.accenture.sensor.humiditysensor.executor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

/*
 * Map Reduce
 * */
public class ParallelExecutor extends Executor {

    @Override
    public Map<String, Integer> run(List<String[]> sensorData) {
        ForkJoinPool pool = new ForkJoinPool();
        RecursiveAggregatorTask task = new RecursiveAggregatorTask(sensorData, threshold);
        pool.execute(task);
        return task.join();
    }
}
