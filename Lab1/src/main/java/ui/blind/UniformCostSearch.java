package ui.blind;

import ui.common.ISearchAlgorithm;
import ui.common.NodeCost;
import ui.common.SearchProblem;

import java.util.*;

/**
 * Search algorithm implementing {@link ISearchAlgorithm} with Uniform-Cost-Search
 * blind approach. States and their values and costs come as {@link SearchProblem}
 * objects.
 *
 * @author MatejCubek
 * @project UILab1
 * @created 25/03/2021
 */
public class UniformCostSearch<T extends Comparable<T>> implements ISearchAlgorithm<T> {
    private final SearchProblem<T> searchProblem;
    private int visitedStates = - 1;

    public UniformCostSearch(SearchProblem<T> searchProblem) {
        this.searchProblem = searchProblem;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public NodeCost<T> search(T startingState) {
        Queue<NodeCost<T>> open = new PriorityQueue<>(NodeCost.COMPARATOR_BY_COST.thenComparing(NodeCost.COMPARATOR_BY_VALUE));
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

            var set = searchProblem.getSuccessorsSortedByCostName(state);
            if (set == null) continue;

            for (var next : set) {
                if (! closed.contains(next.getState())) {
                    open.add(new NodeCost<>(currentNode, next.getState(), currentNode.getCost() + next.getCost()));
                }
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
        return "UCS";
    }
}
