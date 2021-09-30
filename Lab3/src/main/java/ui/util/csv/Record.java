package ui.util.csv;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class representing a row/record in csv.
 *
 * @author MatejCubek
 * @project UILab3
 * @created 12/05/2021
 */
public class Record {
    private final Map<String, String> fields;
    private final String classLabelKey;

    /**
     * Constructor that sets fiels array as the one in the arugment.
     *
     * @param fields list of field pairs
     */
    private Record(String classLabelKey, Map<String, String> fields) {
        this.fields = fields;
        this.classLabelKey = classLabelKey;
    }

    public Record(String classLabelKey) {
        this(classLabelKey, new HashMap<>());
    }

    /**
     * Adds the column to record
     * @param title title of column
     * @param value column value
     */
    public void addColumn(String title, String value) {
        fields.put(title, value);
    }

    /**
     * Returns list of column titles without class label.
     *
     * @return list of column titles without class label.
     */
    public List<String> getColumnTitles() {
        var list = new ArrayList<>(fields.keySet());
        list.remove(classLabelKey);
        return list;
    }

    /**
     * Returns the number of columns without class label.
     *
     * @return number of columns withou class label.
     */
    public int getFieldCount() {
        return fields.size() - 1;
    }

    /**
     * Gets the value for class label in current record.
     *
     * @return value for class label
     */
    public String getClassLabelValue() {
        return fields.get(classLabelKey);
    }

    /**
     * Gets the value for column for columnTitle in argument
     *
     * @param columnTitle title of column
     * @return value for column
     */
    public String getValueForColumnLabel(String columnTitle) {
        return fields.get(columnTitle);
    }

    /**
     * Method deletes column from record with given name and returns
     * a new record with that column deleted.
     *
     * @param columnName name of column to delete
     */
    public Record deleteColumn(String columnName) {
        Map<String, String> newField = new HashMap<>(fields);
        newField.remove(columnName);
        return new Record(classLabelKey, newField);
    }
}

