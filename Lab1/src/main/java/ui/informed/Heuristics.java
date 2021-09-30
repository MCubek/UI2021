package ui.informed;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/**
 * Heuristic helping interface.
 *
 * @author MatejCubek
 * @project UILab1
 * @created 25/03/2021
 */
public interface Heuristics<T> {

    /**
     * Get and return heuristic cost for given state.
     *
     * @param state state for which cost is wanted
     * @return cost for state
     */
    double getHeuristicCost(T state);

    /**
     * Get set of all states ordered by heuristics cost.
     *
     * @return set with states
     */
    List<T> getOrderedStatesByHeuristics();
}
