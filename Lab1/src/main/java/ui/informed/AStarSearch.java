package ui.informed;

import ui.common.ISearchAlgorithm;
import ui.common.NodeCost;
import ui.common.SearchProblem;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Search algorithm implementing {@link ISearchAlgorithm} using a informed search and
 * the A-star approach. Both the informed search parameters and the state parameters come
 * in a interface type {@link SearchProblem} and {@link Heuristics} parametrized by
 * type of state value.
 *
 * @author MatejCubek
 * @project UILab1
 * @created 25/03/2021
 */
public class AStarSearch<T extends Comparable<T>> implements ISearchAlgorithm<T> {
    private final SearchProblem<T> searchProblem;
    private final Heuristics<T> heuristics;
    private int visitedStates = - 1;

    public AStarSearch(SearchProblem<T> searchProblem, Heuristics<T> heuristics) {
        this.searchProblem = searchProblem;
        this.heuristics = heuristics;
    }

    @Override
    public NodeCost<T> search(T startState) {
        Queue<HeuristicNode<T>> open = new PriorityQueue<>(HeuristicNode.COMPARATOR_BY_ESTIMATED_TOTAL_COST.thenComparing(HeuristicNode.COMPARATOR_BY_VALUE));
        Map<T, HeuristicNode<T>> openMap = new HashMap<>();
        Map<T, HeuristicNode<T>> closedMap = new HashMap<>();

        HeuristicNode<T> startNode = new HeuristicNode<>(null, startState, 0, heuristics.getHeuristicCost(startState));
        open.add(startNode);
        openMap.put(startState, startNode);

        while (! open.isEmpty()) {
            var currentNode = open.poll();
            openMap.remove(currentNode.getState());

            if(closedMap.put(currentNode.getState(), currentNode)!=null) continue;

            if (searchProblem.isGoal(currentNode.getState())) {
                visitedStates = closedMap.size();
                return currentNode;
            }

            var successors = searchProblem.getSuccessorsSortedByCostName(currentNode.getState());
            if(successors==null) continue;

            for (var nextState : successors) {
                HeuristicNode<T> nextNode = new HeuristicNode<>(currentNode,
                        nextState.getState(),
                        currentNode.getCost() + nextState.getCost(),
                        currentNode.getCost() + nextState.getCost() + heuristics.getHeuristicCost(nextState.getState())
                );

                var nodeInOpen = openMap.get(nextNode.getState());
                if (nodeInOpen != null && nodeInOpen.getCost() < nextNode.getCost())
                    continue;

                var nodeInClosed = openMap.get(nextNode.getState());
                if (nodeInClosed != null && nodeInClosed.getCost() < nextNode.getCost())
                    continue;

                if(nodeInOpen!=null){
                    openMap.remove(nodeInOpen.getState());
                    open.remove(nodeInOpen);
                }

                open.add(nextNode);
                openMap.put(nextNode.getState(), nextNode);
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
        return "A-STAR";
    }
}
