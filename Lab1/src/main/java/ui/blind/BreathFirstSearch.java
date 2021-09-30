package ui.blind;

import ui.common.ISearchAlgorithm;
import ui.common.NodeCost;
import ui.common.SearchProblem;

import java.util.*;

/**
 * Search algorithm implementing {@link ISearchAlgorithm} with Breath-First-Search
 * blind approach. States and their values come in as {@link SearchProblem} type.
 *
 * @author MatejCubek
 * @project UILab1
 * @created 25/03/2021
 */
public class BreathFirstSearch<T extends Comparable<T>> implements ISearchAlgorithm<T> {
    private final SearchProblem<T> searchProblem;
    private int visitedStates = - 1;

    public BreathFirstSearch(SearchProblem<T> searchProblem) {
        this.searchProblem = searchProblem;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public NodeCost<T> search(T startingState) {
        Queue<NodeCost<T>> open = new ArrayDeque<>();
        Set<T> closed = new HashSet<>();

        open.add(new NodeCost<>(null, startingState, 0));


        while (! open.isEmpty()) {
            var currentNode = open.poll();
            T state = currentNode.getState();

            if(!closed.add(state)) continue;

            if (searchProblem.isGoal(state)) {
                visitedStates = closed.size();
                return currentNode;
            }

            var iterable = searchProblem.getSuccessorsSortedByNameOnly(state);
            if (iterable == null) continue;

            for (var next : iterable) {
                if (! closed.contains(next.getState()))
                    open.add(new NodeCost<>(currentNode, next.getState(), currentNode.getCost() + next.getCost()));
            }
        }

        return null;
    }

    @Override
    public NodeCost<T> search() {
        return search(searchProblem.getStart());
    }

    @Override
    public int visitedStates() {
        return visitedStates;
    }

    @Override
    public String getName() {
        return "BFS";
    }
}
