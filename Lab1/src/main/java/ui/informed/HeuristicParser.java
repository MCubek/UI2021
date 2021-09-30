package ui.informed;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class implementing {@link Heuristics} that is used for UILab1.
 * Class parses input file and creates impelemtation.
 *
 * @author MatejCubek
 * @project UILab1
 * @created 26/03/2021
 */
public class HeuristicParser implements Heuristics<String> {
    private final Map<String, Double> map;


    public HeuristicParser(Path states) throws IOException {
        map = generateHeuristicsMap(states);
    }

    private Map<String, Double> generateHeuristicsMap(Path states) throws IOException {
        Map<String, Double> map = new HashMap<>();

        //Parse file for lines
        try (BufferedReader buf = Files.newBufferedReader(states)) {
            String line;

            while ((line = buf.readLine()) != null) {
                if (line.startsWith("#")) continue;

                String[] array = line.split(":");

                map.put(array[0].trim(), Double.parseDouble(array[1].trim()));
            }
        }
        return map;
    }


    @Override
    public double getHeuristicCost(String state) {
        return map.get(state);
    }

    @Override
    public List<String> getOrderedStatesByHeuristics() {
        List<Map.Entry<String, Double>> entryList = new ArrayList<>(map.entrySet());

        entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        return  entryList
                .stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toUnmodifiableList());
    }
}
