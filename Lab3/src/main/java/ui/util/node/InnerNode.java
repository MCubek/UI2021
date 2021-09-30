package ui.util.node;

import ui.Dataset;

import java.util.ArrayList;
import java.util.List;

/**
 * Treenode type depicting an inner node.
 *
 * @author MatejCubek
 * @project UILab3
 * @created 12/05/2021
 */
public class InnerNode<T> extends TreeNode<T> {
    private final T columnTitle;
    private final List<TreeNode<T>> children = new ArrayList<>();

    public InnerNode(T columnValue, Dataset dataset, T columnTitle) {
        super(columnValue, dataset);
        this.columnTitle = columnTitle;
    }

    public T getColumnTitle() {
        return columnTitle;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public void addChild(TreeNode<T> child) {
        children.add(child);
    }

    @Override
    public void visit(ITreeNodeVisitor<T> visitor) {
        visitor.visitInnerNode(this);
    }
}
