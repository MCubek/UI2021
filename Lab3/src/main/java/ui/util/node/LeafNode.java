package ui.util.node;

import ui.Dataset;

/**
 * TreeNode type depicting a leaf in the tree
 *
 * @author MatejCubek
 * @project UILab3
 * @created 12/05/2021
 */
public class LeafNode<T> extends TreeNode<T> {
    private final T classField;

    public LeafNode(T columnValue, Dataset dataset, T classField) {
        super(columnValue, dataset);
        this.classField = classField;
    }

    public T getClassField() {
        return classField;
    }

    @Override
    public void visit(ITreeNodeVisitor<T> visitor) {
        visitor.visitLeafNode(this);
    }
}
