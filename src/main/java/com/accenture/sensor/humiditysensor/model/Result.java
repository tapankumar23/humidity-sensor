package com.accenture.sensor.humiditysensor.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Component
public class Result {

    private Map<String, Aggregation> sensors = new HashMap<>();

    private Integer failedCount;

    private Integer totalCount;

    private Integer fileCount;

    private String resultStr = """
            Num of processed files: $fileCount
            Num of processed measurements: $totalCount
            Num of failed measurements: $failedCount
                                
            Sensors with highest avg humidity:
                                
            sensor-id,min,avg,max
            $multiline
            """;

    private String getResultString(){
        String result = "";
        resultStr.replace("$fileCount", fileCount.toString());
        resultStr.replace("$totalCount", totalCount.toString());
        resultStr.replace("$failedCount", failedCount.toString());
        return result;
    }
}
