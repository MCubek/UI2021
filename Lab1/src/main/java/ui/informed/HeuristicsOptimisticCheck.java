package ui.informed;

import ui.blind.UniformCostSearch;
import ui.common.NodeCost;
import ui.common.SearchProblem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

/**
 * Class for checking if heuristic is optimistic.
 * Implements {@link IHeuristicsCheckAlgorithm}
 * Optimistic heuristic for each state garantees its heuristic cost is less
 * or equal it's actual cost.
 *
 * @author MatejCubek
 * @project UILab1
 * @created 26/03/2021
 */
public class HeuristicsOptimisticCheck<T extends Comparable<T>> implements IHeuristicsCheckAlgorithm<T> {
    private final Heuristics<T> heuristics;
    private final UniformCostSearch<T> uniformCostSearch;

    public HeuristicsOptimisticCheck(SearchProblem<T> searchProblem, Heuristics<T> heuristics) {
        this.heuristics = heuristics;
        this.uniformCostSearch = new UniformCostSearch<>(searchProblem);
    }

    @Override
    public String checkAndGetOutput() {
        StringBuilder sb = new StringBuilder(1024);
        boolean optimistic = true;

        Map<T, Double> stateCostMap = new HashMap<>();

        var states = heuristics.getOrderedStatesByHeuristics();

        for (T state : states) {
            double realPrice = getRealPrice(state, stateCostMap);
            double heuristicPrice = heuristics.getHeuristicCost(state);

            if (heuristicPrice > realPrice)
                optimistic = false;

            appendResult(sb, state, realPrice, heuristicPrice);
        }
        if (optimistic)
            sb.append("[CONCLUSION]: Heuristic is optimistic.\n");
        else
            sb.append("[CONCLUSION]: Heuristic is not optimistic.\n");

        return sb.toString();
    }

    private void appendResult(StringBuilder sb, T state, double realPrice, double heuristicPrice) {
        sb.append(String.format("[CONDITION]: [%s] h(%s) <= h*: %s <= %s\n",
                heuristicPrice <= realPrice ? "OK" : "ERR",
                state,
                formatPrice(heuristicPrice),
                formatPrice(realPrice)));
    }

    private double getRealPrice(T state, Map<T, Double> stateCostMap) {
        if (stateCostMap.containsKey(state)) return stateCostMap.get(state);

        var node = uniformCostSearch.search(state);
        if (node == null) return - 1;

        populateMap2(node, stateCostMap, node.getCost());

        return node.getCost();
    }

    private boolean populateMap(NodeCost<T> state, Map<T, Double> stateCostMap, Double price) {
        if (state.getParent() != null) {
            if (! populateMap((NodeCost<T>) state.getParent(), stateCostMap, price))
                return false;
        }

        return (stateCostMap.put(state.getState(), price - state.getCost()) == null);
    }

    private void populateMap2(NodeCost<T> state, Map<T, Double> stateCostMap, Double price) {
        Deque<NodeCost<T>> stack = new ArrayDeque<>(state.nodePathLength());
        var node = state;
        while (node != null) {
            stack.push(node);
            node = (NodeCost<T>) node.getParent();
        }
        while ((node = stack.poll()) != null) {
            if (stateCostMap.put(node.getState(), price - node.getCost()) != null)
                break;
        }
    }

    private String formatPrice(double price) {
        DecimalFormat df = new DecimalFormat("0.0");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        return df.format(price);
    }


    @Override
    public String getName() {
        return "HEURISTIC-OPTIMISTIC";
    }
}
