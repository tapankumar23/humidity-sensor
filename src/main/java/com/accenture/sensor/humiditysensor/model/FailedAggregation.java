package com.accenture.sensor.humiditysensor.model;

public class FailedAggregation extends Aggregation {
    @Override
    public Integer average() {
        return null;
    }

    @Override
    public Aggregation add(Integer value) {
        return switch (value) {
            default -> new ValidAggregation();
        };
    }

    @Override
    public Aggregation combine(Aggregation with) {
        return null;
    }
}
