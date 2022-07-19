package com.accenture.sensor.humiditysensor.model;

public abstract class Aggregation {

    public abstract Integer average();

    public abstract Aggregation add(Integer value);

    public abstract Aggregation combine(Aggregation with);
}
