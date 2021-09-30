package ui.util.node;

import ui.Dataset;

/**
 * Tree node implementation.
 *
 * @author MatejCubek
 * @project UILab3
 * @created 12/05/2021
 */
public abstract class TreeNode<T> {
    private final T columnValue;
    private final Dataset dataset;

    public TreeNode(T columnValue, Dataset dataset) {
        this.columnValue = columnValue;
        this.dataset = dataset;
    }

    public T getColumnValue() {
        return columnValue;
    }

    public Dataset getDataset() {
        return dataset;
    }


    public abstract void visit(ITreeNodeVisitor<T> visitor);

}
