package com.accenture.sensor.humiditysensor.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Measurement extends SensorData {

    @CsvBindByPosition(position = 0)
    private String sensorId;

    @CsvBindByPosition(position = 1)
    private String humidity;


    @Override
    public String toString() {
        return "HumiditySensorData {" +
                "sensorId='" + sensorId + '\'' +
                ", humidity='" + humidity + '\'' +
                '}';
    }
}
