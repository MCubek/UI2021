package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class.
 *
 * @author MatejCubek
 * @project UILab1
 * @created 26/03/2021
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
    void istraBFS() {
        Path statesPath = resources.resolve("istra.txt");

        String[] args = {"--alg", "BFS", "--ss", statesPath.toAbsolutePath().toString()};

        Solution.main(args);

        String expected = """
                # BFS
                [FOUND_SOLUTION]: yes
                [STATES_VISITED]: 11
                [PATH_LENGTH]: 5
                [TOTAL_COST]: 100.0
                [PATH]: Pula => Barban => Labin => Lupoglav => Buzet
                """;

        assertEquals(expected.trim(), outputStreamCaptor.toString().trim());
    }

    @Test
    void AiBFS() {
        Path statesPath = resources.resolve("ai.txt");

        String[] args = {"--alg", "BFS", "--ss", statesPath.toAbsolutePath().toString()};

        Solution.main(args);

        String expected = """
                # BFS
                [FOUND_SOLUTION]: yes
                [STATES_VISITED]: 6
                [PATH_LENGTH]: 3
                [TOTAL_COST]: 21.0
                [PATH]: enroll_artificial_intelligence => fail_lab => fail_course
                """;

        assertEquals(expected.trim(), outputStreamCaptor.toString().trim());
    }

    @Test
    void istraUCS() {
        Path statesPath = resources.resolve("istra.txt");

        String[] args = {"--alg", "UCS", "--ss", statesPath.toAbsolutePath().toString()};

        Solution.main(args);

        String expected = """
                # UCS
                [FOUND_SOLUTION]: yes
                [STATES_VISITED]: 17
                [PATH_LENGTH]: 5
                [TOTAL_COST]: 100.0
                [PATH]: Pula => Barban => Labin => Lupoglav => Buzet
                """;

        assertEquals(expected.trim(), outputStreamCaptor.toString().trim());
    }

    @Test
    void AiUCS() {
        Path statesPath = resources.resolve("ai.txt");

        String[] args = {"--alg", "UCS", "--ss", statesPath.toAbsolutePath().toString()};

        Solution.main(args);

        String expected = """
                # UCS
                [FOUND_SOLUTION]: yes
                [STATES_VISITED]: 7
                [PATH_LENGTH]: 4
                [TOTAL_COST]: 17.0
                [PATH]: enroll_artificial_intelligence => complete_lab => pass_continuous => pass_course
                """;

        assertEquals(expected.trim(), outputStreamCaptor.toString().trim());
    }

    @Test
    void three3UCS() {
        Path statesPath = resources.resolve("3x3_puzzle.txt");

        String[] args = {"--alg", "UCS", "--ss", statesPath.toAbsolutePath().toString()};

        Solution.main(args);

        String expected = """
                # UCS
                [FOUND_SOLUTION]: yes
                [STATES_VISITED]: 181218
                [PATH_LENGTH]: 31
                [TOTAL_COST]: 30.0
                [PATH]: 876_543_21x => 876_543_2x1 => 876_543_x21 => 876_x43_521 => x76_843_521 => 7x6_843_521 => 746_8x3_521 => 746_83x_521 => 746_831_52x => 746_831_5x2 => 746_831_x52 => 746_x31_852 => x46_731_852 => 4x6_731_852 => 436_7x1_852 => 436_71x_852 => 43x_716_852 => 4x3_716_852 => 413_7x6_852 => 413_756_8x2 => 413_756_82x => 413_75x_826 => 413_7x5_826 => 413_725_8x6 => 413_725_x86 => 413_x25_786 => x13_425_786 => 1x3_425_786 => 123_4x5_786 => 123_45x_786 => 123_456_78x
                """;

        assertEquals(expected.trim(), outputStreamCaptor.toString().trim());
    }

    @Test
    void AiAstarFail() {
        Path statesPath = resources.resolve("ai.txt");
        Path heuristicsPath = resources.resolve("ai_fail.txt");

        String[] args = {"--alg", "ASTAR", "--ss", statesPath.toAbsolutePath().toString(), "--h", heuristicsPath.toAbsolutePath().toString()};

        Solution.main(args);

        String expected = """
                # A-STAR ai_fail.txt
                [FOUND_SOLUTION]: yes
                [STATES_VISITED]: 6
                [PATH_LENGTH]: 3
                [TOTAL_COST]: 21.0
                [PATH]: enroll_artificial_intelligence => fail_lab => fail_course
                """;

        assertEquals(expected.trim(), outputStreamCaptor.toString().trim());
    }

    @Test
    void AiAstarPass() {
        Path statesPath = resources.resolve("ai.txt");
        Path heuristicsPath = resources.resolve("ai_pass.txt");

        String[] args = {"--alg", "ASTAR", "--ss", statesPath.toAbsolutePath().toString(), "--h", heuristicsPath.toAbsolutePath().toString()};

        Solution.main(args);

        String expected = """
                # A-STAR ai_pass.txt
                [FOUND_SOLUTION]: yes
                [STATES_VISITED]: 4
                [PATH_LENGTH]: 4
                [TOTAL_COST]: 17.0
                [PATH]: enroll_artificial_intelligence => complete_lab => pass_continuous => pass_course
                """;

        assertEquals(expected.trim(), outputStreamCaptor.toString().trim());
    }

    @Test
    void IstraAstar() {
        Path statesPath = resources.resolve("istra.txt");
        Path heuristicsPath = resources.resolve("istra_heuristic.txt");

        String[] args = {"--alg", "ASTAR", "--ss", statesPath.toAbsolutePath().toString(), "--h", heuristicsPath.toAbsolutePath().toString()};

        Solution.main(args);

        String expected = """
                # A-STAR istra_heuristic.txt
                [FOUND_SOLUTION]: yes
                [STATES_VISITED]: 14
                [PATH_LENGTH]: 5
                [TOTAL_COST]: 100.0
                [PATH]: Pula => Barban => Labin => Lupoglav => Buzet
                """;

        assertEquals(expected.trim(), outputStreamCaptor.toString().trim());
    }


    @Test
    void IstraAstar2() {
        Path statesPath = resources.resolve("istra.txt");
        Path heuristicsPath = resources.resolve("istra_pessimistic_heuristic.txt");

        String[] args = {"--alg", "ASTAR", "--ss", statesPath.toAbsolutePath().toString(), "--h", heuristicsPath.toAbsolutePath().toString()};

        Solution.main(args);

        String expected = """
                # A-STAR istra_pessimistic_heuristic.txt
                [FOUND_SOLUTION]: yes
                [STATES_VISITED]: 13
                [PATH_LENGTH]: 7
                [TOTAL_COST]: 102.0
                [PATH]: Pula => Vodnjan => Kanfanar => Žminj => Pazin => Motovun => Buzet
                """;

        assertEquals(expected.trim(), outputStreamCaptor.toString().trim());
    }

    @Test
    void three3Astar() {
        Path statesPath = resources.resolve("3x3_puzzle.txt");
        Path heuristicsPath = resources.resolve("3x3_misplaced_heuristic.txt");

        String[] args = {"--alg", "ASTAR", "--ss", statesPath.toAbsolutePath().toString(), "--h", heuristicsPath.toAbsolutePath().toString()};

        Solution.main(args);

        String expected = """
                # A-STAR 3x3_misplaced_heuristic.txt
                [FOUND_SOLUTION]: yes
                [STATES_VISITED]: 95544
                [PATH_LENGTH]: 31
                [TOTAL_COST]: 30.0
                [PATH]: 876_543_21x => 876_54x_213 => 876_5x4_213 => 876_x54_213 => 876_254_x13 => 876_254_1x3 => 876_2x4_153 => 876_24x_153 => 876_243_15x => 876_243_1x5 => 876_2x3_145 => 8x6_273_145 => x86_273_145 => 286_x73_145 => 286_173_x45 => 286_173_4x5 => 286_1x3_475 => 2x6_183_475 => 26x_183_475 => 263_18x_475 => 263_1x8_475 => 2x3_168_475 => x23_168_475 => 123_x68_475 => 123_468_x75 => 123_468_7x5 => 123_468_75x => 123_46x_758 => 123_4x6_758 => 123_456_7x8 => 123_456_78x
                """;

        assertEquals(expected.trim(), outputStreamCaptor.toString().trim());
    }

    @Test
    void AiOptimisedCheckFail() {
        Path statesPath = resources.resolve("ai.txt");
        Path heuristicsPath = resources.resolve("ai_fail.txt");

        String[] args = {"--check-optimistic", "--ss", statesPath.toAbsolutePath().toString(), "--h", heuristicsPath.toAbsolutePath().toString()};

        Solution.main(args);

        var expected = Arrays.asList("""
                # HEURISTIC-OPTIMISTIC ai_fail.txt
                [CONDITION]: [OK] h(complete_lab) <= h*: 10.0 <= 13.0
                [CONDITION]: [OK] h(enroll_artificial_intelligence) <= h*: 17.0 <= 17.0
                [CONDITION]: [OK] h(fail_continuous) <= h*: 6.0 <= 17.0
                [CONDITION]: [OK] h(fail_course) <= h*: 0.0 <= 0.0
                [CONDITION]: [OK] h(fail_exam) <= h*: 5.0 <= 20.0
                [CONDITION]: [OK] h(fail_lab) <= h*: 1.0 <= 17.0
                [CONDITION]: [ERR] h(pass_continuous) <= h*: 20.0 <= 1.0
                [CONDITION]: [OK] h(pass_course) <= h*: 0.0 <= 0.0
                [CONDITION]: [OK] h(pass_exam) <= h*: 1.0 <= 1.0
                [CONCLUSION]: Heuristic is not optimistic.
                """.replace("\r","").split("\n"));

        var recieved = Arrays.asList(outputStreamCaptor.toString().replace("\r","").split("\n"));

        assertTrue(expected.size() == recieved.size() && expected.containsAll(recieved) && recieved.containsAll(expected));
    }

    @Test
    void AiOptimisedCheckPass() {
        Path statesPath = resources.resolve("ai.txt");
        Path heuristicsPath = resources.resolve("ai_pass.txt");

        String[] args = {"--check-optimistic", "--ss", statesPath.toAbsolutePath().toString(), "--h", heuristicsPath.toAbsolutePath().toString()};

        Solution.main(args);

        var expected = Arrays.asList("""
                # HEURISTIC-OPTIMISTIC ai_pass.txt
                [CONDITION]: [OK] h(complete_lab) <= h*: 13.0 <= 13.0
                [CONDITION]: [OK] h(enroll_artificial_intelligence) <= h*: 17.0 <= 17.0
                [CONDITION]: [OK] h(fail_continuous) <= h*: 17.0 <= 17.0
                [CONDITION]: [OK] h(fail_course) <= h*: 0.0 <= 0.0
                [CONDITION]: [OK] h(fail_exam) <= h*: 20.0 <= 20.0
                [CONDITION]: [OK] h(fail_lab) <= h*: 17.0 <= 17.0
                [CONDITION]: [OK] h(pass_continuous) <= h*: 1.0 <= 1.0
                [CONDITION]: [OK] h(pass_course) <= h*: 0.0 <= 0.0
                [CONDITION]: [OK] h(pass_exam) <= h*: 1.0 <= 1.0
                [CONCLUSION]: Heuristic is optimistic.
                """.replace("\r","").split("\n"));

        var recieved = Arrays.asList(outputStreamCaptor.toString().replace("\r","").split("\n"));

        assertTrue(expected.size() == recieved.size() && expected.containsAll(recieved) && recieved.containsAll(expected));
    }

    @Test
    void IstraOptimisedCheck() {
        Path statesPath = resources.resolve("istra.txt");
        Path heuristicsPath = resources.resolve("istra_pessimistic_heuristic.txt");

        String[] args = {"--check-optimistic", "--ss", statesPath.toAbsolutePath().toString(), "--h", heuristicsPath.toAbsolutePath().toString()};

        Solution.main(args);

        var expected = Arrays.asList("""
                # HEURISTIC-OPTIMISTIC istra_pessimistic_heuristic.txt
                [CONDITION]: [OK] h(Baderna) <= h*: 25.0 <= 57.0
                [CONDITION]: [OK] h(Barban) <= h*: 35.0 <= 72.0
                [CONDITION]: [OK] h(Buje) <= h*: 21.0 <= 41.0
                [CONDITION]: [OK] h(Buzet) <= h*: 0.0 <= 0.0
                [CONDITION]: [OK] h(Grožnjan) <= h*: 17.0 <= 33.0
                [CONDITION]: [OK] h(Kanfanar) <= h*: 30.0 <= 61.0
                [CONDITION]: [OK] h(Labin) <= h*: 35.0 <= 57.0
                [CONDITION]: [ERR] h(Lupoglav) <= h*: 35.0 <= 15.0
                [CONDITION]: [OK] h(Medulin) <= h*: 61.0 <= 109.0
                [CONDITION]: [OK] h(Motovun) <= h*: 12.0 <= 18.0
                [CONDITION]: [OK] h(Opatija) <= h*: 26.0 <= 44.0
                [CONDITION]: [ERR] h(Pazin) <= h*: 40.0 <= 38.0
                [CONDITION]: [OK] h(Poreč) <= h*: 32.0 <= 71.0
                [CONDITION]: [OK] h(Pula) <= h*: 57.0 <= 100.0
                [CONDITION]: [OK] h(Rovinj) <= h*: 40.0 <= 79.0
                [CONDITION]: [OK] h(Umag) <= h*: 31.0 <= 54.0
                [CONDITION]: [OK] h(Višnjan) <= h*: 20.0 <= 52.0
                [CONDITION]: [OK] h(Vodnjan) <= h*: 47.0 <= 90.0
                [CONDITION]: [OK] h(Žminj) <= h*: 27.0 <= 55.0
                [CONCLUSION]: Heuristic is not optimistic.
                """.replace("\r","").split("\n"));

        var recieved = Arrays.asList(outputStreamCaptor.toString().replace("\r","").split("\n"));

        assertTrue(expected.size() == recieved.size() && expected.containsAll(recieved) && recieved.containsAll(expected));
    }

    @Test
    void AiConsistencyCheckFail() {
        Path statesPath = resources.resolve("ai.txt");
        Path heuristicsPath = resources.resolve("ai_fail.txt");

        String[] args = {"--check-consistent", "--ss", statesPath.toAbsolutePath().toString(), "--h", heuristicsPath.toAbsolutePath().toString()};

        Solution.main(args);

        var expected = Arrays.asList("""
                # HEURISTIC-CONSISTENT ai_fail.txt
                [CONDITION]: [ERR] h(complete_lab) <= h(fail_continuous) + c: 10.0 <= 6.0 + 1.0
                [CONDITION]: [OK] h(complete_lab) <= h(pass_continuous) + c: 10.0 <= 20.0 + 12.0
                [CONDITION]: [ERR] h(enroll_artificial_intelligence) <= h(complete_lab) + c: 17.0 <= 10.0 + 4.0
                [CONDITION]: [ERR] h(enroll_artificial_intelligence) <= h(fail_lab) + c: 17.0 <= 1.0 + 1.0
                [CONDITION]: [OK] h(fail_continuous) <= h(fail_exam) + c: 6.0 <= 5.0 + 1.0
                [CONDITION]: [OK] h(fail_continuous) <= h(pass_exam) + c: 6.0 <= 1.0 + 16.0
                [CONDITION]: [OK] h(fail_exam) <= h(fail_course) + c: 5.0 <= 0.0 + 20.0
                [CONDITION]: [OK] h(fail_lab) <= h(complete_lab) + c: 1.0 <= 10.0 + 4.0
                [CONDITION]: [OK] h(fail_lab) <= h(fail_course) + c: 1.0 <= 0.0 + 20.0
                [CONDITION]: [OK] h(fail_lab) <= h(fail_lab) + c: 1.0 <= 1.0 + 1.0
                [CONDITION]: [ERR] h(pass_continuous) <= h(pass_course) + c: 20.0 <= 0.0 + 1.0
                [CONDITION]: [OK] h(pass_exam) <= h(pass_course) + c: 1.0 <= 0.0 + 1.0
                [CONCLUSION]: Heuristic is not consistent.
                """.replace("\r","").split("\n"));

        var recieved = Arrays.asList(outputStreamCaptor.toString().replace("\r","").split("\n"));

        assertTrue(expected.size() == recieved.size() && expected.containsAll(recieved) && recieved.containsAll(expected));
    }

    @Test
    void istraConsistencyTest() {
        Path statesPath = resources.resolve("istra.txt");
        Path heuristicsPath = resources.resolve("istra_heuristic.txt");

        String[] args = {"--check-consistent", "--ss", statesPath.toAbsolutePath().toString(), "--h", heuristicsPath.toAbsolutePath().toString()};

        Solution.main(args);

        var expected = Arrays.asList("""
                # HEURISTIC-CONSISTENT istra_heuristic.txt
                [CONDITION]: [OK] h(Baderna) <= h(Kanfanar) + c: 25.0 <= 30.0 + 19.0
                [CONDITION]: [OK] h(Baderna) <= h(Pazin) + c: 25.0 <= 17.0 + 19.0
                [CONDITION]: [OK] h(Baderna) <= h(Poreč) + c: 25.0 <= 32.0 + 14.0
                [CONDITION]: [OK] h(Baderna) <= h(Višnjan) + c: 25.0 <= 20.0 + 13.0
                [CONDITION]: [OK] h(Barban) <= h(Labin) + c: 35.0 <= 35.0 + 15.0
                [CONDITION]: [OK] h(Barban) <= h(Pula) + c: 35.0 <= 57.0 + 28.0
                [CONDITION]: [OK] h(Buje) <= h(Grožnjan) + c: 21.0 <= 17.0 + 8.0
                [CONDITION]: [OK] h(Buje) <= h(Umag) + c: 21.0 <= 31.0 + 13.0
                [CONDITION]: [OK] h(Buzet) <= h(Lupoglav) + c: 0.0 <= 13.0 + 15.0
                [CONDITION]: [OK] h(Buzet) <= h(Motovun) + c: 0.0 <= 12.0 + 18.0
                [CONDITION]: [OK] h(Grožnjan) <= h(Buje) + c: 17.0 <= 21.0 + 8.0
                [CONDITION]: [OK] h(Grožnjan) <= h(Motovun) + c: 17.0 <= 12.0 + 15.0
                [CONDITION]: [OK] h(Grožnjan) <= h(Višnjan) + c: 17.0 <= 20.0 + 19.0
                [CONDITION]: [OK] h(Kanfanar) <= h(Baderna) + c: 30.0 <= 25.0 + 19.0
                [CONDITION]: [OK] h(Kanfanar) <= h(Rovinj) + c: 30.0 <= 40.0 + 18.0
                [CONDITION]: [OK] h(Kanfanar) <= h(Vodnjan) + c: 30.0 <= 47.0 + 29.0
                [CONDITION]: [OK] h(Kanfanar) <= h(Žminj) + c: 30.0 <= 27.0 + 6.0
                [CONDITION]: [OK] h(Labin) <= h(Barban) + c: 35.0 <= 35.0 + 15.0
                [CONDITION]: [OK] h(Labin) <= h(Lupoglav) + c: 35.0 <= 13.0 + 42.0
                [CONDITION]: [OK] h(Lupoglav) <= h(Buzet) + c: 13.0 <= 0.0 + 15.0
                [CONDITION]: [OK] h(Lupoglav) <= h(Labin) + c: 13.0 <= 35.0 + 42.0
                [CONDITION]: [OK] h(Lupoglav) <= h(Opatija) + c: 13.0 <= 26.0 + 29.0
                [CONDITION]: [OK] h(Lupoglav) <= h(Pazin) + c: 13.0 <= 17.0 + 23.0
                [CONDITION]: [OK] h(Medulin) <= h(Pula) + c: 61.0 <= 57.0 + 9.0
                [CONDITION]: [OK] h(Motovun) <= h(Buzet) + c: 12.0 <= 0.0 + 18.0
                [CONDITION]: [OK] h(Motovun) <= h(Grožnjan) + c: 12.0 <= 17.0 + 15.0
                [CONDITION]: [OK] h(Motovun) <= h(Pazin) + c: 12.0 <= 17.0 + 20.0
                [CONDITION]: [OK] h(Opatija) <= h(Lupoglav) + c: 26.0 <= 13.0 + 29.0
                [CONDITION]: [OK] h(Pazin) <= h(Baderna) + c: 17.0 <= 25.0 + 19.0
                [CONDITION]: [OK] h(Pazin) <= h(Lupoglav) + c: 17.0 <= 13.0 + 23.0
                [CONDITION]: [OK] h(Pazin) <= h(Motovun) + c: 17.0 <= 12.0 + 20.0
                [CONDITION]: [OK] h(Pazin) <= h(Žminj) + c: 17.0 <= 27.0 + 17.0
                [CONDITION]: [OK] h(Poreč) <= h(Baderna) + c: 32.0 <= 25.0 + 14.0
                [CONDITION]: [OK] h(Pula) <= h(Barban) + c: 57.0 <= 35.0 + 28.0
                [CONDITION]: [OK] h(Pula) <= h(Medulin) + c: 57.0 <= 61.0 + 9.0
                [CONDITION]: [OK] h(Pula) <= h(Vodnjan) + c: 57.0 <= 47.0 + 12.0
                [CONDITION]: [OK] h(Rovinj) <= h(Kanfanar) + c: 40.0 <= 30.0 + 18.0
                [CONDITION]: [OK] h(Umag) <= h(Buje) + c: 31.0 <= 21.0 + 13.0
                [CONDITION]: [OK] h(Višnjan) <= h(Baderna) + c: 20.0 <= 25.0 + 13.0
                [CONDITION]: [OK] h(Višnjan) <= h(Grožnjan) + c: 20.0 <= 17.0 + 19.0
                [CONDITION]: [OK] h(Vodnjan) <= h(Kanfanar) + c: 47.0 <= 30.0 + 29.0
                [CONDITION]: [OK] h(Vodnjan) <= h(Pula) + c: 47.0 <= 57.0 + 12.0
                [CONDITION]: [OK] h(Žminj) <= h(Kanfanar) + c: 27.0 <= 30.0 + 6.0
                [CONDITION]: [OK] h(Žminj) <= h(Pazin) + c: 27.0 <= 17.0 + 17.0
                [CONCLUSION]: Heuristic is consistent.
                """.replace("\r","").split("\n"));

        var recieved = Arrays.asList(outputStreamCaptor.toString().replace("\r","").split("\n"));

        assertTrue(expected.size() == recieved.size() && expected.containsAll(recieved) && recieved.containsAll(expected));
    }

    @Test
    void AiConsistencyCheckPass() {
        Path statesPath = resources.resolve("ai.txt");
        Path heuristicsPath = resources.resolve("ai_pass.txt");

        String[] args = {"--check-consistent", "--ss", statesPath.toAbsolutePath().toString(), "--h", heuristicsPath.toAbsolutePath().toString()};

        Solution.main(args);

        var expected = Arrays.asList("""
                # HEURISTIC-CONSISTENT ai_pass.txt
                [CONDITION]: [OK] h(complete_lab) <= h(fail_continuous) + c: 13.0 <= 17.0 + 1.0
                [CONDITION]: [OK] h(complete_lab) <= h(pass_continuous) + c: 13.0 <= 1.0 + 12.0
                [CONDITION]: [OK] h(enroll_artificial_intelligence) <= h(complete_lab) + c: 17.0 <= 13.0 + 4.0
                [CONDITION]: [OK] h(enroll_artificial_intelligence) <= h(fail_lab) + c: 17.0 <= 17.0 + 1.0
                [CONDITION]: [OK] h(fail_continuous) <= h(fail_exam) + c: 17.0 <= 20.0 + 1.0
                [CONDITION]: [OK] h(fail_continuous) <= h(pass_exam) + c: 17.0 <= 1.0 + 16.0
                [CONDITION]: [OK] h(fail_exam) <= h(fail_course) + c: 20.0 <= 0.0 + 20.0
                [CONDITION]: [OK] h(fail_lab) <= h(complete_lab) + c: 17.0 <= 13.0 + 4.0
                [CONDITION]: [OK] h(fail_lab) <= h(fail_course) + c: 17.0 <= 0.0 + 20.0
                [CONDITION]: [OK] h(fail_lab) <= h(fail_lab) + c: 17.0 <= 17.0 + 1.0
                [CONDITION]: [OK] h(pass_continuous) <= h(pass_course) + c: 1.0 <= 0.0 + 1.0
                [CONDITION]: [OK] h(pass_exam) <= h(pass_course) + c: 1.0 <= 0.0 + 1.0
                [CONCLUSION]: Heuristic is consistent.
                """.replace("\r","").split("\n"));

        var recieved = Arrays.asList(outputStreamCaptor.toString().replace("\r","").split("\n"));

        assertTrue(expected.size() == recieved.size() && expected.containsAll(recieved) && recieved.containsAll(expected));
    }

    @Test
    void IstraConsistencyCheck() {
        Path statesPath = resources.resolve("istra.txt");
        Path heuristicsPath = resources.resolve("istra_pessimistic_heuristic.txt");

        String[] args = {"--check-consistent", "--ss", statesPath.toAbsolutePath().toString(), "--h", heuristicsPath.toAbsolutePath().toString()};

        Solution.main(args);

        var expected = Arrays.asList("""
                # HEURISTIC-CONSISTENT istra_pessimistic_heuristic.txt
                [CONDITION]: [OK] h(Baderna) <= h(Kanfanar) + c: 25.0 <= 30.0 + 19.0
                [CONDITION]: [OK] h(Baderna) <= h(Pazin) + c: 25.0 <= 40.0 + 19.0
                [CONDITION]: [OK] h(Baderna) <= h(Poreč) + c: 25.0 <= 32.0 + 14.0
                [CONDITION]: [OK] h(Baderna) <= h(Višnjan) + c: 25.0 <= 20.0 + 13.0
                [CONDITION]: [OK] h(Barban) <= h(Labin) + c: 35.0 <= 35.0 + 15.0
                [CONDITION]: [OK] h(Barban) <= h(Pula) + c: 35.0 <= 57.0 + 28.0
                [CONDITION]: [OK] h(Buje) <= h(Grožnjan) + c: 21.0 <= 17.0 + 8.0
                [CONDITION]: [OK] h(Buje) <= h(Umag) + c: 21.0 <= 31.0 + 13.0
                [CONDITION]: [OK] h(Buzet) <= h(Lupoglav) + c: 0.0 <= 35.0 + 15.0
                [CONDITION]: [OK] h(Buzet) <= h(Motovun) + c: 0.0 <= 12.0 + 18.0
                [CONDITION]: [OK] h(Grožnjan) <= h(Buje) + c: 17.0 <= 21.0 + 8.0
                [CONDITION]: [OK] h(Grožnjan) <= h(Motovun) + c: 17.0 <= 12.0 + 15.0
                [CONDITION]: [OK] h(Grožnjan) <= h(Višnjan) + c: 17.0 <= 20.0 + 19.0
                [CONDITION]: [OK] h(Kanfanar) <= h(Baderna) + c: 30.0 <= 25.0 + 19.0
                [CONDITION]: [OK] h(Kanfanar) <= h(Rovinj) + c: 30.0 <= 40.0 + 18.0
                [CONDITION]: [OK] h(Kanfanar) <= h(Vodnjan) + c: 30.0 <= 47.0 + 29.0
                [CONDITION]: [OK] h(Kanfanar) <= h(Žminj) + c: 30.0 <= 27.0 + 6.0
                [CONDITION]: [OK] h(Labin) <= h(Barban) + c: 35.0 <= 35.0 + 15.0
                [CONDITION]: [OK] h(Labin) <= h(Lupoglav) + c: 35.0 <= 35.0 + 42.0
                [CONDITION]: [ERR] h(Lupoglav) <= h(Buzet) + c: 35.0 <= 0.0 + 15.0
                [CONDITION]: [OK] h(Lupoglav) <= h(Labin) + c: 35.0 <= 35.0 + 42.0
                [CONDITION]: [OK] h(Lupoglav) <= h(Opatija) + c: 35.0 <= 26.0 + 29.0
                [CONDITION]: [OK] h(Lupoglav) <= h(Pazin) + c: 35.0 <= 40.0 + 23.0
                [CONDITION]: [OK] h(Medulin) <= h(Pula) + c: 61.0 <= 57.0 + 9.0
                [CONDITION]: [OK] h(Motovun) <= h(Buzet) + c: 12.0 <= 0.0 + 18.0
                [CONDITION]: [OK] h(Motovun) <= h(Grožnjan) + c: 12.0 <= 17.0 + 15.0
                [CONDITION]: [OK] h(Motovun) <= h(Pazin) + c: 12.0 <= 40.0 + 20.0
                [CONDITION]: [OK] h(Opatija) <= h(Lupoglav) + c: 26.0 <= 35.0 + 29.0
                [CONDITION]: [OK] h(Pazin) <= h(Baderna) + c: 40.0 <= 25.0 + 19.0
                [CONDITION]: [OK] h(Pazin) <= h(Lupoglav) + c: 40.0 <= 35.0 + 23.0
                [CONDITION]: [ERR] h(Pazin) <= h(Motovun) + c: 40.0 <= 12.0 + 20.0
                [CONDITION]: [OK] h(Pazin) <= h(Žminj) + c: 40.0 <= 27.0 + 17.0
                [CONDITION]: [OK] h(Poreč) <= h(Baderna) + c: 32.0 <= 25.0 + 14.0
                [CONDITION]: [OK] h(Pula) <= h(Barban) + c: 57.0 <= 35.0 + 28.0
                [CONDITION]: [OK] h(Pula) <= h(Medulin) + c: 57.0 <= 61.0 + 9.0
                [CONDITION]: [OK] h(Pula) <= h(Vodnjan) + c: 57.0 <= 47.0 + 12.0
                [CONDITION]: [OK] h(Rovinj) <= h(Kanfanar) + c: 40.0 <= 30.0 + 18.0
                [CONDITION]: [OK] h(Umag) <= h(Buje) + c: 31.0 <= 21.0 + 13.0
                [CONDITION]: [OK] h(Višnjan) <= h(Baderna) + c: 20.0 <= 25.0 + 13.0
                [CONDITION]: [OK] h(Višnjan) <= h(Grožnjan) + c: 20.0 <= 17.0 + 19.0
                [CONDITION]: [OK] h(Vodnjan) <= h(Kanfanar) + c: 47.0 <= 30.0 + 29.0
                [CONDITION]: [OK] h(Vodnjan) <= h(Pula) + c: 47.0 <= 57.0 + 12.0
                [CONDITION]: [OK] h(Žminj) <= h(Kanfanar) + c: 27.0 <= 30.0 + 6.0
                [CONDITION]: [OK] h(Žminj) <= h(Pazin) + c: 27.0 <= 40.0 + 17.0
                [CONCLUSION]: Heuristic is not consistent.
                """.replace("\r","").split("\n"));

        var recieved = Arrays.asList(outputStreamCaptor.toString().replace("\r","").split("\n"));

        assertTrue(expected.size() == recieved.size() && expected.containsAll(recieved) && recieved.containsAll(expected));
        //assertEquals(expected.trim(), outputStreamCaptor.toString().trim());
    }

    @Disabled
    @Test
    void three3OptimisticJustRun() {
        Path statesPath = resources.resolve("3x3_puzzle.txt");
        Path heuristicsPath = resources.resolve("3x3_misplaced_heuristic.txt");

        String[] args = {"--check-optimistic", "--ss", statesPath.toAbsolutePath().toString(), "--h", heuristicsPath.toAbsolutePath().toString()};

        Solution.main(args);
    }
}