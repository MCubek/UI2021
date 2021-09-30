package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * @author MatejCubek
 * @project UILab2
 * @created 07/04/2021
 */
class SolutionTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final Path resources = Path.of("src/test/resources");

    @BeforeEach
    private void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    private void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void smallExampleResolutionTest() {
        String expected = "[CONCLUSION]: c is true";

        Path inputPath = resources.resolve("resolution_examples/small_example_2.txt");

        String[] args = {"resolution", inputPath.toAbsolutePath().toString()};
        Solution.main(args);

        var recieved = Arrays.asList(outputStreamCaptor.toString().replace("\r", "").split("\n"));

        assertTrue(recieved.contains(expected));
    }

    @Test
    void autograderTest1() {
        String expected = "[CONCLUSION]: a v b is unknown";

        Path inputPath = resources.resolve("grader/resolution_heldout_tautology_4.txt");

        String[] args = {"resolution", inputPath.toAbsolutePath().toString()};
        Solution.main(args);

        var recieved = Arrays.asList(outputStreamCaptor.toString().replace("\r", "").split("\n"));

        assertTrue(recieved.contains(expected));
    }

    @Test
    void autograderTest2() {
        String expected = "[CONCLUSION]: c is unknown";

        Path inputPath = resources.resolve("grader/resolution_heldout_tautology.txt");

        String[] args = {"resolution", inputPath.toAbsolutePath().toString()};
        Solution.main(args);

        var recieved = Arrays.asList(outputStreamCaptor.toString().replace("\r", "").split("\n"));

        assertTrue(recieved.contains(expected));
    }

    @Test
    void autograderTest3() {
        String expected = "[CONCLUSION]: c is true";

        Path inputPath = resources.resolve("grader/resolution_heldout_tautology_2.txt");

        String[] args = {"resolution", inputPath.toAbsolutePath().toString()};
        Solution.main(args);

        var recieved = Arrays.asList(outputStreamCaptor.toString().replace("\r", "").split("\n"));

        assertTrue(recieved.contains(expected));
    }

    @Test
    void autograderTest4() {
        String expected = "[CONCLUSION]: coffee v tea is true";

        Path inputPath = resources.resolve("resolution_examples/coffee_or_tea.txt");

        String[] args = {"resolution", inputPath.toAbsolutePath().toString()};
        Solution.main(args);

        var recieved = Arrays.asList(outputStreamCaptor.toString().replace("\r", "").split("\n"));

        assertTrue(recieved.contains(expected));
    }
}