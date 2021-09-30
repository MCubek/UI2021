package ui.informed;

import ui.common.Node;
import ui.common.NodeCost;

import java.util.Comparator;

/**
 * Class extending Node with Cost with estimated total cost value.
 * That value shows estimation from starting to final state.
 *
 * @author MatejCubek
 * @project UILab1
 * @created 25/03/2021
 */
public class HeuristicNode<T extends Comparable<T>> extends NodeCost<T> {

    protected double estimatedTotalCost;

    public HeuristicNode(Node<T> parent, T state, double cost, double estimatedTotalCost) {
        super(parent, state, cost);
        this.estimatedTotalCost = estimatedTotalCost;
    }

    public double getEstimatedTotalCost() {
        return estimatedTotalCost;
    }

    /**
     * Comparator for Node class comparing them by their estimated total cost.
     * Used often for comparing by alphabetical order.
     */
    @SuppressWarnings({"rawtypes"})
    final static Comparator<HeuristicNode> COMPARATOR_BY_ESTIMATED_TOTAL_COST =
            Comparator.comparing(o -> o.estimatedTotalCost);
}
