package ui.common;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Comparator;
import java.util.Locale;

/**
 * Ckass extending {@link Node} with a cost assosiated to it's current location.
 *
 * @author MatejCubek
 * @project UILab1
 * @created 25/03/2021
 */
public class NodeCost<T extends Comparable<T>> extends Node<T> implements Comparable<NodeCost<T>> {

    protected double cost;

    public NodeCost(Node<T> parent, T state, double cost) {
        super(parent, state);
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    /**
     * Get cost of node as a string formatted to one decimal place.
     *
     * @return String of cost.
     */
    public String getFormattedCost() {
        DecimalFormat df = new DecimalFormat("0.0");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        return df.format(cost);
    }

    @Override
    public int compareTo(NodeCost<T> o) {
        return Double.compare(this.cost, o.cost);
    }

    /**
     * Comparator for Node class comparing them by their value.
     * Used often for comparing by alphabetical order.
     */
    @SuppressWarnings({"rawtypes"})
    public final static Comparator<NodeCost> COMPARATOR_BY_COST =
            Comparator.comparing(o -> o.cost);
}
