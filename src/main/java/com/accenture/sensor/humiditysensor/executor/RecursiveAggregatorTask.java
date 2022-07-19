package com.accenture.sensor.humiditysensor.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.RecursiveTask;

public class RecursiveAggregatorTask extends RecursiveTask<Map<String, Integer>> {

    private Integer threshold;
    private List<String[]> taskList;

    public RecursiveAggregatorTask(List<String[]> taskList, Integer threshold) {
        this.threshold = threshold;
        this.taskList = taskList;
    }

    @Override
    protected Map<String, Integer> compute() {
        Map<String, Integer> result;
        if (taskList.size() > threshold) {
            List<RecursiveAggregatorTask> subTasks = createSubtasks();
            map(subTasks);
            result = reduce(subTasks);
        } else {
            result = new SequentialExecutor().run(taskList);
        }

        return result;
    }

    private Map<String, Integer> reduce(List<RecursiveAggregatorTask> subTasks) {
        List<Map<String, Integer>> results = new ArrayList<>();
        subTasks.forEach(task -> results.add(task.join()));
        return mergeResults(results);
    }

    private void map(List<RecursiveAggregatorTask> subTasks) {
        subTasks.forEach(RecursiveAggregatorTask::fork);
    }

    private Map<String, Integer> mergeResults(List<Map<String, Integer>> results) {
        Map<String, Integer> mergedResults = new TreeMap<>();
        for (Map<String, Integer> result : results) {
            result.entrySet().forEach(entry -> {
                mergedResults.merge(entry.getKey(), entry.getValue(), Integer::sum);
            });
        }

        return mergedResults;
    }

    private List<RecursiveAggregatorTask> createSubtasks() {
        List<RecursiveAggregatorTask> dividedTasks = new ArrayList<>();
        int validListSize = taskList.size();
        int subTaskSize = validListSize / threshold;
        int start;
        int end = 0;
        while (subTaskSize-- > 0) {
            start = end;
            end = start + threshold;

            List<String[]> subList = taskList.subList(start, end);
            dividedTasks.add(new RecursiveAggregatorTask(subList, threshold));
        }
        if (validListSize % threshold > 0) {
            start = end;
            end = validListSize;
            List<String[]> subList = taskList.subList(start, end);
            dividedTasks.add(new RecursiveAggregatorTask(subList, threshold));
        }

        return dividedTasks;
    }
}
