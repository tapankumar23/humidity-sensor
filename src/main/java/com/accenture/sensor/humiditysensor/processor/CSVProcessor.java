package com.accenture.sensor.humiditysensor.processor;

import com.accenture.sensor.humiditysensor.executor.Executor;
import com.accenture.sensor.humiditysensor.executor.ParallelExecutor;
import com.accenture.sensor.humiditysensor.model.Result;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVProcessor {

    private static final char DEFAULT_SEPARATOR = ',';
    private static final int SKIP_LINES = 1;
    private static final int LIMIT = 100;
    private final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");

    @Autowired
    private Executor executor;

    public static void main(String[] args) throws IOException {
        String path = "/Users/tapankumar/dev/projects/accenture/humidity-sensor-java/src/test/resources/all";
        CSVProcessor pro = new CSVProcessor();
        pro.executor = new ParallelExecutor();
        pro.process(path);
    }

    public Result process(String directoryPath) throws IOException {
        Result result = new Result();
        List<Path> files = getInputFiles(directoryPath);
        List<String[]> validSensors = new ArrayList<>();
        List<String[]> invalidSensors = new ArrayList<>();
        for (Path path : files) {
            parseCSVFile(path, validSensors, invalidSensors);
        }

        Map<String, Integer> res1 = executor.execute(validSensors);
        res1.entrySet().forEach(entry -> System.out.println(entry.getKey() + " @VALID@ " + entry.getValue()));
        Map<String, Integer> res2 = executor.execute(invalidSensors);
        res2.entrySet().forEach(entry -> System.out.println(entry.getKey() + " #INVALID# " + entry.getValue()));

        result.setFileCount(files.size());
        result.setTotalCount(validSensors.size() + invalidSensors.size());
        result.setFailedCount(invalidSensors.size());

        return null;
    }

    private List<Path> getInputFiles(String directoryPath) throws IOException {
        final Path directory = Path.of(directoryPath);
        if (!Files.isDirectory(directory)) throw new IllegalArgumentException("Wrong input directory");

        List<Path> pathList;
        try (Stream<Path> stream = Files.walk(directory)) {
            pathList = stream.map(Path::normalize)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".csv"))
                    .collect(Collectors.toList());
        }
        return pathList;
    }

    public void parseCSVFile(Path filePath, List<String[]> validSensors, List<String[]> invalidSensors) throws IOException {
        try (var fr = new FileReader(filePath.toString(), StandardCharsets.UTF_8);
             var reader = new CSVReader(fr)) {
            String[] data;
            reader.readNext();
            while ((data = reader.readNext()) != null) {
                if (NUMBER_PATTERN.matcher(data[1]).matches()) {
                    validSensors.add(data);
                } else {
                    invalidSensors.add(data);
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        /*var builder = new CsvToBeanBuilder(new FileReader(filePath.toString()))
                .withType(HumiditySensorData.class)
                .withSeparator(DEFAULT_SEPARATOR)
                .withSkipLines(SKIP_LINES)
                .build();
        return builder.parse();*/
    }
}
