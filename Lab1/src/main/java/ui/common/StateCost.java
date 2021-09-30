package ui.common;

import java.util.Comparator;

/**
 * Class containg state and cost for getting to that state.
 *
 * @author MatejCubek
 * @project UILab1
 * @created 25/03/2021
 */
public class StateCost<T extends Comparable<T>> {

    private final T state;

    private final double cost;

    public StateCost(T state, double cost) {
        this.state = state;
        this.cost = cost;
    }

    public T getState() {
        return state;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return String.format("(%s,%f)", state, cost);
    }

    /**
     * Comparator for Node class comparing them by their value.
     * Used often for comparing by alphabetical order.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public final static Comparator<StateCost> COMPARATOR_BY_STATE =
            Comparator.comparing(o -> o.state);

    /**
     * Comparator for Node class comparing them by their value.
     * Used often for comparing by alphabetical order.
     */
    @SuppressWarnings({"rawtypes"})
    public final static Comparator<StateCost> COMPARATOR_BY_COST =
            Comparator.comparing(o -> o.cost);
}
