package ui.util.csv;

import ui.Dataset;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Dataset implementation for using csv files.
 *
 * @author MatejCubek
 * @project UILab3
 * @created 12/05/2021
 */
public class CSVDataset implements Dataset {
    private List<Record> data = new ArrayList<>();

    @SuppressWarnings("FieldCanBeLocal")
    private final String delimiter = ",";

    /**
     * Constructor that creates record from csv with headers
     *
     * @param csvFile csv file
     * @throws IllegalArgumentException when error occurs while parsing file
     */
    public CSVDataset(Path csvFile) throws IllegalArgumentException {
        parseFile(csvFile);
    }


    private CSVDataset(List<Record> data) {
        this.data = data;
    }

    /**
     * Private method that parses file
     *
     * @param csvFile csv file
     * @throws IllegalArgumentException when error occurs while parsing file
     */
    private void parseFile(Path csvFile) throws IllegalArgumentException {
        try (BufferedReader bufferedReader = Files.newBufferedReader(csvFile)) {

            List<String> headers = Arrays.asList(bufferedReader.readLine().split(delimiter));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                var values = line.split(delimiter);
                Record record = new Record(headers.get(headers.size() - 1));

                for (int i = 0; i < values.length; i++) {
                    record.addColumn(headers.get(i), values[i]);
                }
                data.add(record);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("CSV Not Parsable!");
        }
    }

    @Override
    public List<String> getColumnValues(String columnName) {
        return data.stream()
                .map(record -> record.getValueForColumnLabel(columnName))
                .sorted()
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getClassLabels() {
        return data.stream()
                .map(Record::getClassLabelValue)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getDistinctSortedClassLabels() {
        return data.stream()
                .map(Record::getClassLabelValue)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public boolean isClassified() {
        return getClassLabels().stream()
                       .distinct()
                       .count() == 1
               || data.get(0).getFieldCount() == 0;
    }

    @Override
    public boolean areAllAttributesUsed() {
        return data.isEmpty();
    }

    @Override
    public Record getRecord(int index) {
        return data.get(index);
    }

    @Override
    public List<String> getColumnTitles() {
        if (data.size() == 0) return null;
        return data.get(0).getColumnTitles();
    }

    @Override
    public Dataset splitByColumnValue(String columnName, String columnValue) {
        var newDataList = data.stream()
                .filter(record -> columnValue.equals(record.getValueForColumnLabel(columnName)))
                .map(record -> record.deleteColumn(columnName))
                .collect(Collectors.toList());
        return new CSVDataset(newDataList);
    }

    @Override
    public List<Dataset> splitByColumnToList(String columnName) {
        List<Dataset> list = new ArrayList<>();
        for (String columnValue : getColumnValues(columnName)) {
            list.add(splitByColumnValue(columnName, columnValue));
        }
        return list;
    }

    @Override
    public int rowNumber() {
        return data.size();
    }

    @Override
    public double entropy() {
        if (rowNumber() == 0) return 0;

        double entropy = 0;

        ArrayList<Long> frequencies = new ArrayList<>(data.stream()
                .map(Record::getClassLabelValue)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values());

        for (var frequency : frequencies) {
            entropy -= nLogN(1.0 * frequency / rowNumber());
        }

        return entropy;
    }

    @Override
    public String getMajorityClassLabel() {
        return data.stream()
                .map(Record::getClassLabelValue)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.<String, Long>comparingByValue()
                        .thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                .map(Map.Entry::getKey)
                .orElse("");
    }

    @Override
    public List<String> getSortedColumnValues(String columnName) {
        return data.stream()
                .map(record -> record.getValueForColumnLabel(columnName))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue()
                        .thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private double nLogN(double n) {
        return n == 0 ? 0 : n * Math.log(n) / Math.log(2);
    }

    @Override
    public Iterator<Record> iterator() {
        return data.iterator();
    }
}
