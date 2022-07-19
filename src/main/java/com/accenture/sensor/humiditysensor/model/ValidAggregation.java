package com.accenture.sensor.humiditysensor.model;

public class ValidAggregation extends Aggregation {
    @Override
    public Integer average() {
        return 0;
    }

    @Override
    public Aggregation add(Integer value) {
        return null;
    }

    @Override
    public Aggregation combine(Aggregation with) {
        return null;
    }
}
