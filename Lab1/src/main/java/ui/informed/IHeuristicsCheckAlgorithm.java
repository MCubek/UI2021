package ui.informed;

/**
 * Interface for heuristics checking
 *
 * @author MatejCubek
 * @project UILab1
 * @created 25/03/2021
 */
public interface IHeuristicsCheckAlgorithm<T> {

    /**
     * Metkod that checks if heuristics pass inspection and returns string
     * with output formatted correctly as lab1 describes.
     *
     * @return formatted output of check
     */
    String checkAndGetOutput();

    /**
     * Gets name of algorithm.
     *
     * @return name of algorithm
     */
    String getName();
}
