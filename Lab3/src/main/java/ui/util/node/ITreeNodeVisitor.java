package ui.util.node;

/**
 * @author MatejCubek
 * @project UILab3
 * @created 12/05/2021
 */
public interface ITreeNodeVisitor<T> {
    void visitInnerNode(InnerNode<T> node);

    void visitLeafNode(LeafNode<T> node);
}
