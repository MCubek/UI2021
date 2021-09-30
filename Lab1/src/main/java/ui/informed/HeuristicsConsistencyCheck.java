package ui.informed;

import ui.common.SearchProblem;
import ui.common.StateCost;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

/**
 * Class that check if heuristic is consistent. If heuristic is consistent it's also optimistic.
 * Heuristic is consistent if for each state it's heuristic value is lesser or equal
 * the sum of the heuristic value of the state you can travel to and the cost of that trip.
 *
 * @author MatejCubek
 * @project UILab1
 * @created 26/03/2021
 */
public class HeuristicsConsistencyCheck<T extends Comparable<T>> implements IHeuristicsCheckAlgorithm<T> {
    private final SearchProblem<T> searchProblem;
    private final Heuristics<T> heuristics;

    public HeuristicsConsistencyCheck(SearchProblem<T> searchProblem, Heuristics<T> heuristics) {
        this.searchProblem = searchProblem;
        this.heuristics = heuristics;
    }

    @Override
    public String checkAndGetOutput() {
        StringBuilder sb = new StringBuilder(1024);
        boolean consistent = true;

        var states = heuristics.getOrderedStatesByHeuristics();

        for (T state : states) {
            double h1 = heuristics.getHeuristicCost(state);

            if (searchProblem.getSuccessorsSortedByCostName(state) == null) continue;

            for (StateCost<T> stateCost : searchProblem.getSuccessorsSortedByCostName(state)) {
                double c = stateCost.getCost();
                double h2 = heuristics.getHeuristicCost(stateCost.getState());

                if (! (h1 <= h2 + c))
                    consistent = false;

                appendResult(sb, state, stateCost.getState(), h1, h2, c);
            }

        }
        if (consistent)
            sb.append("[CONCLUSION]: Heuristic is consistent.\n");
        else
            sb.append("[CONCLUSION]: Heuristic is not consistent.\n");

        return sb.toString();
    }

    private void appendResult(StringBuilder sb, T state1, T state2, double h1, double h2, double c) {
        sb.append(String.format("[CONDITION]: [%s] h(%s) <= h(%s) + c: %s <= %s + %s\n",
                h1 <= h2 + c ? "OK" : "ERR",
                state1,
                state2,
                formatPrice(h1),
                formatPrice(h2),
                formatPrice(c)));
    }

    private String formatPrice(double price) {
        DecimalFormat df = new DecimalFormat("0.0");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        return df.format(price);
    }

    @Override
    public String getName() {
        return "HEURISTIC-CONSISTENT";
    }
}
