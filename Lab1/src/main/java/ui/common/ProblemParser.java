package ui.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.lang.String;
import java.util.function.Function;

/**
 * Class that implements {@link SearchProblem} for purpuses of UI Lab1.
 * Class parses input file and makes a tree of states.
 *
 * @author MatejCubek
 * @project UILab1
 * @created 25/03/2021
 */
public class ProblemParser implements SearchProblem<String> {
    private Map<String, Set<StateCost<String>>> map;
    private Map<String, Set<StateCost<String>>> map2;
    private String startingState;
    private final Set<String> finalStates = new HashSet<>();

    public ProblemParser(Path states) throws IOException {
        generateStateMap(states);
    }

    private void generateStateMap(Path states) throws IOException {
        map = new HashMap<>();
        map2 = new HashMap<>();
        //Parse file for lines
        try (BufferedReader buf = Files.newBufferedReader(states)) {
            String line;
            int lineCounter = 0;
            while ((line = buf.readLine()) != null) {
                if (line.startsWith("#")) continue;
                lineCounter++;

                if (lineCounter == 1) {
                    startingState = line;
                    continue;
                } else if (lineCounter == 2) {
                    finalStates.addAll(Arrays.asList(line.split(" ")));
                    continue;
                }

                //Parse single line
                String[] statesFirstRest = line.split(":");

                if (statesFirstRest.length == 1) continue;

                String source = statesFirstRest[0];
                String[] destinations = statesFirstRest[1].trim().split(" ");

                Set<StateCost<String>> nextStates = map.computeIfAbsent(source, s -> new TreeSet<>(StateCost.COMPARATOR_BY_COST.thenComparing(StateCost.COMPARATOR_BY_STATE.reversed())));
                Set<StateCost<String>> nextStatesOnlyName = map2.computeIfAbsent(source, s -> new TreeSet<>(StateCost.COMPARATOR_BY_STATE));

                for (String destination : destinations) {
                    String[] nameValue = destination.split(",");
                    nextStates.add(new StateCost<>(nameValue[0], Double.parseDouble(nameValue[1])));
                    nextStatesOnlyName.add(new StateCost<>(nameValue[0], Double.parseDouble(nameValue[1])));
                }
            }
        }
    }

    @Override
    public boolean isGoal(String state) {
        return finalStates.contains(state);
    }

    @Override
    public String getStart() {
        return startingState;
    }

    @Override
    public Set<StateCost<String>> getSuccessorsSortedByCostName(String state) {
        var set = map.get(state);
        if (set == null) return null;
        return Collections.unmodifiableSet(set);
    }

    @Override
    public Set<StateCost<String>> getSuccessorsSortedByNameOnly(String state) {
        var set = map2.get(state);
        if (set == null) return null;
        return Collections.unmodifiableSet(set);
    }

}
