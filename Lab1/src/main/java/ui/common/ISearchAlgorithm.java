package ui.common;

/**
 * Interface for searching path to final state.
 *
 * @author MatejCubek
 * @project UILab1
 * @created 25/03/2021
 */
public interface ISearchAlgorithm<T extends Comparable<T>> {

    /**
     * Start search on provided algorithm.
     *
     * @return final node containing final state and path to it or <code>null</code>
     * if not found
     */
    NodeCost<T> search();

    /**
     * Start search on provided algorithm with provided state
     *
     * @return final node containing final state and path to it or <code>null</code>
     * if not found
     */
    NodeCost<T> search(T state);

    /**
     * Returns number of states on last runned search.
     * If no search have been run returns -1;
     * @return number of visited states or -1 if not run.
     */
    int visitedStates();

    /**
     * Get name of algorithm
     */
    String getName();
}
