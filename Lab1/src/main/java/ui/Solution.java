package ui;

import ui.blind.*;

import ui.common.ISearchAlgorithm;
import ui.common.ProblemParser;
import ui.informed.*;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author MatejCubek
 * @project Lab1
 * @created 23/03/2021
 */
public class Solution {

    public static void main(String[] args) {
        SearchOrCheckAlgorithm algorithm = null;
        Path states = null;
        Path heuristics = null;


        try {
            for (int i = 0; i < args.length; i++) {
                switch (args[i].toLowerCase()) {
                    case "--alg" -> {
                        String next = args[++ i].toLowerCase();
                        algorithm = switch (next) {
                            case "bfs" -> SearchOrCheckAlgorithm.BFS;
                            case "ucs" -> SearchOrCheckAlgorithm.UCS;
                            case "astar" -> SearchOrCheckAlgorithm.ASTAR;
                            default -> throw new IllegalArgumentException("No algorithm called " + next);
                        };
                    }
                    case "--ss" -> states = Path.of(args[++ i]);
                    case "--h" -> heuristics = Path.of(args[++ i]);
                    case "--check-optimistic" -> algorithm = SearchOrCheckAlgorithm.OPTIMISTIC_CHECK;
                    case "--check-consistent" -> algorithm = SearchOrCheckAlgorithm.CONSISTENT_CHECK;
                    default -> throw new IllegalArgumentException("No argument: " + args[i]);
                }
            }
        } catch (IllegalArgumentException argumentException) {
            System.out.println(argumentException.getMessage());
            return;
        } catch (IndexOutOfBoundsException exception) {
            System.out.println("Wrong number of arguments provided!");
            return;
        }

        ProblemParser problemParser;
        HeuristicParser heuristicParser = null;

        try {
            problemParser = new ProblemParser(states);
            if (algorithm != SearchOrCheckAlgorithm.BFS && algorithm != SearchOrCheckAlgorithm.UCS) {
                heuristicParser = new HeuristicParser(heuristics);
            }
        } catch (IOException e) {
            System.out.println("Problem with file: " + e.getMessage());
            return;
        }

        assert algorithm != null;
        String output = switch (algorithm) {
            case BFS -> formatSearchResults(new BreathFirstSearch<>(problemParser), heuristics);
            case UCS -> formatSearchResults(new UniformCostSearch<>(problemParser), heuristics);
            case ASTAR -> formatSearchResults(new AStarSearch<>(problemParser, heuristicParser), heuristics);
            case OPTIMISTIC_CHECK -> formatCheckResults(new HeuristicsOptimisticCheck<>(problemParser, heuristicParser), heuristics);
            case CONSISTENT_CHECK -> formatCheckResults(new HeuristicsConsistencyCheck<>(problemParser, heuristicParser), heuristics);
        };

        System.out.println(output);
    }

    private static String formatCheckResults(IHeuristicsCheckAlgorithm<String> heuristicsCheckAlgorithm, Path heuristic) {
        String output = heuristicsCheckAlgorithm.checkAndGetOutput();

        return String.format("# %s %s\n", heuristicsCheckAlgorithm.getName(), heuristic.getFileName()) +
                output;
    }

    public static String formatSearchResults(ISearchAlgorithm<String> algorithm, Path heuristicPath) {
        var node = algorithm.search();
        StringBuilder stringBuilder = new StringBuilder(256);

        //Line 1
        stringBuilder.append("# ").append(algorithm.getName());
        if (algorithm.getName().equals("A-STAR"))
            stringBuilder.append(" ").append(heuristicPath.getFileName().toString());
        stringBuilder.append("\n");

        //Line 2
        stringBuilder.append(String.format("[FOUND_SOLUTION]: %s\n", node != null ? "yes" : "no"));

        //Line 3
        stringBuilder.append(String.format("[STATES_VISITED]: %d\n", algorithm.visitedStates()));

        //Line 4
        int pathLength = 0;
        if (node != null)
            pathLength = node.nodePathLength();
        stringBuilder.append(String.format("[PATH_LENGTH]: %d\n", pathLength));

        //Line 5
        String cost = "0";
        if (node != null)
            cost = node.getFormattedCost();
        stringBuilder.append(String.format("[TOTAL_COST]: %s\n", cost));

        //Line 6
        String path = "";
        if (node != null)
            path = node.nodePath();
        stringBuilder.append(String.format("[PATH]: %s\n", path));

        return stringBuilder.toString();
    }


    public enum SearchOrCheckAlgorithm {
        BFS,
        UCS,
        ASTAR,
        OPTIMISTIC_CHECK,
        CONSISTENT_CHECK
    }
}
