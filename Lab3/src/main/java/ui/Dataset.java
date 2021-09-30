package ui;

import ui.util.csv.Record;

import java.util.List;

/**
 * @author MatejCubek
 * @project UILab3
 * @created 12/05/2021
 */
public interface Dataset extends Iterable<Record> {
    /**
     * Getter for row/record.
     * Getter returns pairs containing column name and value for index.
     *
     * @param index index of row
     * @return list of pairs with column name and values
     */
    Record getRecord(int index);

    /**
     * Method splits dataset on column columnName with value columnValue.
     *
     * @param columnName  name of column to split by
     * @param columnValue value of which collumn will be returned
     * @return new Dataset with filtered values
     */
    Dataset splitByColumnValue(String columnName, String columnValue);

    /**
     * Method splits dataset on column into a list of subsets.
     *
     * @param columnName name of column to split by
     * @return list of spit subsets
     */
    List<Dataset> splitByColumnToList(String columnName);

    /**
     * Method returns all diferent values for specified column.
     *
     * @param columnName column for which to get info
     * @return list of column values
     */
    List<String> getColumnValues(String columnName);

    /**
     * Method returns all types of columns in dataset.
     *
     * @return all types/names of columns.
     */
    List<String> getColumnTitles();

    /**
     * Method that gets all the class labels in the dataset and puts them in a list.
     *
     * @return list of all class labels
     */
    List<String> getClassLabels();

    /**
     * Method that all the class labels in the dataset and returns a sorted list without duplicates.
     *
     * @return sorted list without duplicates of all class labels
     */
    List<String> getDistinctSortedClassLabels();

    /**
     * Returns number of rows
     *
     * @return rownumber
     */
    int rowNumber();

    /**
     * Method that calculates if dataset is classified.
     * Dataset is classified if all classLabels are the same value.
     *
     * @return <code>true</code> if dataset is classified, else <code>false</code>.
     */
    boolean isClassified();

    /**
     * Method that returns <code>true</code> if dataset is empty or contains no columns other
     * then classLabel.
     *
     * @return <code>true</code> if dataset is empty else <code>false</code>.
     */
    boolean areAllAttributesUsed();

    /**
     * Method calculates entropy and returns it for current dataset.
     *
     * @return entropy
     */
    double entropy();

    /**
     * Method finds and returns the majority classlabel.
     * That classLabel is the one that occurs most often and if there are
     * more with same occurance the one first alphabetically.
     *
     * @return majority classlabel
     */
    String getMajorityClassLabel();

    /**
     * Method finds and returns column values for column name in argument.
     * Column values are sorted by occurance and then by natural order.
     *
     * @param columnName column name for which column names are generated
     * @return list of m
     */
    List<String> getSortedColumnValues(String columnName);
}
