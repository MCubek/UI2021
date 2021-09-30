package ui.common;

import java.util.List;
import java.util.Set;

/**
 * Interface showing a problem that needs to be solved using algorithms.
 *
 * @author MatejCubek
 * @project UILab1
 * @created 25/03/2021
 */
public interface SearchProblem<T extends Comparable<T> > {

    /**
     * Method that checks if state is goal.
     *
     * @param state state that is checked
     * @return <code>true</code> if is goal else <code>false</code>
     */
    boolean isGoal(T state);

    /**
     * Get starting state for problem.
     *
     * @return starting state
     */
    T getStart();

    /**
     * Method that returns an iterable object that contains all states and
     * their costs that can be traveled to from state in argument.
     *
     * @param state state for which next states are looked for
     * @return next possible states
     */
    Set<StateCost<T>> getSuccessorsSortedByCostName(T state);

    Set<StateCost<T>> getSuccessorsSortedByNameOnly(T state);
}
