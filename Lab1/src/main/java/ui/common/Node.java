package ui.common;

import java.util.Comparator;

/**
 * Node class parametrized by type of state.
 * State needs to implement comparable interface for correct implementation.
 * Contains helpful methods for obtaining path of AI search algorithms
 * and method for obtaining langth of said path.
 *
 * @author MatejCubek
 * @project UILab1
 * @created 25/03/2021
 */
public class Node<T extends Comparable<T>> {
    protected Node<T> parent;

    protected T state;

    public Node(Node<T> parent, T state) {
        this.parent = parent;
        this.state = state;
    }

    public Node<T> getParent() {
        return parent;
    }

    public T getState() {
        return state;
    }

    @Override
    public String toString() {
        return state.toString();
    }

    /**
     * Method for returning path of node family tree starting with oldest parent to
     * most recent child.
     *
     * @return Family tree as string with "=>" as separator
     */
    public String nodePath() {
        StringBuilder sb = new StringBuilder();
        nodePathRecursive(sb, this);
        return sb.toString();
    }

    /**
     * Method for returnng size of node family.
     *
     * @return Size of node family
     */
    public int nodePathLength() {
        Node<T> node = this;
        int count = 1;
        while (node.parent != null) {
            count++;
            node = node.parent;
        }
        return count;
    }

    /**
     * Helper method for calculating path of node tree.
     *
     * @param sb   stringbuilder for building path
     * @param node current node
     * @param <X>  parameter of node value
     */
    private static <X extends Comparable<X>> void nodePathRecursive(StringBuilder sb, Node<X> node) {
        if (node.parent != null) {
            nodePathRecursive(sb, node.parent);
            sb.append(" => ");
        }
        sb.append(node.state);
    }

    /**
     * Comparator for Node class comparing them by their value.
     * Used often for comparing by alphabetical order.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public final static Comparator<Node> COMPARATOR_BY_VALUE =
            Comparator.comparing(o -> o.state);
}
